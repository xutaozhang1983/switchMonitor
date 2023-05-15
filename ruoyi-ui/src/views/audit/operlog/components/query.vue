<template>
  <el-form :model="operLogStore.queryParams" ref="queryRef" :inline="true" v-show="operLogStore.showSearch" label-width="68px">
    <el-form-item label="系统模块" prop="title">
      <el-input v-model="operLogStore.queryParams.title" placeholder="请输入系统模块" clearable style="width: 240px" @keyup.enter="handleQuery" />
    </el-form-item>
    <el-form-item label="操作人员" prop="operName">
      <el-input v-model="operLogStore.queryParams.operName" placeholder="请输入操作人员" clearable style="width: 240px" @keyup.enter="handleQuery" />
    </el-form-item>
    <el-form-item label="类型" prop="businessType">
      <el-select v-model="operLogStore.queryParams.businessType" placeholder="操作类型" clearable style="width: 240px">
        <el-option v-for="dict in sys_oper_type" :key="dict.value" :label="dict.label" :value="dict.value" />
      </el-select>
    </el-form-item>
    <el-form-item label="状态" prop="status">
      <el-select v-model="operLogStore.queryParams.status" placeholder="操作状态" clearable style="width: 240px">
        <el-option v-for="dict in sys_common_status" :key="dict.value" :label="dict.label" :value="dict.value" />
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
  import useOperLogStore from '../store'

  const { proxy } = getCurrentInstance() as any
  const operLogStore = useOperLogStore()

  const queryRef = ref<InstanceType<typeof ElForm> | null>(null)

  const { sys_oper_type, sys_common_status }: { sys_oper_type: DictType[], sys_common_status: DictType[] } = proxy.useDict(
    "sys_oper_type",
    "sys_common_status"
  )

  /** 搜索按钮操作 */
  function handleQuery() {
    operLogStore.queryParams.pageNum = 1
    operLogStore.getOperLogData()
  }

  // 重置按钮操作
  function resetQuery() {
    proxy.$refs.queryRef.resetFields()
    handleQuery()
  }
</script>