<template>
  <el-row :gutter="10" class="mb8">
    <el-col :span="1.5">
      <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['system:role:add']" >新增</el-button>
    </el-col>
    <el-col :span="1.5">
      <el-button type="success" plain icon="Edit" :disabled="deviceModelStore.single" @click="handleUpdate" v-hasPermi="['system:role:edit']">修改</el-button>
    </el-col>
    <el-col :span="1.5">
      <el-button type="danger" plain icon="Delete" :disabled="deviceModelStore.multiple" @click="handleDelete" v-hasPermi="['system:role:remove']">删除</el-button>
    </el-col>
    <el-col :span="1.5">
      <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['system:role:export']">导出</el-button>
    </el-col>
    <!--右侧工具条-->
    <right-toolbar v-model:showSearch="deviceModelStore.showSearch" @queryTable="deviceModelStore.getDeviceModelData"></right-toolbar>
  </el-row>
</template>

<script setup lang="ts">
  import { ElMessageBox, ElMessage } from 'element-plus'
  import useDeviceModelStore from '../store'

  const { proxy } = getCurrentInstance() as any
  const deviceModelStore = useDeviceModelStore()
  const emits = defineEmits(['setMenuChecked'])

  // 新增按钮操作
  async function handleAdd() {
    deviceModelStore.resetFormData()
    deviceModelStore.showFormDialog = true
    deviceModelStore.formTitle = "添加型号"
  }

  // 修改按钮操作
  async function handleUpdate() {
    deviceModelStore.resetFormData()
    await deviceModelStore.getDeviceModelInfo(deviceModelStore.ids[0])
    deviceModelStore.formTitle = "修改型号"
    deviceModelStore.showFormDialog = true
    emits('setMenuChecked')
  }

  // 删除按钮操作
  function handleDelete() {
    ElMessageBox.confirm('是否确认删除型号编号为"' + deviceModelStore.ids + '"的数据项？', '系统提示', { type: 'warning' }).then(async () => {
      await deviceModelStore.delDeviceModel()
      deviceModelStore.getDeviceModelData()
      ElMessage.success("删除成功")
    }).catch(() => {})
  }

  // 导出按钮操作
  function handleExport() {
    proxy.download(
      "system/role/export",
      {
        ...deviceModelStore.queryParams,
      },
      `role_${new Date().getTime()}.xlsx`
    );
  }

</script>