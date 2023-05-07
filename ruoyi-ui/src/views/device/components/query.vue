<template>
  <el-form :model="deviceStore.queryParams" ref="queryRef" v-show="deviceStore.showSearch" :inline="true">
      <el-form-item label="设备名称" prop="roleName">
        <el-input v-model="deviceStore.queryParams.roleName" placeholder="请输入设备名称" clearable style="width: 240px" @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="deviceStore.queryParams.status" placeholder="设备状态" clearable style="width: 240px">
          <el-option v-for="dict in sys_device_status" :key="dict.value" :label="dict.label" :value="dict.value"/>
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

  const { sys_device_status }: { sys_device_status: DictType[] } = proxy.useDict(
    "sys_device_status"
  )

  const dateRange = ref([])

  /** 搜索按钮操作 */
  function handleQuery() {
    deviceStore.queryParams.pageNum = 1;
    if (dateRange.value.length) {
      deviceStore.queryParams.beginTime = dayjs().format(dateRange.value[0])
      deviceStore.queryParams.endTime = dayjs().format(dateRange.value[1])
    }
    deviceStore.getDeviceData()
  }

  // 重置按钮操作
  function resetQuery() {
    dateRange.value = []
    deviceStore.queryParams.beginTime = undefined
    deviceStore.queryParams.endTime = undefined
    proxy.resetForm("queryRef")
    handleQuery()
  }
</script>