<template>
  <el-dialog :title="jobStore.formTitle" v-model="jobStore.showFormDialog" width="800px" append-to-body>
    <el-form ref="jobRef" :model="jobStore.formData" :rules="rules" label-width="120px">
      <el-row>
        <el-col :span="12">
          <el-form-item label="任务名称" prop="jobName">
            <el-input v-model="jobStore.formData.jobName" placeholder="请输入任务名称" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="任务分组" prop="jobGroup">
            <el-select v-model="jobStore.formData.jobGroup" placeholder="请选择">
              <el-option v-for="dict in dictJobGroup" :key="dict.value" :label="dict.label" :value="dict.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item prop="invokeTarget">
            <template #label>
              <span>调用方法
                <el-tooltip placement="top">
                  <template #content>
                    <div>Bean调用示例：ryTask.ryParams('ry')<br />Class类调用示例：com.ruoyi.quartz.task.RyTask.ryParams('ry')<br />参数说明：支持字符串，布尔类型，长整型，浮点型，整型
                    </div>
                  </template>
                  <el-icon><question-filled /></el-icon>
                </el-tooltip>
              </span>
            </template>
            <el-input v-model="jobStore.formData.invokeTarget" placeholder="请输入调用目标字符串" />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="cron表达式" prop="cronExpression">
            <el-input v-model="jobStore.formData.cronExpression" placeholder="请输入cron执行表达式">
              <template #append>
                <el-button type="primary" @click="handleShowCron">
                  生成表达式
                  <i class="el-icon-time el-icon--right"></i>
                </el-button>
              </template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="执行策略" prop="misfirePolicy">
            <el-radio-group v-model="jobStore.formData.misfirePolicy">
              <el-radio-button label="1">立即执行</el-radio-button>
              <el-radio-button label="2">执行一次</el-radio-button>
              <el-radio-button label="3">放弃执行</el-radio-button>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="是否并发" prop="concurrent">
            <el-radio-group v-model="jobStore.formData.concurrent">
              <el-radio-button label="0">允许</el-radio-button>
              <el-radio-button label="1">禁止</el-radio-button>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="状态">
            <el-radio-group v-model="jobStore.formData.status">
              <el-radio v-for="dict in dictNormalPause" :key="dict.value" :label="dict.value">{{ dict.label }}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
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
  import useJobStore from '../store'
  import { dictJobGroup, dictNormalPause } from '@/utils/dict'

  const { proxy } = getCurrentInstance() as any
  const jobStore = useJobStore()
  
  const rules = ref({
    jobName: [
      { required: true, message: '任务名称不能为空', trigger: 'blur' }
    ],
    invokeTarget: [
      { required: true, message: '调用目标字符串不能为空', trigger: 'blur' }
    ],
    cronExpression: [
      { required: true, message: 'cron执行表达式不能为空', trigger: 'change' }
    ]
  })

  /** cron表达式按钮操作 */
  function handleShowCron() {
    jobStore.showCronDialog = true
  }
  
  // 提交按钮
  function submitForm() {
    proxy.$refs["jobRef"].validate(async (valid: boolean) => {
      if (valid) {
        if (jobStore.formData.jobId != undefined) {
          await jobStore.updateJob()
          ElMessage.success("修改成功")
          jobStore.showFormDialog = false
          jobStore.getJobData()
        } else {
          await jobStore.addJob()
          ElMessage.success("新增成功")
          jobStore.showFormDialog = false
          jobStore.getJobData()
        }
      }
    })
  }

  // 取消按钮
  function cancel() {
    jobStore.resetFormData()
    jobStore.showFormDialog = false
  }
</script>