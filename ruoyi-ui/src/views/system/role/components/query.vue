<template>
  <el-form :model="roleStore.queryParams" ref="queryRef" v-show="roleStore.showSearch" :inline="true">
      <el-form-item label="角色名称" prop="roleName">
        <el-input v-model="roleStore.queryParams.roleName" placeholder="请输入角色名称" clearable style="width: 240px" @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="权限字符" prop="roleKey">
        <el-input v-model="roleStore.queryParams.roleKey" placeholder="请输入权限字符" clearable style="width: 240px" @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="roleStore.queryParams.status" placeholder="角色状态" clearable style="width: 240px">
          <el-option v-for="dict in sys_normal_disable" :key="dict.value" :label="dict.label" :value="dict.value"/>
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间" style="width: 308px">
        <el-date-picker v-model="dateRange" value-format="YYYY-MM-DD" type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
</template>

<script setup lang="ts">
  import { ElForm } from 'element-plus'
  import useRoleStore from '../store'
  import { dayjs } from 'element-plus'

  const { proxy } = getCurrentInstance() as any;
  const roleStore = useRoleStore()

  const queryRef = ref<InstanceType<typeof ElForm> | null>(null)

  const { sys_normal_disable }: { sys_normal_disable: DictType[] } = proxy.useDict(
    "sys_normal_disable"
  );

  const dateRange = ref([])

  /** 搜索按钮操作 */
  function handleQuery() {
    roleStore.queryParams.pageNum = 1;
    if (dateRange.value.length) {
      roleStore.queryParams.beginTime = dayjs().format(dateRange.value[0])
      roleStore.queryParams.endTime = dayjs().format(dateRange.value[1])
    }
    roleStore.getRoleData()
  }

  // 重置按钮操作
  function resetQuery() {
    dateRange.value = []
    roleStore.queryParams.beginTime = undefined
    roleStore.queryParams.endTime = undefined
    proxy.$refs.queryRef.resetFields()
    handleQuery()
  }
</script>