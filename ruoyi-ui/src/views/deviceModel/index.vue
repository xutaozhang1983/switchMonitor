<template>
  <div class="app-container">
    <!--查询条件-->
    <Query></Query>
    <!--操作区域-->
    <Operate></Operate>
    <!--型号数据-->
    <DataTable></DataTable>
    <!-- 添加、修改型号配置对话框 -->
    <FormDialog ref="formDialog"></FormDialog>
  </div>
</template>

<script setup name="DeviceModel" lang="ts">
  import Query from './components/query.vue'
  import Operate from './components/operate.vue'
  import DataTable from './components/dataTable.vue'
  import FormDialog from './components/formDialog.vue'
  import useDeviceModelStore from './store'

  const deviceModelStore = useDeviceModelStore()

  onBeforeMount(async () => {
    // 获取型号数据
    await deviceModelStore.getDeviceModelData()
  })

</script>
