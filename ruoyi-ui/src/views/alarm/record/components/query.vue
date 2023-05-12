<template>
  <el-form :model="recordStore.queryParams" ref="queryRef" v-show="recordStore.showSearch" :inline="true">
      <el-form-item label="发送时间">
        <el-date-picker
          v-model="timeRange"
          type="datetimerange"
          range-separator="至"
          start-placeholder="开始时间"
          end-placeholder="结束时间"
          format="YYYY-MM-DD HH:mm:ss"
          value-format="YYYY-MM-DD HH:mm:ss"/>
      </el-form-item>
      <el-form-item label="发送人" prop="userName">
        <el-input v-model="recordStore.queryParams.userName" placeholder="请输入" clearable style="width: 240px" @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="发送状态" prop="status">
        <el-select v-model="recordStore.queryParams.status" placeholder="请选择" clearable style="width: 240px">
          <el-option v-for="dict in sys_common_status" :key="dict.value" :label="dict.label" :value="dict.value"/>
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
  import useRecordStore from '../store'

  const { proxy } = getCurrentInstance() as any
  const recordStore = useRecordStore()

  const queryRef = ref<InstanceType<typeof ElForm> | null>(null)

  const { sys_common_status }: { sys_common_status: DictType[] } = proxy.useDict(
    "sys_common_status"
  )

  const timeRange = ref([])

  // 搜索按钮操作
  function handleQuery() {
    if (timeRange.value.length) {
      recordStore.queryParams.startTime = timeRange.value[0]
      recordStore.queryParams.endTime = timeRange.value[1]
    }
    recordStore.queryParams.pageNum = 1
    recordStore.getRecordData()
  }

  // 重置按钮操作
  function resetQuery() {
    timeRange.value = []
    proxy.$refs.queryRef.resetFields()
    handleQuery()
  }
</script>