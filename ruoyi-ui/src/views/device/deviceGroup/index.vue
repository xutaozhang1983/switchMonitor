<template>
  <div class="app-container">
    <!--查询条件-->
    <Query></Query>
    <!--操作区域-->
    <Operate></Operate>
    <!--设备组数据-->
    <DataTable></DataTable>
    <!-- 添加、修改设备组配置对话框 -->
    <FormDialog ref="formDialog"></FormDialog>
  </div>
</template>

<script setup name="DeviceGroup" lang="ts">
  import Query from './components/query.vue'
  import Operate from './components/operate.vue'
  import DataTable from './components/dataTable.vue'
  import FormDialog from './components/formDialog.vue'
  import useDeviceGroupStore from './store'

  const deviceGroupStore = useDeviceGroupStore()

  onBeforeMount(async () => {
    // 获取用户数据
    await deviceGroupStore.getUserData()
    // 获取设备组数据
    await deviceGroupStore.getDeviceGroupData()
  })

</script>
