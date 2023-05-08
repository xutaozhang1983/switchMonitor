<template>
  <el-row :gutter="10" class="mb8">
    <el-col :span="1.5">
      <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['system:role:add']" >新增</el-button>
    </el-col>
    <el-col :span="1.5">
      <el-button type="success" plain icon="Edit" :disabled="deviceGroupStore.single" @click="handleUpdate" v-hasPermi="['system:role:edit']">修改</el-button>
    </el-col>
    <el-col :span="1.5">
      <el-button type="danger" plain icon="Delete" :disabled="deviceGroupStore.multiple" @click="handleDelete" v-hasPermi="['system:role:remove']">删除</el-button>
    </el-col>
    <!--<el-col :span="1.5">
      <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['system:role:export']">导出</el-button>
    </el-col>-->
    <!--右侧工具条-->
    <right-toolbar v-model:showSearch="deviceGroupStore.showSearch" @queryTable="deviceGroupStore.getDeviceGroupData"></right-toolbar>
  </el-row>
</template>

<script setup lang="ts">
  import { ElMessageBox, ElMessage } from 'element-plus'
  import useDeviceGroupStore from '../store'

  const { proxy } = getCurrentInstance() as any
  const deviceGroupStore = useDeviceGroupStore()
  const emits = defineEmits(['setMenuChecked'])

  // 检测多选列表是否有存在设备的组
  function isExistsDevice () {
    for (let item of deviceGroupStore.ids) {
      if (deviceGroupStore.deviceGroupData.find((group: any) => group.id === item && group.deviceCount > 0)) {
        return item
      }
    }
    return 0
  }

  // 新增按钮操作
  async function handleAdd() {
    deviceGroupStore.resetFormData()
    deviceGroupStore.showFormDialog = true
    deviceGroupStore.formTitle = "添加设备组"
  }

  // 修改按钮操作
  async function handleUpdate() {
    deviceGroupStore.resetFormData()
    await deviceGroupStore.getDeviceGroupInfo(deviceGroupStore.ids[0])
    deviceGroupStore.formTitle = "修改设备组"
    deviceGroupStore.showFormDialog = true
    emits('setMenuChecked')
  }

  // 删除按钮操作
  function handleDelete() {
    let group = isExistsDevice()
    if (group) {
      ElMessage.warning(`设备组${group}存在设备，不允许删除`)
      return
    }
    ElMessageBox.confirm('是否确认删除设备组编号为"' + deviceGroupStore.ids + '"的数据项？', '系统提示', { type: 'warning' }).then(async () => {
      await deviceGroupStore.delDeviceGroup()
      deviceGroupStore.getDeviceGroupData()
      ElMessage.success("删除成功")
    }).catch(() => {})
  }

  // 导出按钮操作
  function handleExport() {
    proxy.download(
      "monitor/group/export",
      {
        ...deviceGroupStore.queryParams,
      },
      `group_${new Date().getTime()}.xlsx`
    );
  }

</script>