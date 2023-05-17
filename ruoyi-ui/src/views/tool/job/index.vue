<template>
  <div class="app-container">
    <!--查询条件-->
    <Query></Query>
    <!--操作区域-->
    <Operate></Operate>
    <!--角色数据-->
    <DataTable></DataTable>
    <!-- 添加、修改用户配置对话框 -->
    <FormDialog></FormDialog>
    <!-- Cron表达式生成器对话框 -->
    <CronDialog></CronDialog>
    <!-- 任务详情对话框 -->
    <DetailDialog></DetailDialog>
  </div>
</template>

<script setup name="Job" lang="ts">
  import Query from './components/query.vue'
  import Operate from './components/operate.vue'
  import DataTable from './components/dataTable.vue'
  import FormDialog from './components/formDialog.vue'
  import DetailDialog from './components/detailDialog.vue'
  import CronDialog from './components/cronDialog.vue'

  import useJobStore from './store'

  const jobStore = useJobStore()
  
  onBeforeMount(async () => {
   // 获取任务数据
   await jobStore.getJobData()
  })
</script>
