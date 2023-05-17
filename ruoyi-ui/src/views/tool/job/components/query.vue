<template>
  <el-form :model="jobStore.queryParams" ref="queryRef" :inline="true" v-show="jobStore.showSearch" label-width="68px">
    <el-form-item label="任务名称" prop="jobName">
      <el-input v-model="jobStore.queryParams.jobName" placeholder="请输入任务名称" clearable @keyup.enter="handleQuery" />
    </el-form-item>
    <el-form-item label="任务组名" prop="jobGroup">
      <el-select v-model="jobStore.queryParams.jobGroup" placeholder="请选择任务组名" clearable>
        <el-option v-for="dict in dictJobGroup" :key="dict.value" :label="dict.label" :value="dict.value" />
      </el-select>
    </el-form-item>
    <el-form-item label="任务状态" prop="status">
      <el-select v-model="jobStore.queryParams.status" placeholder="请选择任务状态" clearable>
        <el-option v-for="dict in dictNormalPause" :key="dict.value" :label="dict.label" :value="dict.value" />
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
  import useJobtStore from '../store'
  import { dictJobGroup, dictNormalPause } from '@/utils/dict'
  
  const { proxy } = getCurrentInstance() as any
  const jobStore = useJobtStore()

  const queryRef = ref<InstanceType<typeof ElForm> | null>(null)

  // 搜索按钮操作
  function handleQuery() {
    jobStore.queryParams.pageNum = 1
    jobStore.getJobData()
  }

  // 重置按钮操作
  function resetQuery() {
    proxy.$refs.queryRef.resetFields()
    handleQuery()
  }
</script>