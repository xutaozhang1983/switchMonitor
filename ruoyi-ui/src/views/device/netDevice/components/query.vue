<template>
  <el-form :model="deviceStore.queryParams" ref="queryRef" v-show="deviceStore.showSearch" :inline="true">
      <el-form-item label="设备名称" prop="deviceName">
        <el-input v-model="deviceStore.queryParams.deviceName" placeholder="请输入" clearable style="width: 240px" @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="所在组" prop="groupId">
        <el-select v-model="deviceStore.queryParams.groupId" placeholder="请选择" clearable style="width: 240px">
          <el-option v-for="(item, index) in deviceStore.deviceGroupData" :key="index" :label="item.groupName" :value="item.id"/>
        </el-select>
      </el-form-item>
      <el-form-item label="IP地址" prop="deviceIp">
        <el-input v-model="deviceStore.queryParams.deviceIp" placeholder="请输入" clearable style="width: 240px" @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="设备状态" prop="status">
        <el-select v-model="deviceStore.queryParams.status" placeholder="请选择" clearable style="width: 240px">
          <el-option v-for="dict in dictNormalAbnormal" :key="dict.value" :label="dict.label" :value="dict.value"/>
        </el-select>
      </el-form-item>
      <el-form-item label="监控状态" prop="enable">
        <el-select v-model="deviceStore.queryParams.enable" placeholder="请选择" clearable style="width: 240px">
          <el-option v-for="dict in dictOpenClose" :key="dict.value" :label="dict.label" :value="dict.value"/>
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
  import useNetDeviceStore from '../store'
  import { dictNormalAbnormal, dictOpenClose } from '@/utils/dict'

  const { proxy } = getCurrentInstance() as any
  const deviceStore = useNetDeviceStore()

  const queryRef = ref<InstanceType<typeof ElForm> | null>(null)

  /** 搜索按钮操作 */
  function handleQuery() {
    deviceStore.queryParams.pageNum = 1;
    deviceStore.getDeviceData()
  }

  // 重置按钮操作
  function resetQuery() {
    proxy.$refs.queryRef.resetFields()
    handleQuery()
  }
</script>