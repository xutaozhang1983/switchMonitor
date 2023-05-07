<template>
  <el-form :model="alarmStore.queryParams" ref="queryRef" v-show="alarmStore.showSearch" :inline="true">
      <el-form-item label="关键字" prop="roleName">
        <el-input v-model="alarmStore.queryParams.roleName" placeholder="请输入" clearable style="width: 240px" @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
</template>

<script setup lang="ts">
  import usealarmStore from '../store'
  import { dayjs } from 'element-plus'

  const { proxy } = getCurrentInstance() as any
  const alarmStore = usealarmStore()


  const dateRange = ref([])

  /** 搜索按钮操作 */
  function handleQuery() {
    alarmStore.queryParams.pageNum = 1;
    if (dateRange.value.length) {
      alarmStore.queryParams.beginTime = dayjs().format(dateRange.value[0])
      alarmStore.queryParams.endTime = dayjs().format(dateRange.value[1])
    }
    alarmStore.getalarmData()
  }

  // 重置按钮操作
  function resetQuery() {
    dateRange.value = []
    alarmStore.queryParams.beginTime = undefined
    alarmStore.queryParams.endTime = undefined
    proxy.resetForm("queryRef")
    handleQuery()
  }
</script>