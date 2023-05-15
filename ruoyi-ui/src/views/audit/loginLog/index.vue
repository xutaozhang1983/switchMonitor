<template>
  <div class="app-container">
    <!--查询条件-->
    <Query></Query>
    <!--操作区域-->
    <Operate></Operate>
    <!--登录日志数据-->
    <DataTable></DataTable>
  </div>
</template>

<script setup name="Logininfor" lang="ts">
   import Query from './components/query.vue'
   import Operate from './components/operate.vue'
   import DataTable from './components/dataTable.vue'
   import useLoginLogStore from './store'

  const loginLogStore = useLoginLogStore()

  onBeforeMount(async () => {
    // 获取登录日志数据
    await loginLogStore.getLoginLogData()
  })
  
</script>
