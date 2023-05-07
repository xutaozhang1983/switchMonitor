<template>
  <div class="app-container">
    <!--查询条件-->
    <Query></Query>
    <!--操作区域-->
    <Operate></Operate>
    <!--菜单数据-->
    <DataTable></DataTable>
    <!-- 添加、修改用户配置对话框 -->
    <FormDialog></FormDialog>
  </div>
</template>

<script setup name="Menu" lang="ts">
  import Query from './components/query.vue'
  import Operate from './components/operate.vue'
  import DataTable from './components/dataTable.vue'
  import FormDialog from './components/formDialog.vue'

  import useMenuStore from './store'

  onBeforeMount(async () => {
    // 获取菜单数据
    await useMenuStore().getMenuData()
  })
</script>
