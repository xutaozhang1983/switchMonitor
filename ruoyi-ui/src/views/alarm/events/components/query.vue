<template>
  <el-form :model="eventsStore.queryParams" ref="queryRef" v-show="eventsStore.showSearch" :inline="true">
      <el-form-item label="所在组" prop="groupId">
        <el-select v-model="groupId" placeholder="请选择" clearable style="width: 240px" @change="handleSelectDeviceGroup">
          <el-option v-for="(item, index) in eventsStore.deviceGroupData" :key="index" :label="item.groupName" :value="item.id"/>
        </el-select>
      </el-form-item>
      <el-form-item label="设备" prop="deviceId">
        <el-select v-model="eventsStore.queryParams.deviceId" placeholder="请选择" clearable style="width: 240px" :disabled="!showDeviceSelect">
          <el-option v-for="(item, index) in eventsStore.deviceData" :key="index" :label="item.deviceName" :value="item.id"/>
        </el-select>
      </el-form-item>
      <el-form-item label="告警级别" prop="alarmLevel">
        <el-select v-model="eventsStore.queryParams.alarmLevel" placeholder="请选择" clearable style="width: 240px">
          <el-option v-for="dict in sys_alarm_level" :key="dict.value" :label="dict.label" :value="dict.value"/>
        </el-select>
      </el-form-item>
      <el-form-item label="告警状态" prop="status">
        <el-select v-model="eventsStore.queryParams.status" placeholder="请选择" clearable style="width: 240px">
          <el-option v-for="dict in sys_alarm_status" :key="dict.value" :label="dict.label" :value="dict.value"/>
        </el-select>
      </el-form-item>
      <el-form-item label="告警时间">
        <el-date-picker
          v-model="timeRange"
          type="datetimerange"
          range-separator="至"
          start-placeholder="开始时间"
          end-placeholder="结束时间"
          format="YYYY-MM-DD HH:mm:ss"
          value-format="YYYY-MM-DD HH:mm:ss"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
</template>

<script setup lang="ts">
  import { ElForm } from 'element-plus'
  import useEventsStore from '../store'

  const { proxy } = getCurrentInstance() as any
  const eventsStore = useEventsStore()

  const queryRef = ref<InstanceType<typeof ElForm> | null>(null)

  const { sys_alarm_status, sys_alarm_level }: { sys_alarm_status: DictType[], sys_alarm_level: DictType[] } = proxy.useDict(
    "sys_alarm_status",
    "sys_alarm_level"
  )
  const groupId = ref(undefined)
  const showDeviceSelect = ref(false)
  const timeRange = ref([])

  // 选择设备组
  async function handleSelectDeviceGroup (groupId: number) {
    eventsStore.queryParams.deviceId = undefined
    await eventsStore.getDeviceData(groupId)
    showDeviceSelect.value = true
  }

  // 搜索按钮操作
  function handleQuery() {
    if (timeRange.value.length) {
      eventsStore.queryParams.startTime = timeRange.value[0]
      eventsStore.queryParams.endTime = timeRange.value[1]
    }
    eventsStore.queryParams.pageNum = 1
    eventsStore.getEventsData()
  }

  // 重置按钮操作
  function resetQuery() {
    groupId.value = undefined
    timeRange.value = []
    showDeviceSelect.value = false
    proxy.$refs.queryRef.resetFields()
    handleQuery()
  }
</script>