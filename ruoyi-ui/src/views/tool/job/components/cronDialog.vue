<template>
  <el-dialog
    title="Cron表达式生成器"
    v-model="jobStore.showCronDialog"
    append-to-body
    destroy-on-close>
    <Crontab ref="crontabRef" @hide="jobStore.showCronDialog = false" @fill="crontabFill" :expression="jobStore.formData.cronExpression"></Crontab>
  </el-dialog>
</template>

<script setup lang="ts">
  import Crontab from '@/components/Crontab/index.vue'

  import useJobStore from '../store'

  const jobStore = useJobStore()

  /** 确定后回传值 */
  function crontabFill(value: string) {
    jobStore.formData.cronExpression = value
  }
</script>