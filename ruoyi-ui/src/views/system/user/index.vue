<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--部门数据-->
      <el-col :span="4" :xs="24">
        <DeptTree></DeptTree>
      </el-col>
      <!--查询条件-->
      <el-col :span="20" :xs="24">
        <Query></Query>
        <!--操作区域-->
        <Operate></Operate>
        <!--用户数据-->
        <DataTable></DataTable>
      </el-col>
    </el-row>
    <!-- 添加、修改用户配置对话框 -->
    <FormDialog></FormDialog>
    <!-- 用户导入对话框 -->
    <ImportDialog></ImportDialog>
  </div>
</template>

<script setup name="User" lang="ts">
  import DeptTree from './components/deptTree.vue'
  import Query from './components/query.vue'
  import Operate from './components/operate.vue'
  import DataTable from './components/dataTable.vue'
  import FormDialog from './components/formDialog.vue'
  import ImportDialog from './components/importDialog.vue'

  import useUserStore from './store'

  const userStore = useUserStore()

  onBeforeMount(async () => {
    // 获取部门树状数据
    await userStore.getDeptTreeData()
    // 获取用户数据
    await userStore.getUserData()
  })
</script>
