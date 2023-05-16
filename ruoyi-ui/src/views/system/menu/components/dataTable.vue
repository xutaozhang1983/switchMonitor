<template>
  <el-table
    v-if="menuStore.refreshTable"
    v-loading="menuStore.loading"
    :data="menuStore.menuData"
    row-key="menuId"
    :default-expand-all="menuStore.isExpandAll"
    :tree-props="{ children: 'children', hasChildren: 'hasChildren' }">
    <el-table-column prop="menuName" label="菜单名称" :show-overflow-tooltip="true" width="160"></el-table-column>
    <el-table-column prop="icon" label="图标" align="center" width="100">
      <template #default="scope">
          <svg-icon :icon-class="scope.row.icon" />
      </template>
    </el-table-column>
    <el-table-column prop="orderNum" label="排序" width="60"></el-table-column>
    <el-table-column prop="perms" label="权限标识" :show-overflow-tooltip="true"></el-table-column>
    <el-table-column prop="component" label="组件路径" :show-overflow-tooltip="true"></el-table-column>
    <el-table-column prop="status" label="状态" width="80">
      <template #default="scope">
        <dict-tag :options="dictNormalDisable" :value="scope.row.status" />
      </template>
    </el-table-column>
    <el-table-column label="创建时间" align="center" prop="createTime">
      <template #default="scope">
        <span>{{ dayjs(scope.row.createTime).format('YYYY-MM-DD HH:mm:ss') }}</span>
      </template>
    </el-table-column>
    <el-table-column label="操作" align="center" width="200" class-name="small-padding fixed-width">
      <template #default="scope">
        <el-button type="text" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:menu:edit']">修改</el-button>
        <el-button type="text" icon="Plus" @click="handleAdd(scope.row)" v-hasPermi="['system:menu:add']">新增</el-button>
        <el-button type="text" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['system:menu:remove']">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script setup lang="ts">
  import { ElMessageBox, ElMessage } from 'element-plus'
  import useMenuStore from '../store'
  import { dayjs } from 'element-plus'
  import { dictNormalDisable } from '@/utils/dict'

  const menuStore = useMenuStore()

  // 新增按钮操作
  function handleAdd(row: any) {
    menuStore.resetFormData()
    menuStore.formData.parentId = row.menuId
    menuStore.showFormDialog = true
    menuStore.formTitle = "添加菜单"
  }

  // 删除按钮操作
  function handleDelete(row: any) {
    ElMessageBox.confirm('是否确认删除名称为"' + row.menuName + '"的数据项？', '系统提示', { type: 'warning' }).then(async () => {
      await menuStore.delMenu(row.menuId)
      menuStore.getMenuData()
      ElMessage.success("删除成功")
    }).catch(() => {})
  }

  // 修改按钮操作
  async function handleUpdate(row: any) {
    menuStore.resetFormData()
    await menuStore.getMenuInfo(row.menuId)
    menuStore.formTitle = "修改菜单"
    menuStore.showFormDialog = true
  }
  
</script>