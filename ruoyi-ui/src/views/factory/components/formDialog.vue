<template>
  <el-dialog :title="factoryStore.formTitle" v-model="factoryStore.showFormDialog" width="500px" append-to-body>
    <el-form ref="factoryRef" :model="factoryStore.formData" :rules="rules" label-width="100px">
      <el-form-item label="厂商名称" prop="factoryName">
        <el-input v-model="factoryStore.formData.factoryName" placeholder="请输入厂商名称" />
      </el-form-item>
      <el-form-item label="备注">
        <el-input v-model="factoryStore.formData.remark" type="textarea" placeholder="请输入内容"></el-input>
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
  import useFactoryStore from '../store'

  const { proxy } = getCurrentInstance() as any
  const factoryStore = useFactoryStore()
  
  const rules = {
    factoryName: [
      { required: true, message: "厂商名称不能为空", trigger: "blur" },
    ]
  }
  
  // 提交按钮
  function submitForm() {
    proxy.$refs["factoryRef"].validate(async (valid: boolean) => {
      if (valid) {
        if (factoryStore.formData.roleId != undefined) {
          await factoryStore.updateFactory()
          ElMessage.success("修改成功")
          factoryStore.showFormDialog = false
          factoryStore.getFactoryData()
        } else {
          await factoryStore.addFactory()
          ElMessage.success("新增成功")
          factoryStore.showFormDialog = false
          factoryStore.getFactoryData()
        }
      }
    });
  }

  // 取消按钮
  function cancel() {
    factoryStore.resetFormData()
    factoryStore.showFormDialog = false
  }
</script>