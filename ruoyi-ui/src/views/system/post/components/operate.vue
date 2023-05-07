<template>
  <el-row :gutter="10" class="mb8">
    <el-col :span="1.5">
      <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['system:post:add']" >新增</el-button>
    </el-col>
    <el-col :span="1.5">
      <el-button type="success" plain icon="Edit" :disabled="postStore.single" @click="handleUpdate" v-hasPermi="['system:post:edit']">修改</el-button>
    </el-col>
    <el-col :span="1.5">
      <el-button type="danger" plain icon="Delete" :disabled="postStore.multiple" @click="handleDelete" v-hasPermi="['system:post:remove']">删除</el-button>
    </el-col>
    <el-col :span="1.5">
      <el-button
        type="warning"
        plain
        icon="Download"
        @click="handleExport"
        v-hasPermi="['system:post:export']"
        >导出</el-button
      >
    </el-col>
    <!--右侧工具条-->
    <right-toolbar
      v-model:showSearch="postStore.showSearch"
      @queryTable="postStore.getPostData"></right-toolbar>
  </el-row>
</template>

<script setup lang="ts">
  import { ElMessageBox, ElMessage } from 'element-plus'
  import usePostStore from '../store'

  const { proxy } = getCurrentInstance() as any
  const postStore = usePostStore()

  // 新增按钮操作
  function handleAdd() {
    postStore.resetFormData()
    postStore.showFormDialog = true
    postStore.formTitle = "添加岗位"
  }

  // 修改按钮操作
  async function handleUpdate() {
    await postStore.getPostInfo(postStore.ids[0])
    postStore.formTitle = "修改岗位";
    postStore.showFormDialog = true;
  }

  // 删除按钮操作
  function handleDelete() {
    ElMessageBox.confirm('是否确认删除岗位编号为"' + postStore.ids + '"的数据项？', '系统提示', { type: 'warning' }).then(async () => {
      await postStore.delPost()
      postStore.getPostData()
      ElMessage.success("删除成功")
    }).catch(() => {})
  }

  // 导出按钮操作
  function handleExport() {
    proxy.download(
      "system/user/export",
      {
        ...postStore.queryParams,
      },
      `post_${new Date().getTime()}.xlsx`
    )
  }

</script>