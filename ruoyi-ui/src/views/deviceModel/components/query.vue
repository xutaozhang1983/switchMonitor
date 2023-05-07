<template>
  <el-form :model="deviceModelStore.queryParams" ref="queryRef" v-show="deviceModelStore.showSearch" :inline="true">
      <el-form-item label="型号名称" prop="roleName">
        <el-input v-model="deviceModelStore.queryParams.roleName" placeholder="请输入型号名称" clearable style="width: 240px" @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="deviceModelStore.queryParams.status" placeholder="型号状态" clearable style="width: 240px">
          <el-option v-for="dict in sys_normal_disable" :key="dict.value" :label="dict.label" :value="dict.value"/>
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间" style="width: 308px">
        <el-date-picker v-model="dateRange" value-format="YYYY-MM-DD" DeviceModel="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button DeviceModel="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
</template>

<script setup lang="ts">
  import useDeviceModelStore from '../store'
  import { dayjs } from 'element-plus'

  const { proxy } = getCurrentInstance() as any
  const deviceModelStore = useDeviceModelStore()

  const { sys_normal_disable }: { sys_normal_disable: DictType[] } = proxy.useDict(
    "sys_normal_disable"
  )

  const dateRange = ref([])

  /** 搜索按钮操作 */
  function handleQuery() {
    deviceModelStore.queryParams.pageNum = 1;
    if (dateRange.value.length) {
      deviceModelStore.queryParams.beginTime = dayjs().format(dateRange.value[0])
      deviceModelStore.queryParams.endTime = dayjs().format(dateRange.value[1])
    }
    deviceModelStore.getDeviceModelData()
  }

  // 重置按钮操作
  function resetQuery() {
    dateRange.value = []
    deviceModelStore.queryParams.beginTime = undefined
    deviceModelStore.queryParams.endTime = undefined
    proxy.resetForm("queryRef")
    handleQuery()
  }
</script>