<template>
  <el-dialog :title="deviceGroupStore.formTitle" v-model="deviceGroupStore.showFormDialog" width="500px" append-to-body>
    <el-form ref="deviceGroupRef" :model="deviceGroupStore.formData" :rules="rules" label-width="120px">
      <el-form-item label="设备组名称" prop="groupName">
        <el-input v-model="deviceGroupStore.formData.groupName" placeholder="请输入" />
      </el-form-item>
      <el-form-item label="报警接收用户组" prop="alarmUserGrpId">
        <el-select v-model="alarmUserGrpIdList" multiple placeholder="请选择" style="width:100%">
          <el-option v-for="(item, index) in deviceGroupStore.userData" :key="index" :label="item.userName" :value="item.userId" :disabled="item.status == 1"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="报警发送次数" prop="alarmMaxTimes">
        <el-input-number v-model="deviceGroupStore.formData.alarmMaxTimes" placeholder="请输入" :min="0" style="width:100%"/>
      </el-form-item>
      <el-form-item label="状态" @change="handleChangeStatus">
        <el-radio-group v-model="deviceGroupStore.formData.status">
          <el-radio v-for="dict in sys_normal_disable" :key="dict.value" :label="dict.value">{{ dict.label }}</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="备注">
        <el-input v-model="deviceGroupStore.formData.remark" type="textarea" placeholder="请输入内容"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
  import { ElMessage } from 'element-plus'
  import useDeviceGroupStore from '../store'

  const { proxy } = getCurrentInstance() as any
  const deviceGroupStore = useDeviceGroupStore()

  const { sys_normal_disable }: { sys_normal_disable: DictType[] } = proxy.useDict(
    "sys_normal_disable"
  )

  const alarmUserGrpIdList= ref<number[]>([])
  
  const rules = {
    groupName: [
      { required: true, message: "设备组名称不能为空", trigger: "blur" },
    ]
  }

  //更改状态
  function handleChangeStatus() {
    if (deviceGroupStore.formData.deviceCount && deviceGroupStore.formData.status === '1') {
      ElMessage.warning('设备组存在设备，不允许禁用')
      deviceGroupStore.formData.status = '0'
    }
  }

  // 初始化报警用户列表
  watchEffect(() => {
    if (deviceGroupStore.showFormDialog && deviceGroupStore.formTitle === '修改设备组') {
      if (deviceGroupStore.formData.alarmUserGrpId) {
        for (let item of deviceGroupStore.formData.alarmUserGrpId?.split(',')) {
          alarmUserGrpIdList.value.push(Number(item))
        }
      }
    }
  })
  
  // 提交按钮
  function submitForm() {
    proxy.$refs["deviceGroupRef"].validate(async (valid: boolean) => {
      if (valid) {
        if (alarmUserGrpIdList.value.length) {
          deviceGroupStore.formData.alarmUserGrpId = alarmUserGrpIdList.value.join(',')
        }
        if (deviceGroupStore.formData.id != undefined) {
          await deviceGroupStore.updateDeviceGroup()
          ElMessage.success("修改成功")
          alarmUserGrpIdList.value = []
          deviceGroupStore.showFormDialog = false
          deviceGroupStore.getDeviceGroupData()
        } else {
          await deviceGroupStore.addDeviceGroup()
          ElMessage.success("新增成功")
          alarmUserGrpIdList.value = []
          deviceGroupStore.showFormDialog = false
          deviceGroupStore.getDeviceGroupData()
        }
      }
    });
  }

  // 取消按钮
  function cancel() {
    deviceGroupStore.resetFormData()
    alarmUserGrpIdList.value = []
    deviceGroupStore.showFormDialog = false
  }
</script>