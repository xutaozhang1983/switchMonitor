<template>
  <div class="app-container">
    <!--查询条件-->
    <Query></Query>
    <!--操作区域-->
    <Operate></Operate>
    <!--记录数据-->
    <DataTable></DataTable>
    <!-- 添加、修改记录配置对话框 -->
    <FormDialog ref="formDialog"></FormDialog>
  </div>
</template>

<script setup name="AlarmRecord" lang="ts">
  import Query from './components/query.vue'
  import DataTable from './components/dataTable.vue'
  import useRecordStore from './store'

  const recordStore = useRecordStore()

  onBeforeMount(async () => {
    // 获取记录数据
    await recordStore.getRecordData()
  })

</script>
