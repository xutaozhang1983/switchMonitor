<template>
  <el-form :model="deviceGroupStore.queryParams" ref="queryRef" v-show="deviceGroupStore.showSearch" :inline="true">
      <el-form-item label="名称" prop="groupName">
        <el-input v-model="deviceGroupStore.queryParams.groupName" placeholder="请输入" clearable @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="deviceGroupStore.queryParams.status" placeholder="请选择" clearable style="width: 240px">
          <el-option v-for="dict in dictNormalAbnormal" :key="dict.value" :label="dict.label" :value="dict.value"/>
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
  import useDeviceGroupStore from '../store'
  import { dictNormalAbnormal } from '@/utils/dict'

  const { proxy } = getCurrentInstance() as any
  const deviceGroupStore = useDeviceGroupStore()

  const queryRef = ref<InstanceType<typeof ElForm> | null>(null)

  /** 搜索按钮操作 */
  function handleQuery() {
    deviceGroupStore.queryParams.pageNum = 1
    deviceGroupStore.getDeviceGroupData()
  }

  // 重置按钮操作
  function resetQuery() {
    deviceGroupStore.queryParams.groupName = undefined
    proxy.$refs.queryRef.resetFields()
    handleQuery()
  }
</script>