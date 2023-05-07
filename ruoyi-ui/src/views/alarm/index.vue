<template>
  <div class="app-container">
    <!--查询条件-->
    <Query></Query>
    <!--操作区域-->
    <Operate></Operate>
    <!--告警数据-->
    <DataTable></DataTable>
    <!-- 添加、修改告警配置对话框 -->
    <FormDialog ref="formDialog"></FormDialog>
  </div>
</template>

<script setup name="Alarm" lang="ts">
  import Query from './components/query.vue'
  import Operate from './components/operate.vue'
  import DataTable from './components/dataTable.vue'
  import FormDialog from './components/formDialog.vue'
  import usealarmStore from './store'

  const alarmStore = usealarmStore()

  onBeforeMount(async () => {
    // 获取告警数据
    await alarmStore.getalarmData()
  })

</script>
