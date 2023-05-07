<template>
  <el-table v-loading="alarmStore.loading" :data="alarmStore.alarmData" @selection-change="handleSelectionChange">
    <el-table-column label="消息" prop="message" :show-overflow-tooltip="true" />
    <el-table-column label="来源" prop="source"/>
    <el-table-column label="重要度">
      <template #default="scope">
        <div class="status" v-if="scope.row.importance === 1">
          <el-icon class="mr5" color="#79c155" size="20px"><CircleCheckFilled/></el-icon>
          <span>正常</span>
        </div>
        <div class="status" v-if="scope.row.importance === 2">
          <el-icon class="mr5" color="#E6A23C" size="20px"><WarningFilled/></el-icon>
          <span>注意</span>
        </div>
        <div class="status" v-if="scope.row.importance === 3">
          <el-icon class="mr5" color="#F56C6C" size="20px"><CircleCloseFilled/></el-icon>
          <span>严重</span>
        </div>
      </template>
    </el-table-column>
    <el-table-column label="告警时间" prop="alarmTime" />
  </el-table>
  <!--数据分页-->
  <pagination
    v-show="alarmStore.total > 0"
    :total="alarmStore.total"
    v-model:page="alarmStore.queryParams.pageNum"
    v-model:limit="alarmStore.queryParams.pageSize"
    @pagination="alarmStore.getalarmData"
  />
</template>

<script setup lang="ts">
  import { ElMessageBox, ElMessage } from 'element-plus'
  import usealarmStore from '../store'
  import { dayjs } from 'element-plus'

  const alarmStore = usealarmStore()
  const router = useRouter()
  const emits = defineEmits(['setMenuChecked', 'setDeptChecked'])

  // 选择条数
  function handleSelectionChange(selection: any) {
    alarmStore.ids = selection.map((item: any) => item.roleId)
    alarmStore.single = selection.length != 1
    alarmStore.multiple = !selection.length
  }

  // 告警状态修改
  function handleStatusChange(row: any) {
    let text = row.status === "0" ? "启用" : "停用";
    ElMessageBox.confirm('确认要"' + text + '""' + row.roleName + '"告警吗?', '系统提示', { type: 'warning' }).then(async () => {
      let sendData = {
        roleId: row.roleId,
        status: row.status
      }
      await alarmStore.changeAlarmStatus(sendData)
    }).then(() => {
      ElMessage.success(text + "成功")
    }).catch(function () {
      row.status = row.status === "0" ? "1" : "0"
    })
  }

  // 删除按钮操作
  function handleDelete(row: any) {
    alarmStore.ids = [ row.roleId ]
    ElMessageBox.confirm('是否确认删除告警编号为"' + alarmStore.ids + '"的数据项？', '系统提示', { type: 'warning' }).then(async () => {
      await alarmStore.delAlarm()
      alarmStore.getalarmData()
      ElMessage.success("删除成功")
    }).catch(() => {})
  }

  // 修改按钮操作
  async function handleUpdate(row: any) {
    alarmStore.resetFormData()
    await alarmStore.getAlarmInfo(row.roleId)
    alarmStore.formTitle = "修改告警"
    alarmStore.showFormDialog = true
    emits('setMenuChecked')
  }  
</script>

<style lang="scss" scoped>
  .status {
    display: flex;
    align-items: center;
  }
</style>