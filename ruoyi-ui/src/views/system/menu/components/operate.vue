<template>
  <el-row :gutter="10" class="mb8">
    <el-col :span="1.5">
      <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['system:menu:add']">新增</el-button>
    </el-col>
    <el-col :span="1.5">
      <el-button type="info" plain icon="Sort" @click="toggleExpandAll">展开/折叠</el-button>
    </el-col>
    <!--右侧工具条-->
    <right-toolbar v-model:showSearch="menuStore.showSearch" @queryTable="menuStore.getMenuData"></right-toolbar>
  </el-row>
</template>

<script setup lang="ts">
  import useMenuStore from '../store'

  const menuStore = useMenuStore()

  // 新增按钮操作
  function handleAdd() {
    menuStore.resetFormData()
    menuStore.formData.parentId = 0
    menuStore.showFormDialog = true
    menuStore.formTitle = "添加菜单"
  }

  /** 展开/折叠操作 */
  function toggleExpandAll() {
    menuStore.refreshTable = false
    menuStore.isExpandAll = !menuStore.isExpandAll
    nextTick(() => {
      menuStore.refreshTable = true
    })
  }

</script>