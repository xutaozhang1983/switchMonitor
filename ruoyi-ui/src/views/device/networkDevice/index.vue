<template>
  <div class="app-container">
    <!--查询条件-->
    <Query></Query>
    <!--操作区域-->
    <Operate></Operate>
    <!--设备数据-->
    <DataTable></DataTable>
    <!-- 添加、修改设备配置对话框 -->
    <FormDialog ref="formDialog"></FormDialog>
  </div>
</template>

<script setup name="NetworkDevice" lang="ts">
  import Query from './components/query.vue'
  import Operate from './components/operate.vue'
  import DataTable from './components/dataTable.vue'
  import FormDialog from './components/formDialog.vue'
  import useNetworkDeviceStore from './store'

  const deviceStore = useNetworkDeviceStore()

  onBeforeMount(async () => {
    // 获取设备组数据
    await deviceStore.getDeviceGroupData()
    // 获取设备数据
    await deviceStore.getDeviceData()
  })

</script>
