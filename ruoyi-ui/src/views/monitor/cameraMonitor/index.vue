<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--编码设备数据-->
      <el-col :span="4" :xs="24">
        <EncodeTree></EncodeTree>
      </el-col>
      <el-col :span="20" :xs="24">
        <!--查询条件-->
        <Query></Query>
        <!--用户数据-->
        <DataTable></DataTable>
      </el-col>
    </el-row>
  </div>
</template>

<script setup name="NetMonitor" lang="ts">
  import EncodeTree from './components/EncodeTree.vue'
  import Query from './components/query.vue'
  import DataTable from './components/dataTable.vue'
  import useCameraMonitorStore from './store'

  const deviceStore = useCameraMonitorStore()

  onBeforeMount(async () => {
    // 获取编码设备数据
    await deviceStore.getEncodeData()
    // 获取摄像头数据
    await deviceStore.getCameraData()
  })

</script>
