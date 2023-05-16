<template>
  <el-table ref="operlogRef" v-loading="operLogStore.loading" :data="operLogStore.operLogData" @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="55" align="center" />
    <el-table-column label="日志编号" align="center" prop="operId" />
    <el-table-column label="系统模块" align="center" prop="title" />
    <el-table-column label="操作类型" align="center" prop="businessType">
      <template #default="scope">
        <dict-tag :options="dictOperType" :value="scope.row.businessType" />
      </template>
    </el-table-column>
    <el-table-column label="请求方式" align="center" prop="requestMethod" />
    <el-table-column label="操作人员" align="center" prop="operName" :show-overflow-tooltip="true" sortable="custom" :sort-orders="['descending', 'ascending']" width="100" />
    <el-table-column label="主机" align="center" prop="operIp" width="130" :show-overflow-tooltip="true" />
    <el-table-column label="操作状态" align="center" prop="status">
      <template #default="scope">
        <dict-tag :options="dictSuccessFail" :value="scope.row.status" />
      </template>
    </el-table-column>
    <el-table-column label="操作日期" align="center" prop="operTime" sortable="custom" :sort-orders="['descending', 'ascending']" width="180">
      <template #default="scope">
        <span>{{ dayjs(scope.row.operTime).format('YYYY-MM-DD HH:mm:ss') }}</span>
      </template>
    </el-table-column>
    <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
      <template #default="scope">
        <el-button type="text" icon="View" @click="handleView(scope.row)" v-hasPermi="['audit:operlog:query']">详细</el-button>
      </template>
    </el-table-column>
  </el-table>
  <!--数据分页-->
  <pagination
    v-show="operLogStore.total > 0"
    :total="operLogStore.total"
    v-model:page="operLogStore.queryParams.pageNum"
    v-model:limit="operLogStore.queryParams.pageSize"
    @pagination="operLogStore.getOperLogData" />
</template>

<script setup lang="ts">
  import useOperLogStore from '../store'
  import dayjs from 'dayjs'
  import { dictOperType, dictSuccessFail } from '@/utils/dict'

  const operLogStore = useOperLogStore()

  // 选择条数
  function handleSelectionChange(selection: any) {
    operLogStore.ids = selection.map((item: any) => item.operId)
    operLogStore.single = selection.length != 1
    operLogStore.multiple = !selection.length
  }

  /** 详细按钮操作 */
  function handleView(row: any ) {
    operLogStore.showDetailDialog = true
    operLogStore.detailForm = row
  }
</script>