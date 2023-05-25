<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--设备组数据-->
      <el-col :span="4" :xs="24">
        <DeviceGroup></DeviceGroup>
      </el-col>
      <el-col :span="20" :xs="24">
        <!--查询条件-->
        <Query></Query>
        <!--操作区域-->
        <Operate></Operate>
        <!--用户数据-->
        <DataTable></DataTable>
      </el-col>
    </el-row>
  </div>
</template>

<script setup name="NetMonitor" lang="ts">
  import DeviceGroup from './components/deviceGroup.vue'
  import Query from './components/query.vue'
  import DataTable from './components/dataTable.vue'
  import useNetMonitorStore from './store'

  const deviceStore = useNetMonitorStore()

  onBeforeMount(async () => {
    // 获取设备组数据
    await deviceStore.getDeviceGroupData()
    // 获取设备数据
    await deviceStore.getDeviceData()
  })

</script>
