<template>
  <el-form :model="postStore.queryParams" ref="queryRef" :inline="true" v-show="postStore.showSearch" label-width="68px">
    <el-form-item label="岗位编码" prop="postCode">
      <el-input v-model="postStore.queryParams.postCode" placeholder="请输入岗位编码" clearable @keyup.enter="handleQuery" />
    </el-form-item>
    <el-form-item label="岗位名称" prop="postName">
      <el-input v-model="postStore.queryParams.postName" placeholder="请输入岗位名称" clearable @keyup.enter="handleQuery" />
    </el-form-item>
    <el-form-item label="状态" prop="status">
      <el-select v-model="postStore.queryParams.status" placeholder="岗位状态" clearable>
        <el-option v-for="dict in sys_normal_disable" :key="dict.value" :label="dict.label" :value="dict.value" />
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
  import usePosttStore from '../store'
  
  const { proxy } = getCurrentInstance() as any
  const postStore = usePosttStore()

  const queryRef = ref<InstanceType<typeof ElForm> | null>(null)

  const { sys_normal_disable }: { sys_normal_disable: DictType[] } = proxy.useDict(
    "sys_normal_disable"
  )

  // 搜索按钮操作
  function handleQuery() {
    postStore.queryParams.pageNum = 1
    postStore.getPostData()
  }

  // 重置按钮操作
  function resetQuery() {
    proxy.$refs.queryRef.resetFields()
    handleQuery()
  }
</script>