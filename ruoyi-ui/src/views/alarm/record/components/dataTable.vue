<template>
  <el-table v-loading="recordStore.loading" :data="recordStore.recordData">
    <el-table-column label="编号" prop="id" width="100"/>
    <el-table-column label="发送时间" prop="createTime"></el-table-column>
    <el-table-column label="发送人" prop="userName"/>
    <el-table-column label="发送方式" prop="sendType"/>
    <el-table-column label="接收地址" prop="receiveAddr"/>
    <el-table-column label="发送状态">
      <template #default="scope">
        <div class="status status-open" v-if="scope.row.status === '0'">成功</div>
        <div class="status status-close" v-else>失败</div>
      </template>
    </el-table-column>  
  </el-table>
  <!--数据分页-->
  <pagination
    v-show="recordStore.total > 0"
    :total="recordStore.total"
    v-model:page="recordStore.queryParams.pageNum"
    v-model:limit="recordStore.queryParams.pageSize"
    @pagination="recordStore.getRecordData"
  />
</template>

<script setup lang="ts">
  import useRecordStore from '../store'

  const recordStore = useRecordStore()

</script>

<style lang="scss" scoped>
  .status {
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 12px;
    width: 50px;
    padding: 1px 0;
    border-radius: 5px;
    &-open {
      color: #67C23A;
      border: 1px solid #67C23A;
    }
    &-close {
      color: #F56C6C;
      border: 1px solid #F56C6C;
    }
  }
</style>