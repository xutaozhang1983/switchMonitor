<template>
  <el-form :model="genStore.queryParams" ref="queryRef" :inline="true" v-show="genStore.showSearch" label-width="68px">
    <el-form-item label="表名称" prop="tableName">
      <el-input v-model="genStore.queryParams.tableName" placeholder="请输入表名称" clearable @keyup.enter="handleQuery" />
    </el-form-item>
    <el-form-item label="表描述" prop="tableComment">
      <el-input v-model="genStore.queryParams.tableComment" placeholder="请输入表描述" clearable @keyup.enter="handleQuery" />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
      <el-button icon="Refresh" @click="resetQuery">重置</el-button>
    </el-form-item>
  </el-form>
</template>

<script setup lang="ts">
  import { ElForm } from 'element-plus'
  import useGentStore from '../store'
  
  const { proxy } = getCurrentInstance() as any
  const genStore = useGentStore()

  const queryRef = ref<InstanceType<typeof ElForm> | null>(null)

  // 搜索按钮操作
  function handleQuery() {
    genStore.queryParams.pageNum = 1
    genStore.getGenData()
  }

  // 重置按钮操作
  function resetQuery() {
    proxy.$refs.queryRef.resetFields()
    handleQuery()
  }

  defineExpose({
    handleQuery
  })

</script>