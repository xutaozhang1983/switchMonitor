<template>
  <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="danger" plain icon="Delete" :disabled="operLogStore.multiple" @click="handleDelete" v-hasPermi="['audit:operlog:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="Delete" @click="handleClean" v-hasPermi="['audit:operlog:remove']">清空</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['audit:operlog:export']">导出</el-button>
      </el-col>
      <!--右侧工具条-->
      <right-toolbar v-model:showSearch="operLogStore.showSearch" @queryTable="operLogStore.getOperLogData"></right-toolbar>
    </el-row>
</template>

<script setup lang="ts">
  import { ElMessageBox, ElMessage } from 'element-plus'
  import useOperLogStore from '../store'

  const { proxy } = getCurrentInstance() as any
  const operLogStore = useOperLogStore()

  // 删除按钮操作
  function handleDelete() {
    ElMessageBox.confirm('是否确认删除日志编号为"' + operLogStore.ids + '"的数据项？', '系统提示', { type: 'warning' }).then(async () => {
      await operLogStore.delOperLog()
      operLogStore.getOperLogData()
      ElMessage.success("删除成功")
    }).catch(() => {})
  }

  /** 清空按钮操作 */
  function handleClean() {
    ElMessageBox.confirm('是否确认清空所有操作日志数据项?', '系统提示', { type: 'warning' }).then(async () => {
      await operLogStore.cleanOperLog()
      operLogStore.getOperLogData()
      proxy.$modal.msgSuccess('清空成功')
    }).catch(() => {})
  }

  // 导出按钮操作
  function handleExport() {
    proxy.download(
      "monitor/operlog/export",
      {
        ...operLogStore.queryParams,
      },
      `operLog_${new Date().getTime()}.xlsx`
    );
  }

</script>