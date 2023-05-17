<template>
  <el-table v-loading="jobStore.loading" :data="jobStore.jobData" @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="55" align="center" />
    <el-table-column label="任务编号" width="100" align="center" prop="jobId" />
    <el-table-column label="任务名称" align="center" prop="jobName" :show-overflow-tooltip="true" />
    <el-table-column label="任务组名" align="center" prop="jobGroup">
      <template #default="scope">
        <dict-tag :options="dictJobGroup" :value="scope.row.jobGroup" />
      </template>
    </el-table-column>
    <el-table-column label="调用目标字符串" align="center" prop="invokeTarget" :show-overflow-tooltip="true" />
    <el-table-column label="cron执行表达式" align="center" prop="cronExpression" :show-overflow-tooltip="true" />
    <el-table-column label="状态" align="center">
      <template #default="scope">
        <el-switch v-model="scope.row.status" active-value="0" inactive-value="1" @change="handleStatusChange(scope.row)"></el-switch>
      </template>
    </el-table-column>
    <el-table-column label="操作" align="center" width="200" class-name="small-padding fixed-width">
      <template #default="scope">
        <el-tooltip content="修改" placement="top">
          <el-button type="text" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['monitor:job:edit']"></el-button>
        </el-tooltip>
        <el-tooltip content="删除" placement="top">
          <el-button type="text" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['monitor:job:remove']"></el-button>
        </el-tooltip>
        <el-tooltip content="执行一次" placement="top">
          <el-button type="text" icon="CaretRight" @click="handleRun(scope.row)" v-hasPermi="['monitor:job:changeStatus']"></el-button>
        </el-tooltip>
        <el-tooltip content="任务详细" placement="top">
          <el-button type="text" icon="View" @click="handleView(scope.row)" v-hasPermi="['monitor:job:query']"></el-button>
        </el-tooltip>
      </template>
    </el-table-column>
  </el-table>
  <!--数据分页-->
  <pagination
    v-show="jobStore.total > 0"
    :total="jobStore.total"
    v-model:page="jobStore.queryParams.pageNum"
    v-model:limit="jobStore.queryParams.pageSize"
    @pagination="jobStore.getJobData" />
</template>

<script setup lang="ts">
  import { ElMessageBox, ElMessage } from 'element-plus'
  import useJobStore from '../store'
  import { dictJobGroup } from '@/utils/dict'

  const router = useRouter()
  const jobStore = useJobStore()

  // 选择条数
  function handleSelectionChange(selection: any) {
    jobStore.ids = selection.map((item: any) => item.jobId)
    jobStore.single = selection.length != 1
    jobStore.multiple = !selection.length
  }

  // 任务状态修改
  function handleStatusChange(row: any) {
    let text = row.status === "0" ? "启用" : "停用";
    ElMessageBox.confirm('确认要"' + text + '""' + row.jobName + '"任务吗?', '系统提示', { type: 'warning' }).then(async () => {
      let sendData = {
        jobId: row.jobId,
        status: row.status
      }
      await jobStore.changeJobStatus(sendData)
    }).then(() => {
      ElMessage.success(text + "成功")
    }).catch(function () {
      row.status = row.status === "0" ? "1" : "0"
    })
  }

  // 删除按钮操作
  function handleDelete(row: any) {
    jobStore.ids = [ row.jobId ]
    ElMessageBox.confirm('是否确认删除任务编号为"' + jobStore.ids + '"的数据项？', '系统提示', { type: 'warning' }).then(async () => {
      await jobStore.delJob()
      jobStore.getJobData()
      ElMessage.success("删除成功")
    }).catch(() => {})
  }

  // 修改按钮操作
  async function handleUpdate(row: any) {
    jobStore.resetFormData()
    await jobStore.getJobInfo(row.jobId)
    jobStore.formTitle = "修改任务"
    jobStore.showFormDialog = true
  }

  /* 立即执行一次 */
  function handleRun(row: any) {
    ElMessageBox.confirm('确认要立即执行一次"' + row.jobName + '"任务吗?', '系统提示', { type: 'warning' }).then(async () => {
      let sendData = {
        jobId: row.jobId,
        jobGroup: row.jobGroup
      }
      await jobStore.runJob(sendData)
    }).then(() => {
      ElMessage.success("执行成功")
    }).catch(() => {})
  }

  /** 任务详细信息 */
  async function handleView(row: any) {
    await jobStore.getJobInfo(row.jobId)
    jobStore.showDetailDialog = true
  }
  
</script>