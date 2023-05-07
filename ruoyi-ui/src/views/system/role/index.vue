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
    <!-- 分配角色数据权限对话框 -->
    <AuthDataDialog ref="authDataDialog"></AuthDataDialog>
  </div>
</template>

<script setup name="Role" lang="ts">
  import Query from './components/query.vue'
  import Operate from './components/operate.vue'
  import DataTable from './components/dataTable.vue'
  import FormDialog from './components/formDialog.vue'
  import AuthDataDialog from './components/authDataDialog.vue'
  import useRoleStore from './store'

  const roleStore = useRoleStore()

  onBeforeMount(async () => {
    // 获取角色数据
    await roleStore.getRoleData()
  })

</script>
