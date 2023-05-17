<template>
  <el-row :gutter="10" class="mb8">
    <el-col :span="1.5">
      <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['tool:job:add']">新增</el-button>
    </el-col>
    <el-col :span="1.5">
      <el-button type="success" plain icon="Edit" :disabled="jobStore.single" @click="handleUpdate" v-hasPermi="['tool:job:edit']">修改</el-button>
    </el-col>
    <el-col :span="1.5">
      <el-button type="danger" plain icon="Delete" :disabled="jobStore.multiple" @click="handleDelete" v-hasPermi="['tool:job:remove']">删除</el-button>
    </el-col>
    <el-col :span="1.5">
      <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['tool:job:export']">导出</el-button>
    </el-col>
    <!--右侧工具条-->
    <right-toolbar v-model:showSearch="jobStore.showSearch" @queryTable="jobStore.getJobData"></right-toolbar>
  </el-row>
</template>

<script setup lang="ts">
  import { ElMessageBox, ElMessage } from 'element-plus'
  import useJobStore from '../store'

  const { proxy } = getCurrentInstance() as any
  const jobStore = useJobStore()

  // 新增按钮操作
  function handleAdd() {
    jobStore.resetFormData()
    jobStore.showFormDialog = true
    jobStore.formTitle = "添加任务"
  }

  // 修改按钮操作
  async function handleUpdate() {
    await jobStore.getJobInfo(jobStore.ids[0])
    jobStore.formTitle = "修改任务";
    jobStore.showFormDialog = true;
  }

  // 删除按钮操作
  function handleDelete() {
    ElMessageBox.confirm('是否确认删除定时任务编号为"' + jobStore.ids + '"的数据项？', '系统提示', { type: 'warning' }).then(async () => {
      await jobStore.delJob()
      jobStore.getJobData()
      ElMessage.success("删除成功")
    }).catch(() => {})
  }

  // 导出按钮操作
  function handleExport() {
    proxy.download(
      "monitor/job/export",
      {
        ...jobStore.queryParams,
      },
      `job_${new Date().getTime()}.xlsx`
    )
  }

</script>