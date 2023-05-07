<template>
  <el-row :gutter="10" class="mb8">
    <el-col :span="1.5">
      <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['system:role:add']" >新增</el-button>
    </el-col>
    <el-col :span="1.5">
      <el-button type="success" plain icon="Edit" :disabled="roleStore.single" @click="handleUpdate" v-hasPermi="['system:role:edit']">修改</el-button>
    </el-col>
    <el-col :span="1.5">
      <el-button type="danger" plain icon="Delete" :disabled="roleStore.multiple" @click="handleDelete" v-hasPermi="['system:role:remove']">删除</el-button>
    </el-col>
    <el-col :span="1.5">
      <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['system:role:export']">导出</el-button>
    </el-col>
    <!--右侧工具条-->
    <right-toolbar v-model:showSearch="roleStore.showSearch" @queryTable="roleStore.getRoleData"></right-toolbar>
  </el-row>
</template>

<script setup lang="ts">
  import { ElMessageBox, ElMessage } from 'element-plus'
  import useRoleStore from '../store'

  const { proxy } = getCurrentInstance() as any
  const roleStore = useRoleStore()

  // 新增按钮操作
  async function handleAdd() {
    roleStore.resetFormData()
    await roleStore.getMenuTree()
    roleStore.showFormDialog = true
    roleStore.formTitle = "添加角色"
  }

  // 修改按钮操作
  async function handleUpdate() {
    roleStore.resetFormData()
    await roleStore.getRoleInfo(roleStore.ids[0])
    await roleStore.getMenuTreeByRoleId(roleStore.ids[0])
    roleStore.formTitle = "修改角色"
    roleStore.showFormDialog = true
  }

  // 删除按钮操作
  function handleDelete() {
    ElMessageBox.confirm('是否确认删除角色编号为"' + roleStore.ids + '"的数据项？', '系统提示', { type: 'warning' }).then(async () => {
      await roleStore.delRole()
      roleStore.getRoleData()
      ElMessage.success("删除成功")
    }).catch(() => {})
  }

  // 导出按钮操作
  function handleExport() {
    proxy.download("system/role/export", { ...roleStore.queryParams }, `role_${new Date().getTime()}.xlsx` )
  }

</script>