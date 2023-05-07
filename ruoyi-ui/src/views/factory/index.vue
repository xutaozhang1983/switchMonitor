<template>
  <div class="app-container">
    <!--查询条件-->
    <Query></Query>
    <!--操作区域-->
    <Operate></Operate>
    <!--厂商数据-->
    <DataTable></DataTable>
    <!-- 添加、修改厂商配置对话框 -->
    <FormDialog ref="formDialog"></FormDialog>
  </div>
</template>

<script setup name="Factory" lang="ts">
  import Query from './components/query.vue'
  import Operate from './components/operate.vue'
  import DataTable from './components/dataTable.vue'
  import FormDialog from './components/formDialog.vue'
  import useFactoryStore from './store'

  const factoryStore = useFactoryStore()

  onBeforeMount(async () => {
    // 获取厂商数据
    await factoryStore.getFactoryData()
  })

</script>
