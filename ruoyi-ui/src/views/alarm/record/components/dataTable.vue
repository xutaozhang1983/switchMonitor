<template>
  <el-table v-loading="recordStore.loading" :data="recordStore.recordData">
    <el-table-column label="编号" prop="id" width="100"/>
    <el-table-column label="发送时间" prop="createTime"></el-table-column>
    <el-table-column label="发送人" prop="userName"/>
    <el-table-column label="发送方式" prop="sendType"/>
    <el-table-column label="接收地址" prop="receiveAddr"/>
    <el-table-column label="发送状态">
      <template #default="scope">
        <div class="status" v-if="scope.row.status === '0'">
          <el-icon class="mr5" color="#67C23A" size="20px"><WarningFilled /></el-icon>
          <span>成功</span>
        </div>
        <div class="status" v-else>
          <el-icon class="mr5" color="#F56C6C" size="20px"><WarningFilled /></el-icon>
          <span>失败</span>
        </div>
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
    align-items: center;
  }
</style>