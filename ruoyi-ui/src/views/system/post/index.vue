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
   </div>
 </template>
 
 <script setup name="Post" lang="ts">
   import Query from './components/query.vue'
   import Operate from './components/operate.vue'
   import DataTable from './components/dataTable.vue'
   import FormDialog from './components/formDialog.vue'
   import usePostStore from './store'
 
   const postStore = usePostStore()
 
   onBeforeMount(async () => {
     // 获取岗位数据
     await postStore.getPostData()
   })
 
 </script>
 