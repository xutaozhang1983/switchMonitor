<template>
  <div class="app-container">
    <!--监控数据-->
    <DataTable></DataTable>
  </div>
</template>

<script setup name="Monitor" lang="ts">
  import DataTable from './components/dataTable.vue'
  import useMonitorStore from './store'

  const monitorStore = useMonitorStore()

  onBeforeMount(async () => {
    // 获取监控数据
    await monitorStore.getMonitorData()
  })

</script>
