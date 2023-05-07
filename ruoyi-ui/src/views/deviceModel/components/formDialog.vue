<template>
  <el-dialog :title="deviceModelStore.formTitle" v-model="deviceModelStore.showFormDialog" width="500px" append-to-body>
    <el-form ref="deviceModelRef" :model="deviceModelStore.formData" :rules="rules" label-width="100px">
      <el-form-item label="型号名称" prop="deviceModelName">
        <el-input v-model="deviceModelStore.formData.deviceModelName" placeholder="请输入" />
      </el-form-item>
      <el-form-item label="接口数量" prop="port">
        <el-input v-model="deviceModelStore.formData.port" placeholder="请输入" />
      </el-form-item>
      <el-form-item label="厂商" prop="factoryName">
        <el-select v-model="deviceModelStore.formData.factoryName" placeholder="请选择" style="width:100%">
          <el-option label="Cisco" value="Cisco"></el-option>
          <el-option label="华为" value="华为"></el-option>
          <el-option label="H3C" value="H3C"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="参数" prop="params">
        <el-input v-model="deviceModelStore.formData.params" type="textarea" placeholder="请输入" />
      </el-form-item>
      <el-form-item label="备注">
        <el-input v-model="deviceModelStore.formData.remark" type="textarea" placeholder="请输入"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button DeviceModel="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
  import { ElMessage } from 'element-plus'
  import useDeviceModelStore from '../store'

  const { proxy } = getCurrentInstance() as any
  const deviceModelStore = useDeviceModelStore()
  
  const rules = {
    deviceModelName: [
      { required: true, message: "型号名称不能为空", trigger: "blur" },
    ],
    port: [
      { required: true, message: "接口数量不能为空", trigger: "blur" },
    ],
    factoryName: [
      { required: true, message: "请选择厂商", trigger: "change" },
    ]
  }
  
  // 提交按钮
  function submitForm() {
    proxy.$refs["deviceModelRef"].validate(async (valid: boolean) => {
      if (valid) {
        if (deviceModelStore.formData.roleId != undefined) {
          await deviceModelStore.updateDeviceModel()
          ElMessage.success("修改成功")
          deviceModelStore.showFormDialog = false
          deviceModelStore.getDeviceModelData()
        } else {
          await deviceModelStore.addDeviceModel()
          ElMessage.success("新增成功")
          deviceModelStore.showFormDialog = false
          deviceModelStore.getDeviceModelData()
        }
      }
    });
  }

  // 取消按钮
  function cancel() {
    deviceModelStore.resetFormData()
    deviceModelStore.showFormDialog = false
  }
</script>