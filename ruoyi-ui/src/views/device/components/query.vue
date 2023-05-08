<template>
  <el-form :model="deviceStore.queryParams" ref="queryRef" v-show="deviceStore.showSearch" :inline="true">
      <el-form-item label="名称" prop="roleName">
        <el-input v-model="deviceStore.queryParams.deviceName" placeholder="请输入" clearable style="width: 240px" @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="IP地址" prop="roleName">
        <el-input v-model="deviceStore.queryParams.deviceIp" placeholder="请输入" clearable style="width: 240px" @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="设备状态" prop="status">
        <el-select v-model="deviceStore.queryParams.status" placeholder="状态" clearable style="width: 240px">
          <el-option v-for="dict in sys_device_status" :key="dict.value" :label="dict.label" :value="dict.value"/>
        </el-select>
      </el-form-item>
      <el-form-item label="监控状态" prop="status">
        <el-select v-model="deviceStore.queryParams.status" placeholder="状态" clearable style="width: 240px">
          <el-option v-for="dict in sys_normal_disable" :key="dict.value" :label="dict.label" :value="dict.value"/>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
</template>

<script setup lang="ts">
  import useDeviceStore from '../store'
  import { dayjs } from 'element-plus'

  const { proxy } = getCurrentInstance() as any
  const deviceStore = useDeviceStore()

  const { sys_device_status, sys_normal_disable }: { sys_device_status: DictType[], sys_normal_disable: DictType[] } = proxy.useDict(
    "sys_device_status",
    "sys_normal_disable"
  )

  const dateRange = ref([])

  /** 搜索按钮操作 */
  function handleQuery() {
    deviceStore.queryParams.pageNum = 1;
    deviceStore.getDeviceData()
  }

  // 重置按钮操作
  function resetQuery() {
    dateRange.value = []
    proxy.resetForm("queryRef")
    handleQuery()
  }
</script>