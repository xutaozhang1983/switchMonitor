<template>
  <el-drawer v-model="appStore.showLicense" :withHeader="false" direction="rtl" size="600px">
    <el-row :gutter="16">
      <el-col :span="6">
        <div class="avatar">
          <el-avatar :size=80>
            <el-icon :size="50"><Avatar/></el-icon>
          </el-avatar>
          <div class="status status-ok" v-if="getStatus === '已授权'">{{ getStatus }}</div>
          <div class="status status-no" v-if="getStatus === '未授权'">{{ getStatus }}</div>
          <div class="status status-expires" v-if="getStatus === '已过期'">{{ getStatus }}</div>
        </div>
      </el-col>
      <el-col :span="18">
        <el-form label-width="100px" label-position="top">
          <el-form-item label="GUID">
            <span class="value">{{ license.GUID || '' }}</span>
          </el-form-item>
          <el-form-item label="客户名称">
            <span class="value">{{ license.CUSTOM || '' }}</span>
          </el-form-item>
          <el-form-item label="授权设备数">
            <span class="value">{{ license.DEVICES || '' }}</span>
          </el-form-item>
          <el-form-item label="失效时间">
            <span class="value">{{ license.EXPIRES? dayjs(license.EXPIRES).format('YYYY-MM-DD') : '' }}</span>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <el-divider />
    <div class="footer">
      <el-button type="primary" plain icon="DocumentAdd" @click="showDialog = true">更新授权</el-button>
    </div>
  </el-drawer>
  <!--更新授权码-->
  <el-dialog title="更新授权" v-model="showDialog" width="500px" append-to-body :show-close="false">
    <el-form :model="formData" ref="licenseRef" :rules="rules">
      <el-form-item label="授权码" prop="licenseCode">
        <el-input v-model="formData.licenseCode" type="textarea" :autosize="{ minRows: 4, maxRows: 10 }" placeholder="请输入内容"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button type="primary" @click="handleSubmit">提交</el-button>
        <el-button @click='handleClose'>关闭</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
  import { ElMessage } from 'element-plus'
  import useAppStore from '@/store/modules/app'
  import useAccountStore from '@/store/modules/account'
  import dayjs from 'dayjs'

  const { proxy } = getCurrentInstance() as any
  const appStore = useAppStore()
  const accountStore = useAccountStore()

  const showDialog = ref(false)
  const formData = ref({
    licenseCode: undefined
  })
  const rules = {
    licenseCode: [
      { required: true, message: "授权码不能为空", trigger: "blur" },
    ]
  }

  const license = computed(() => accountStore.license)

  const getStatus = computed(() => {
    if (!license.value.GUID) {
      return '未授权'
    } else {
      if (dayjs().isBefore(dayjs(license.value.EXPIRES + ' 23:59:59'))) {
        return '已授权'
      } else {
        return '已过期'
      }
    }
  })

  // 提交
  function handleSubmit () {
    proxy.$refs["licenseRef"].validate(async (valid: boolean) => {
      if (valid) {
        ElMessage.success('更新授权成功')
        showDialog.value = false
        accountStore.getInfo()
      }
    })
  }

  // 关闭窗口
  function handleClose () {
    formData.value.licenseCode = undefined
    showDialog.value = false
  }
</script>

<style lang="scss" scoped>
  .avatar {
    display: flex;
    flex-direction: column;
    align-items: center;
    .status {
      font-size: 12px;
      padding: 2px 5px;
      border-radius: 3px;
      margin-top: 10px;
      &-ok {
        color: #b88230;
        border: 1px solid #E6A23C;
        background-color: #fdf9f2;
      }
      &-no {
        color: #73767a;
        border: 1px solid #909399;
        background-color: #f9fbff;
      }
      &-expires {
        color: #c45656;
        border: 1px solid #F56C6C;
        background-color: #faf3f3;
      }
    }
  }
  .value {
    color: #909399;
  }
  .footer {
    text-align: center;
  }
</style>
