<template>
  <div class="app-container">
   <!--查询条件-->
   <Query></Query>
   <!--操作区域-->
   <Operate></Operate>
   <!--登录日志数据-->
   <DataTable></DataTable>
   <!-- 操作日志详细 -->
   <DetailDialog></DetailDialog>
  </div>
</template>

<script setup name="Operlog" lang="ts">
   import Query from './components/query.vue'
   import Operate from './components/operate.vue'
   import DataTable from './components/dataTable.vue'
   import DetailDialog from './components/detailDialog.vue'

   import useOperLogStore from './store'

  const operLogStore = useOperLogStore()

  onBeforeMount(async () => {
    // 获取登录日志数据
    await operLogStore.getOperLogData()
  })
</script>
