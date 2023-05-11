<template>
  <el-table v-loading="eventsStore.loading" :data="eventsStore.eventsData" @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="55" align="center" />
    <el-table-column label="编号" prop="id" width="100"/>
    <el-table-column label="设备名称" prop="deviceName" width="100" :show-overflow-tooltip="true"/>
    <el-table-column label="监控指标" prop="itemName" width="100"/>
    <el-table-column label="告警级别" prop="alarmLevel" width="100"/>
    <el-table-column label="告警内容" prop="alarmContent" :show-overflow-tooltip="true"/>
    <el-table-column label="发送次数" prop="alarmSendTimes" width="100"/>
    <el-table-column label="告警状态" width="100">
      <template #default="scope">
        <div class="status status-open" v-if="scope.row.status === '0'">已开启</div>
        <div class="status status-close" v-else>已关闭</div>
      </template>
    </el-table-column>  
    <el-table-column label="关闭人" prop="closedUser" width="100"/>
    <el-table-column label="关闭原因" prop="closedNote" width="150" :show-overflow-tooltip="true"/>
    <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="100">
      <template #default="scope">
        <el-tooltip content="关闭" placement="left" v-if="scope.row.roleId !== 1">
          <el-button type="text" icon="CircleClose" @click="handleClose(scope.row)" v-hasPermi="['system:role:edit']"></el-button>
        </el-tooltip>
      </template>
    </el-table-column>
  </el-table>
  <!--数据分页-->
  <pagination
    v-show="eventsStore.total > 0"
    :total="eventsStore.total"
    v-model:page="eventsStore.queryParams.pageNum"
    v-model:limit="eventsStore.queryParams.pageSize"
    @pagination="eventsStore.getEventsData"
  />
  <!--关闭告警对话框-->
  <el-dialog title="关闭告警" v-model="showCloseDialog" width="500px" append-to-body :show-close="false">
    <el-form :model="formData" ref="queryRef" :rules="rules">
      <el-form-item label="关闭原因" prop="closeNote">
        <el-input v-model="formData.closeNote" type="textarea" placeholder="请输入" style="width:100%"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
  import useEventsStore from '../store'

  const eventsStore = useEventsStore()

  const showCloseDialog = ref(false)
  const formData = ref({
    closeNote: undefined
  })
  const rules = {
    closeNote: [
      { required: true, message: "关闭原因不能为空", trigger: "blur" },
    ]
  }

  // 点击关闭按钮
  function handleClose (row: any) {
    formData.value.closeNote = undefined
    showCloseDialog.value = true
  }

  // 提交
  function submitForm () {

  }

  // 取消
  function cancel () {
    showCloseDialog.value = false
  }

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
      color: #409EFF;
      border: 1px solid #409EFF;
    }
    &-close {
      color: #909399;
      border: 1px solid #909399;
    }
  }
</style>