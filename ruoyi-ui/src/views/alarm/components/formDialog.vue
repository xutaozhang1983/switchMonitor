<template>
  <el-dialog :title="alarmStore.formTitle" v-model="alarmStore.showFormDialog" width="500px" append-to-body>
    <el-form ref="AlarmRef" :model="alarmStore.formData" :rules="rules" label-width="100px">
      <el-form-item label="告警名称" prop="alarmName">
        <el-input v-model="alarmStore.formData.alarmName" placeholder="请输入告警名称" />
      </el-form-item>
      <el-form-item label="备注">
        <el-input v-model="alarmStore.formData.remark" type="textarea" placeholder="请输入内容"></el-input>
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
  import usealarmStore from '../store'

  const { proxy } = getCurrentInstance() as any
  const alarmStore = usealarmStore()
  
  const rules = {
    alarmName: [
      { required: true, message: "告警名称不能为空", trigger: "blur" },
    ]
  }
  
  // 提交按钮
  function submitForm() {
    proxy.$refs["AlarmRef"].validate(async (valid: boolean) => {
      if (valid) {
        if (alarmStore.formData.roleId != undefined) {
          await alarmStore.updateAlarm()
          ElMessage.success("修改成功")
          alarmStore.showFormDialog = false
          alarmStore.getalarmData()
        } else {
          await alarmStore.addAlarm()
          ElMessage.success("新增成功")
          alarmStore.showFormDialog = false
          alarmStore.getalarmData()
        }
      }
    });
  }

  // 取消按钮
  function cancel() {
    alarmStore.resetFormData()
    alarmStore.showFormDialog = false
  }
</script>