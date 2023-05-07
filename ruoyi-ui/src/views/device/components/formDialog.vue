<template>
  <el-dialog :title="deviceStore.formTitle" v-model="deviceStore.showFormDialog" width="500px" append-to-body>
    <el-form ref="deviceRef" :model="deviceStore.formData" :rules="rules" label-width="100px">
      <el-form-item label="设备名称" prop="deviceName">
        <el-input v-model="deviceStore.formData.deviceName" placeholder="请输入" />
      </el-form-item>
      <el-form-item label="IP地址" prop="ip">
        <el-input v-model="deviceStore.formData.ip" placeholder="请输入" />
      </el-form-item>
      <el-form-item label="厂商" prop="factoryName">
        <el-select v-model="deviceStore.formData.factoryName" placeholder="请选择" style="width:100%">
          <el-option label="Cisco" value="Cisco"></el-option>
          <el-option label="华为" value="华为"></el-option>
          <el-option label="H3C" value="H3C"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="设备型号" prop="deviceModel">
        <el-select v-model="deviceStore.formData.deviceModel" placeholder="请选择" style="width:100%">
          <el-option label="CBS110-16T-CN" value="CBS110-16T-CN"></el-option>
          <el-option label="CBS110-24T-CN" value="CBS110-24T-CN"></el-option>
          <el-option label="SG220-52-K9-CN" value="SG220-52-K9-CN"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="备注">
        <el-input v-model="deviceStore.formData.remark" type="textarea" placeholder="请输入内容"></el-input>
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
  import useDeviceStore from '../store'

  const { proxy } = getCurrentInstance() as any
  const deviceStore = useDeviceStore()
  
  const rules = {
    deviceName: [
      { required: true, message: "设备名称不能为空", trigger: "blur" },
    ],
    ip: [
      { required: true, message: "ip地址不能为空", trigger: "blur" },
    ],
    factoryName: [
      { required: true, message: "请选择厂商", trigger: "change" },
    ],
    deviceModel: [
      { required: true, message: "请选择设备型号", trigger: "change" },
    ]
  }
  
  // 提交按钮
  function submitForm() {
    proxy.$refs["deviceRef"].validate(async (valid: boolean) => {
      if (valid) {
        if (deviceStore.formData.roleId != undefined) {
          await deviceStore.updateDevice()
          ElMessage.success("修改成功")
          deviceStore.showFormDialog = false
          deviceStore.getDeviceData()
        } else {
          await deviceStore.addDevice()
          ElMessage.success("新增成功")
          deviceStore.showFormDialog = false
          deviceStore.getDeviceData()
        }
      }
    });
  }

  // 取消按钮
  function cancel() {
    deviceStore.resetFormData()
    deviceStore.showFormDialog = false
  }
</script>