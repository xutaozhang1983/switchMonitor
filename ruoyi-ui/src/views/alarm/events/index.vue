<template>
  <div class="app-container">
    <!--查询条件-->
    <Query></Query>
    <!--操作区域-->
    <Operate></Operate>
    <!--事件数据-->
    <DataTable></DataTable>
    <!-- 添加、修改事件配置对话框 -->
    <FormDialog ref="formDialog"></FormDialog>
  </div>
</template>

<script setup name="Events" lang="ts">
  import Query from './components/query.vue'
  import DataTable from './components/dataTable.vue'
  import useEventsStore from './store'

  const eventsStore = useEventsStore()

  onBeforeMount(async () => {
    // 获取设备组数据
    await eventsStore.getDeviceGroupData()
    // 获取事件数据
    await eventsStore.getEventsData()
  })

</script>
