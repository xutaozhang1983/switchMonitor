<template>
  <el-form :model="deptStore.queryParams" ref="queryRef" :inline="true" v-show="deptStore.showSearch">
    <el-form-item label="部门名称" prop="deptName">
      <el-input v-model="deptStore.queryParams.deptName" placeholder="请输入部门名称" clearable @keyup.enter="handleQuery" />
    </el-form-item>
    <el-form-item label="状态" prop="status">
      <el-select v-model="deptStore.queryParams.status" placeholder="部门状态" clearable>
        <el-option v-for="dict in dictNormalDisable" :key="dict.value" :label="dict.label" :value="dict.value" />
      </el-select>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
      <el-button icon="Refresh" @click="resetQuery">重置</el-button>
    </el-form-item>
  </el-form>
</template>

<script setup lang="ts">
  import { ElForm } from 'element-plus'
  import useDeptStore from '../store'
  import { dictNormalDisable } from '@/utils/dict'
  
  const { proxy } = getCurrentInstance() as any
  const deptStore = useDeptStore()

  const queryRef = ref<InstanceType<typeof ElForm> | null>(null)

  // 搜索按钮操作
  function handleQuery() {
    deptStore.getDeptData()
  }

  // 重置按钮操作
  function resetQuery() {
    proxy.$refs.queryRef.resetFields()
    handleQuery()
  }
</script>