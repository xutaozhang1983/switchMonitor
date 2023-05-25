<template>
  <el-drawer v-model="appStore.showLicense" :withHeader="false" direction="rtl" size="500px">
    <el-row :gutter="16">
      <el-col :span="6">
        <div class="avatar">
          <el-avatar :size=80>
            <el-icon :size="50"><Avatar/></el-icon>
          </el-avatar>
          <div class="status">已授权</div>
        </div>
      </el-col>
      <el-col :span="18">
        <el-form v-model="license" ref="licenseRef" label-width="100px" label-position="top">
          <el-form-item label="GUID" prop="guid">
            <span class="value">{{ license.guid }}</span>
          </el-form-item>
          <el-form-item label="客户名称" prop="guid">
            <span class="value">{{ license.custom }}</span>
          </el-form-item>
          <el-form-item label="授权设备数" prop="guid">
            <span class="value">{{ decrypt(license.devices) }}</span>
          </el-form-item>
          <el-form-item label="失效时间" prop="guid">
            <span class="value">{{ dayjs(Number(decrypt(license.expires))).format('YYYY-MM-DD HH:mm:ss') }}</span>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <el-divider />
    <div class="footer">
      <el-button type="info" plain icon="DocumentAdd" :disabled="true">上传授权文件</el-button>
    </div>
  </el-drawer>
</template>

<script setup lang="ts">
  import useAppStore from '@/store/modules/app'
  import { encrypt, decrypt } from '@/utils/jsencrypt'
  import { localStorage } from '@/utils/storage'
  import dayjs from 'dayjs'

  const appStore = useAppStore()

  const license = JSON.parse(localStorage.get('license'))
  

</script>

<style lang="scss" scoped>
  .avatar {
    display: flex;
    flex-direction: column;
    align-items: center;
    .status {
      color: #b88230;
      font-size: 12px;
      border: 1px solid #E6A23C;
      padding: 2px 5px;
      border-radius: 3px;
      background-color: #fdf9f2;
      margin-top: 10px;
    }
  }
  .value {
    color: #909399;
  }
  .footer {
    text-align: center;
  }
</style>
