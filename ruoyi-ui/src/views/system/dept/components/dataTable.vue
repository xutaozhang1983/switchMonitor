<template>
  <el-table 
    v-if="deptStore.refreshTable"
    v-loading="deptStore.loading"
    :data="deptStore.deptData"
    row-key="deptId"
    :default-expand-all="deptStore.isExpandAll"
    :tree-props="{ children: 'children', hasChildren: 'hasChildren' }">
    <el-table-column prop="deptName" label="部门名称" width="260"></el-table-column>
    <el-table-column prop="orderNum" label="排序" width="200"></el-table-column>
    <el-table-column prop="status" label="状态" width="100">
      <template #default="scope">
        <dict-tag :options="dictNormalDisable" :value="scope.row.status" />
      </template>
    </el-table-column>
    <el-table-column label="创建时间" align="center" prop="createTime" width="200">
      <template #default="scope">
        <span>{{ dayjs(scope.row.createTime).format('YYYY-MM-DD HH:mm:ss') }}</span>
      </template>
    </el-table-column>
    <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
      <template #default="scope">
        <el-button type="text" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:dept:edit']">修改</el-button>
        <el-button type="text" icon="Plus" @click="handleAdd(scope.row)" v-hasPermi="['system:dept:add']">新增</el-button>
        <el-button v-if="scope.row.parentId != 0" type="text" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['system:dept:remove']">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script setup lang="ts">
  import { ElMessageBox, ElMessage } from 'element-plus'
  import useDeptStore from '../store'
  import { dayjs } from 'element-plus'
  import { dictNormalDisable } from '@/utils/dict'

  const deptStore = useDeptStore()

  // 新增按钮操作
  function handleAdd(row: any) {
    deptStore.resetFormData()
    deptStore.formData.parentId = row.deptId
    deptStore.showFormDialog = true
    deptStore.formTitle = "添加部门"
  }

  // 删除按钮操作
  function handleDelete(row: any) {
    ElMessageBox.confirm('是否确认删除名称为"' + row.menuName + '"的数据项？', '系统提示', { type: 'warning' }).then(async () => {
      await deptStore.delDept(row.deptId)
      deptStore.getDeptData()
      ElMessage.success("删除成功")
    }).catch(() => {})
  }

  // 修改按钮操作
  async function handleUpdate(row: any) {
    deptStore.resetFormData()
    await deptStore.listDeptExcludeChild(row.deptId)
    await deptStore.getDeptInfo(row.deptId)
    deptStore.formTitle = "修改部门"
    deptStore.showFormDialog = true
  }
  
</script>