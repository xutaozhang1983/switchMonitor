<template>
  <el-row :gutter="10" class="mb8">
    <el-col :span="1.5">
      <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['system:role:add']" >新增</el-button>
    </el-col>
    <el-col :span="1.5">
      <el-button type="success" plain icon="Edit" :disabled="deviceStore.single" @click="handleUpdate" v-hasPermi="['system:role:edit']">修改</el-button>
    </el-col>
    <el-col :span="1.5">
      <el-button type="danger" plain icon="Delete" :disabled="deviceStore.multiple" @click="handleDelete" v-hasPermi="['system:role:remove']">删除</el-button>
    </el-col>
    <el-col :span="1.5">
      <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['system:role:export']">导出</el-button>
    </el-col>
    <!--右侧工具条-->
    <right-toolbar v-model:showSearch="deviceStore.showSearch" @queryTable="deviceStore.getDeviceData"></right-toolbar>
  </el-row>
</template>

<script setup lang="ts">
  import { ElMessageBox, ElMessage } from 'element-plus'
  import useNetworkDeviceStore from '../store'

  const { proxy } = getCurrentInstance() as any
  const deviceStore = useNetworkDeviceStore()
  const emits = defineEmits(['setMenuChecked'])

  // 新增按钮操作
  async function handleAdd() {
    deviceStore.resetFormData()
    deviceStore.showFormDialog = true
    deviceStore.formTitle = "添加设备"
  }

  // 修改按钮操作
  async function handleUpdate() {
    deviceStore.resetFormData()
    await deviceStore.getDeviceInfo(deviceStore.ids[0])
    deviceStore.formTitle = "修改设备"
    deviceStore.showFormDialog = true
    emits('setMenuChecked')
  }

  // 删除按钮操作
  function handleDelete() {
    ElMessageBox.confirm('是否确认删除设备编号为"' + deviceStore.ids + '"的数据项？', '系统提示', { type: 'warning' }).then(async () => {
      await deviceStore.delDevice()
      deviceStore.getDeviceData()
      ElMessage.success("删除成功")
    }).catch(() => {})
  }

  // 导出按钮操作
  function handleExport() {
    proxy.download(
      "monitor/device/export",
      {
        ...deviceStore.queryParams,
      },
      `device_${new Date().getTime()}.xlsx`
    );
  }

</script>