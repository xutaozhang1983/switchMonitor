<template>
  <el-row :gutter="10" class="mb8">
    <el-col :span="1.5">
      <el-button type="danger" plain icon="Delete" :disabled="loginLogStore.multiple" @click="handleDelete" v-hasPermi="['audit:logininfor:remove']">删除</el-button>
    </el-col>
    <el-col :span="1.5">
      <el-button type="danger" plain icon="Delete" @click="handleClean" v-hasPermi="['audit:logininfor:remove']">清空</el-button>
    </el-col>
    <el-col :span="1.5">
      <el-button type="primary" plain icon="Unlock" :disabled="loginLogStore.single" @click="handleUnlock" v-hasPermi="['audit:logininfor:unlock']">解锁</el-button>
    </el-col>
    <el-col :span="1.5">
      <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['audit:logininfor:export']">导出</el-button>
    </el-col>
    <!--右侧工具栏-->
    <right-toolbar v-model:showSearch="loginLogStore.showSearch" @queryTable="loginLogStore.getLoginLogData"></right-toolbar>
  </el-row>
</template>

<script setup lang="ts">
  import { ElMessageBox, ElMessage } from 'element-plus'
  import useLoginLogStore from '../store'

  const { proxy } = getCurrentInstance() as any
  const loginLogStore = useLoginLogStore()

  // 删除按钮操作
  function handleDelete() {
    ElMessageBox.confirm('是否确认删除访问编号为"' + loginLogStore.ids + '"的数据项？', '系统提示', { type: 'warning' }).then(async () => {
      await loginLogStore.delLoginLog()
      loginLogStore.getLoginLogData()
      ElMessage.success("删除成功")
    }).catch(() => {})
  }

  /** 清空按钮操作 */
  function handleClean() {
    ElMessageBox.confirm('是否确认清空所有登录日志数据项?', '系统提示', { type: 'warning' }).then(async () => {
      await loginLogStore.cleanLoginLog()
      loginLogStore.getLoginLogData()
      proxy.$modal.msgSuccess('清空成功')
    }).catch(() => {})
  }

  /** 解锁按钮操作 */
  function handleUnlock() {
    ElMessageBox.confirm('是否确认解锁用户"' + loginLogStore.selectName + '"数据项?', { type: 'warning' }).then(async () => {
      await loginLogStore.unlockAccount()
      loginLogStore.getLoginLogData()
      proxy.$modal.msgSuccess(`${loginLogStore.selectName}解锁成功`)
    }).catch(() => {})
  }

  // 导出按钮操作
  function handleExport() {
    proxy.download(
      "monitor/logininfor/export",
      {
        ...loginLogStore.queryParams,
      },
      `loginLog_${new Date().getTime()}.xlsx`
    );
  }

</script>