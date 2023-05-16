<template>
  <el-table v-loading="postStore.loading" :data="postStore.postData" @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="55" align="center" />
    <el-table-column label="岗位编号" align="center" prop="postId" />
    <el-table-column label="岗位编码" align="center" prop="postCode" />
    <el-table-column label="岗位名称" align="center" prop="postName" />
    <el-table-column label="岗位排序" align="center" prop="postSort" />
    <el-table-column label="状态" align="center" prop="status">
      <template #default="scope">
        <dict-tag :options="dictNormalDisable" :value="scope.row.status" />
      </template>
    </el-table-column>
    <el-table-column label="创建时间" align="center" prop="createTime" width="180">
      <template #default="scope">
        <span>{{ dayjs(scope.row.createTime).format('YYYY-MM-DD HH:mm:ss') }}</span>
      </template>
    </el-table-column>
    <el-table-column
      label="操作"
      align="center"
      class-name="small-padding fixed-width">
      <template #default="scope">
        <el-button type="text" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:post:edit']">修改</el-button>
        <el-button type="text" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['system:post:remove']">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
   <!--数据分页-->
  <pagination
      v-show="postStore.total > 0"
      :total="postStore.total"
      v-model:page="postStore.queryParams.pageNum"
      v-model:limit="postStore.queryParams.pageSize"
      @pagination="postStore.getPostData" />
</template>

<script setup lang="ts">
  import { ElMessageBox, ElMessage } from 'element-plus'
  import usePostStore from '../store'
  import { dayjs } from 'element-plus'
  import { dictNormalDisable } from '@/utils/dict'

  const postStore = usePostStore()

  // 选择条数
  function handleSelectionChange(selection: any) {
    postStore.ids = selection.map((item: any) => item.postId)
    postStore.single = selection.length != 1
    postStore.multiple = !selection.length
  }

  // 删除按钮操作
  function handleDelete(row: any) {
    postStore.ids = [ row.postId ]
    ElMessageBox.confirm('是否确认删除岗位编号为"' + postStore.ids + '"的数据项？', '系统提示', { type: 'warning' }).then(async () => {
      await postStore.delPost()
      postStore.getPostData()
      ElMessage.success("删除成功")
    }).catch(() => {})
  }

  // 修改按钮操作
  async function handleUpdate(row: any) {
    postStore.resetFormData()
    await postStore.getPostInfo(row.postId)
    postStore.formTitle = "修改岗位"
    postStore.showFormDialog = true
  }
  
</script>