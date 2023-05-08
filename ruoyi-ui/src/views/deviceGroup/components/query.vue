<template>
  <el-form :model="deviceGroupStore.queryParams" ref="queryRef" v-show="deviceGroupStore.showSearch" :inline="true">
      <el-form-item label="设备组名称" prop="groupName">
        <el-input v-model="deviceGroupStore.queryParams.groupName" placeholder="请输入" clearable @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
</template>

<script setup lang="ts">
  import useDeviceGroupStore from '../store'

  const { proxy } = getCurrentInstance() as any
  const deviceGroupStore = useDeviceGroupStore()

  /** 搜索按钮操作 */
  function handleQuery() {
    deviceGroupStore.queryParams.pageNum = 1
    deviceGroupStore.getDeviceGroupData()
  }

  // 重置按钮操作
  function resetQuery() {
    deviceGroupStore.queryParams.groupName = undefined
    proxy.resetForm("queryRef")
    handleQuery()
  }
</script>