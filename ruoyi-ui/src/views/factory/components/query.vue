<template>
  <el-form :model="factoryStore.queryParams" ref="queryRef" v-show="factoryStore.showSearch" :inline="true">
      <el-form-item label="厂商名称" prop="roleName">
        <el-input v-model="factoryStore.queryParams.roleName" placeholder="请输入厂商名称" clearable style="width: 240px" @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
</template>

<script setup lang="ts">
  import useFactoryStore from '../store'
  import { dayjs } from 'element-plus'

  const { proxy } = getCurrentInstance() as any
  const factoryStore = useFactoryStore()


  const dateRange = ref([])

  /** 搜索按钮操作 */
  function handleQuery() {
    factoryStore.queryParams.pageNum = 1;
    if (dateRange.value.length) {
      factoryStore.queryParams.beginTime = dayjs().format(dateRange.value[0])
      factoryStore.queryParams.endTime = dayjs().format(dateRange.value[1])
    }
    factoryStore.getFactoryData()
  }

  // 重置按钮操作
  function resetQuery() {
    dateRange.value = []
    factoryStore.queryParams.beginTime = undefined
    factoryStore.queryParams.endTime = undefined
    proxy.resetForm("queryRef")
    handleQuery()
  }
</script>