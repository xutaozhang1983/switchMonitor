<template>
  <el-form :model="menuStore.queryParams" ref="queryRef" :inline="true" v-show="menuStore.showSearch">
    <el-form-item label="菜单名称" prop="menuName">
      <el-input v-model="menuStore.queryParams.menuName" placeholder="请输入菜单名称" clearable @keyup.enter="handleQuery"/>
    </el-form-item>
    <el-form-item label="状态" prop="status">
      <el-select v-model="menuStore.queryParams.status" placeholder="菜单状态" clearable>
          <el-option v-for="dict in dictNormalDisable" :key="dict.value" :label="dict.label" :value="dict.value"/>
      </el-select>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
      <el-button icon="Refresh" @click="resetQuery">重置</el-button>
    </el-form-item>
  </el-form>
</template>

<script setup lang="ts">
  import { ElForm } from 'element-plus'
  import useMenuStore from '../store'
  import { dictNormalDisable } from '@/utils/dict'
  
  const { proxy } = getCurrentInstance() as any
  const menuStore = useMenuStore()

  const queryRef = ref<InstanceType<typeof ElForm> | null>(null)

  // 搜索按钮操作
  function handleQuery() {
    menuStore.getMenuData()
  }

  // 重置按钮操作
  function resetQuery() {
    proxy.$refs.queryRef.resetFields()
    handleQuery()
  }
</script>