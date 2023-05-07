<template>
  <el-row :gutter="10" class="mb8">
    <el-col :span="1.5">
      <el-button type="primary" plain round icon="CircleCheck">所有</el-button>
    </el-col>
    <el-col :span="1.5">
      <el-button type="success" plain round icon="CircleCheck">正常</el-button>
    </el-col>
    <el-col :span="1.5">
      <el-button type="warning" plain round icon="Warning">注意</el-button>
    </el-col>
    <el-col :span="1.5">
      <el-button type="danger" plain round icon="CircleClose">严重</el-button>
    </el-col>
    <!--右侧工具条-->
    <right-toolbar v-model:showSearch="alarmStore.showSearch" @queryTable="alarmStore.getalarmData"></right-toolbar>
  </el-row>
</template>

<script setup lang="ts">
  import { ElMessageBox, ElMessage } from 'element-plus'
  import usealarmStore from '../store'

  const { proxy } = getCurrentInstance() as any
  const alarmStore = usealarmStore()
  const emits = defineEmits(['setMenuChecked'])

  // 新增按钮操作
  async function handleAdd() {
    alarmStore.resetFormData()
    alarmStore.showFormDialog = true
    alarmStore.formTitle = "添加告警"
  }

  // 修改按钮操作
  async function handleUpdate() {
    alarmStore.resetFormData()
    await alarmStore.getAlarmInfo(alarmStore.ids[0])
    alarmStore.formTitle = "修改告警"
    alarmStore.showFormDialog = true
    emits('setMenuChecked')
  }

  // 删除按钮操作
  function handleDelete() {
    ElMessageBox.confirm('是否确认删除告警编号为"' + alarmStore.ids + '"的数据项？', '系统提示', { type: 'warning' }).then(async () => {
      await alarmStore.delAlarm()
      alarmStore.getalarmData()
      ElMessage.success("删除成功")
    }).catch(() => {})
  }

  // 导出按钮操作
  function handleExport() {
    proxy.download(
      "system/role/export",
      {
        ...alarmStore.queryParams,
      },
      `role_${new Date().getTime()}.xlsx`
    );
  }

</script>