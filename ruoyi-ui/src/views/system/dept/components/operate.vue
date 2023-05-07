<template>
  <el-row :gutter="10" class="mb8">
    <el-col :span="1.5">
      <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['system:dept:add']">新增</el-button>
    </el-col>
    <el-col :span="1.5">
      <el-button type="info" plain icon="Sort" @click="toggleExpandAll">展开/折叠</el-button>
    </el-col>
    <!--右侧工具条-->
    <right-toolbar v-model:showSearch="deptStore.showSearch" @queryTable="deptStore.getDeptData"></right-toolbar>
  </el-row>
</template>

<script setup lang="ts">
  import useDeptStore from '../store'

  const deptStore = useDeptStore()

  // 新增按钮操作
  function handleAdd() {
    deptStore.resetFormData()
    deptStore.formData.parentId = 0
    deptStore.showFormDialog = true
    deptStore.formTitle = "添加部门"
  }

  /** 展开/折叠操作 */
  function toggleExpandAll() {
    deptStore.refreshTable = false
    deptStore.isExpandAll = !deptStore.isExpandAll
    nextTick(() => {
      deptStore.refreshTable = true
    })
  }

</script>