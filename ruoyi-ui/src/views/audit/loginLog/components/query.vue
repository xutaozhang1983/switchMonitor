<template>
  <el-form :model="loginLogStore.queryParams" ref="queryRef" :inline="true" v-show="loginLogStore.showSearch" label-width="68px">
    <el-form-item label="登录地址" prop="ipaddr">
      <el-input v-model="loginLogStore.queryParams.ipaddr" placeholder="请输入登录地址" clearable style="width: 240px" @keyup.enter="handleQuery" />
    </el-form-item>
    <el-form-item label="用户名称" prop="userName">
      <el-input v-model="loginLogStore.queryParams.userName" placeholder="请输入用户名称" clearable style="width: 240px" @keyup.enter="handleQuery" />
    </el-form-item>
    <el-form-item label="状态" prop="status">
      <el-select v-model="loginLogStore.queryParams.status" placeholder="登录状态" clearable style="width: 240px">
        <el-option v-for="dict in dictSuccessFail" :key="dict.value" :label="dict.label" :value="dict.value" />
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
  import useLoginLogStore from '../store'
  import { dictSuccessFail } from '@/utils/dict'

  const { proxy } = getCurrentInstance() as any
  const loginLogStore = useLoginLogStore()

  const queryRef = ref<InstanceType<typeof ElForm> | null>(null)

  /** 搜索按钮操作 */
  function handleQuery() {
    loginLogStore.queryParams.pageNum = 1
    loginLogStore.getLoginLogData()
  }

  // 重置按钮操作
  function resetQuery() {
    proxy.$refs.queryRef.resetFields()
    handleQuery()
  }
</script>