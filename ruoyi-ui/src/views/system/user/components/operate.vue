<template>
  <el-row :gutter="10" class="mb8">
    <el-col :span="1.5">
      <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['system:user:add']">新增</el-button>
    </el-col>
    <el-col :span="1.5">
      <el-button type="success" plain icon="Edit" :disabled="userStore.single" @click="handleUpdate" v-hasPermi="['system:user:edit']">修改</el-button>
    </el-col>
    <el-col :span="1.5">
      <el-button type="danger" plain icon="Delete" :disabled="userStore.multiple" @click="handleDelete" v-hasPermi="['system:user:remove']">删除</el-button>
    </el-col>
    <el-col :span="1.5">
      <el-button type="info" plain icon="Upload" @click="handleImport" v-hasPermi="['system:user:import']">导入</el-button>
    </el-col>
    <el-col :span="1.5">
      <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['system:user:export']">导出</el-button>
    </el-col>
    <!--右侧工具条-->
    <right-toolbar v-model:showSearch="userStore.showSearch" @queryTable="userStore.getUserData" :columns="userStore.columns"></right-toolbar>
  </el-row>
</template>

<script setup lang="ts">
  import { ElMessageBox, ElMessage } from 'element-plus'
  import useUserStore from '../store'

  const { proxy } = getCurrentInstance() as any
  const userStore = useUserStore()

  // 新增按钮操作
  async function handleAdd() {
    userStore.resetFormData()
    await userStore.getUserInfo()
    userStore.showFormDialog = true
    userStore.formTitle = "添加用户"
  }

  // 修改按钮操作
  async function handleUpdate() {
    await userStore.getUserInfo(userStore.ids[0])
    userStore.formTitle = "修改用户";
    userStore.showFormDialog = true;
  }

  // 删除按钮操作
  function handleDelete() {
    ElMessageBox.confirm('是否确认删除用户编号为"' + userStore.ids + '"的数据项？', '系统提示', { type: 'warning' }).then(async () => {
      await userStore.delUser()
      userStore.getUserData()
      ElMessage.success("删除成功")
    }).catch(() => {})
  }

  // 导出按钮操作
  function handleExport() {
    proxy.download(
      "system/user/export",
      {
        ...userStore.queryParams,
      },
      `user_${new Date().getTime()}.xlsx`
    );
  }

  // 导入按钮操作
  function handleImport() {
    userStore.uploadParams.title = "用户导入";
    userStore.uploadParams.open = true;
  }

</script>