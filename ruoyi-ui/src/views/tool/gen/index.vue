<template>
  <div class="app-container">
    <!--查询条件-->
    <Query ref="queryRef"></Query>
    <!--操作区域-->
    <Operate></Operate>
    <!--任务数据-->
    <DataTable></DataTable>
    <!-- 预览对话框 -->
    <ViewDialog></ViewDialog>
    <!--数据表导入对话框-->
    <ImportDialog @ok="handleQuery" />
  </div>
</template>

<script setup name="Gen" lang="ts">
  import Query from './components/query.vue'
  import Operate from './components/operate.vue'
  import DataTable from './components/dataTable.vue'
  import ViewDialog from './components/viewDialog.vue'
  import ImportDialog from './components/importDialog.vue'

  import useGenStore from './store'

  const genStore = useGenStore()
  const queryRef = ref<InstanceType<typeof Query> | null>(null)

  // 重置搜索
  function handleQuery() {
    queryRef.value?.handleQuery()
  }
  
  onBeforeMount(async () => {
   // 获取代码数据
   await genStore.getGenData()
  }) 
  
</script>
