<template>
  <el-dialog title="任务详细" v-model="jobStore.showDetailDialog" width="700px" append-to-body>
    <el-form :model="jobStore.formData" label-width="120px">
      <el-row>
        <el-col :span="12">
          <el-form-item label="任务编号：">{{ jobStore.formData.jobId }}</el-form-item>
          <el-form-item label="任务名称：">{{ jobStore.formData.jobName }}</el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="任务分组：">{{ jobGroupFormat(jobStore.formData) }}</el-form-item>
          <el-form-item label="创建时间：">{{ jobStore.formData.createTime }}</el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="cron表达式：">{{ jobStore.formData.cronExpression }}</el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="下次执行时间：">{{ dayjs(jobStore.formData.nextValidTime).format("YYYY-MM-DD HH:mm:ss") }}</el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="调用目标方法：">{{ jobStore.formData.invokeTarget }}</el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="任务状态：">
            <div v-if="jobStore.formData.status == '0'">正常</div>
            <div v-else-if="jobStore.formData.status == '1'">失败</div>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="是否并发：">
            <div v-if="jobStore.formData.concurrent == 0">允许</div>
            <div v-else-if="jobStore.formData.concurrent == 1">禁止</div>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="执行策略：">
            <div v-if="jobStore.formData.misfirePolicy == 0">默认策略</div>
            <div v-else-if="jobStore.formData.misfirePolicy == 1">立即执行</div>
            <div v-else-if="jobStore.formData.misfirePolicy == 2">执行一次</div>
            <div v-else-if="jobStore.formData.misfirePolicy == 3">放弃执行</div>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="jobStore.showDetailDialog = false">关 闭</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
  import useJobStore from '../store'
  import dayjs from 'dayjs'
  import { dictJobGroup } from '@/utils/dict'

  const jobStore = useJobStore()

  /** 任务组名字典翻译 */
  function jobGroupFormat(row: any) {
    return (dictJobGroup).filter((item: any) => item.value === row.jobGroup)[0].label
  }
</script>