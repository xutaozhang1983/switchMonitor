<template>
  <el-form :model="userStore.queryParams" ref="queryRef" :inline="true" v-show="userStore.showSearch" label-width="68px">
    <el-form-item label="用户名称" prop="userName">
      <el-input v-model="userStore.queryParams.userName" placeholder="请输入用户名称" clearable style="width: 240px" @keyup.enter="handleQuery"/>
    </el-form-item>
    <el-form-item label="手机号码" prop="mobile">
      <el-input v-model="userStore.queryParams.phonenumber" placeholder="请输入手机号码" clearable style="width: 240px" @keyup.enter="handleQuery"/>
    </el-form-item>
    <el-form-item label="状态" prop="status">
      <el-select v-model="userStore.queryParams.status" placeholder="用户状态" clearable style="width: 240px">
        <el-option v-for="dict in sys_normal_disable" :key="dict.value" :label="dict.label" :value="dict.value"/>
      </el-select>
    </el-form-item>
    <el-form-item label="创建时间" style="width: 308px">
      <el-date-picker
        v-model="dateRange"
        value-format="YYYY-MM-DD"
        type="daterange"
        range-separator="-"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
      ></el-date-picker>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
      <el-button icon="Refresh" @click="resetQuery">重置</el-button>
    </el-form-item>
  </el-form>
</template>

<script setup lang="ts">
  import useUserStore from '../store'
  import { dayjs } from 'element-plus'

  const { proxy } = getCurrentInstance() as any;
  const userStore = useUserStore()

  const { sys_normal_disable }: { sys_normal_disable: DictType[] } = proxy.useDict(
    "sys_normal_disable"
  )

  const dateRange = ref([])

  // 搜索按钮操作
  function handleQuery() {
    userStore.queryParams.pageNum = 1
    if (dateRange.value.length) {
      userStore.queryParams.beginTime = dayjs().format(dateRange.value[0])
      userStore.queryParams.endTime = dayjs().format(dateRange.value[1])
    }
    userStore.getUserData()
  }

  // 重置按钮操作
  function resetQuery() {
    dateRange.value = []
    userStore.queryParams.beginTime = undefined
    userStore.queryParams.endTime = undefined
    proxy.resetForm("queryRef")
    userStore.queryParams.deptId = undefined
    userStore.resetDeptTreeKey = true
    handleQuery();
  }
</script>