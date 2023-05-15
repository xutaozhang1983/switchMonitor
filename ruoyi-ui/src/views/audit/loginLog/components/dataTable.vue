<template>
  <el-table ref="logininforRef" v-loading="loginLogStore.loading" :data="loginLogStore.loginLogData" @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="55" align="center" />
    <el-table-column label="访问编号" align="center" prop="infoId" />
    <el-table-column label="用户名称" align="center" prop="userName" :show-overflow-tooltip="true" sortable="custom" :sort-orders="['descending', 'ascending']" />
    <el-table-column label="地址" align="center" prop="ipaddr" :show-overflow-tooltip="true" />
    <el-table-column label="登录地点" align="center" prop="loginLocation" :show-overflow-tooltip="true" />
    <el-table-column label="操作系统" align="center" prop="os" :show-overflow-tooltip="true" />
    <el-table-column label="浏览器" align="center" prop="browser" :show-overflow-tooltip="true" />
    <el-table-column label="登录状态" align="center" prop="status">
      <template #default="scope">
        <dict-tag :options="sys_common_status" :value="scope.row.status" />
      </template>
    </el-table-column>
    <el-table-column label="描述" align="center" prop="msg" />
    <el-table-column label="访问时间" align="center" prop="loginTime" sortable="custom" :sort-orders="['descending', 'ascending']" width="180">
      <template #default="scope">
        <span>{{ dayjs(scope.row.loginTime).format('YYYY-MM-DD HH:mm:ss') }}</span>
      </template>
    </el-table-column>
  </el-table>
  <!--数据分页-->
  <pagination
    v-show="loginLogStore.total > 0"
    :total="loginLogStore.total"
    v-model:page="loginLogStore.queryParams.pageNum"
    v-model:limit="loginLogStore.queryParams.pageSize"
    @pagination="loginLogStore.getLoginLogData" />
</template>

<script setup lang="ts">
  import useLoginLogStore from '../store'
  import dayjs from 'dayjs'

  const { proxy } = getCurrentInstance() as any
  const loginLogStore = useLoginLogStore()

  const { sys_common_status }: { sys_common_status: DictType[] } = proxy.useDict(
    "sys_common_status"
  )

  // 选择条数
  function handleSelectionChange(selection: any) {
    loginLogStore.ids = selection.map((item: any) => item.infoId)
    loginLogStore.selectName = selection.map((item: any) => item.userName)
    loginLogStore.single = selection.length != 1
    loginLogStore.multiple = !selection.length
  }
</script>