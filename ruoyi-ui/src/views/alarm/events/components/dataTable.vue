<template>
  <el-table v-loading="eventsStore.loading" :data="eventsStore.eventsData">
    <el-table-column label="编号" prop="id" width="100"/>
    <el-table-column label="告警级别" width="120">
      <template #default="scope">
        <div class="level" v-if="scope.row.alarmLevel === 'INFO'">
          <el-icon class="mr5" color="#909399" size="20px"><WarningFilled /></el-icon>
          <span>INFO</span>
        </div>
        <div class="level" v-else-if="scope.row.alarmLevel === 'WARING'">
          <el-icon class="mr5" color="#E6A23C"  size="20px"><WarningFilled /></el-icon>
          <span>WARING</span>
        </div>
        <div class="level" v-else-if="scope.row.alarmLevel === 'ERROR'">
          <el-icon class="mr5" color="#F56C6C"  size="20px"><WarningFilled /></el-icon>
          <span>ERROR</span>
        </div>
        <div class="level" v-else>
          <el-icon class="mr5" color="#C0C4CC"  size="20px"><WarningFilled /></el-icon>
          <span>OTHER</span>
        </div>
      </template>
    </el-table-column>
    <el-table-column label="设备名称" prop="deviceName" width="100" :show-overflow-tooltip="true"/>
    <el-table-column label="监控指标" prop="itemName" width="100"/>
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
        <el-tooltip content="关闭告警" placement="left" v-if="scope.row.roleId !== 1 && scope.row.status === '0'">
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
    <el-form :model="formData" ref="eventsRef" :rules="rules">
      <el-form-item label="关闭原因" prop="closedNote">
        <el-input v-model="formData.closedNote" type="textarea" placeholder="请输入" style="width:100%"/>
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
  import { ElMessage } from 'element-plus'
  import useEventsStore from '../store'
  import { closeEvents } from '@/api/alarm/events'

  const { proxy } = getCurrentInstance() as any
  const eventsStore = useEventsStore()

  const showCloseDialog = ref(false)
  const formData = ref({
    id: undefined,
    closedNote: undefined,
    status: '1'
  })
  const rules = {
    closedNote: [
      { required: true, message: "关闭原因不能为空", trigger: "blur" },
    ]
  }

  // 点击关闭按钮
  function handleClose (row: any) {
    formData.value.closedNote = undefined
    formData.value.id = row.id
    showCloseDialog.value = true
  }

  // 提交
  function submitForm () {
    proxy.$refs["eventsRef"].validate(async (valid: boolean) => {
      if (valid) {
        closeEvents(formData.value).then((response: any) => {
          ElMessage.success("关闭成功")
          showCloseDialog.value = false
          eventsStore.getEventsData()
        })
      }
    })
  }

  // 取消
  function cancel () {
    showCloseDialog.value = false
  }

</script>

<style lang="scss" scoped>

  .level {
    display: flex;
    align-items: center;
  }
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