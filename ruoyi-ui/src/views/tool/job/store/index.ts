import { JobQueryParam, JobFormData, JobChangeStatus, JobRun } from '@/types/api/tool/job'
import { listJob, getJob, addJob, updateJob, delJob, runJob, changeJobStatus } from "@/api/tool/job"

const useJobStore = defineStore('useJobStore',
  {
    state: () => ({
      loading: false, // 任务数据loading状态
      queryParams: { pageNum: 1, pageSize: 10 } as JobQueryParam, // 查询参数
      formData: {} as JobFormData, // 任务新增、修改提交参数
      jobData: [] as any[], // 任务数据
      showSearch: true, // 是否显示搜索区域
      showFormDialog: false,  // 显示任务新增、修改对话框
      showDetailDialog: false, // 显示任务详情对话框
      showCronDialog: false, // 显示cron表达式生成器对话框
      total: 0, // 用户数据总条目数
      single: true, // 选择1条数据
      multiple: true, // 选择多条数据
      ids: [] as number[], // 选择数据的id集合
      formTitle: '', // 任务新增、修改对话框标题
      refreshTable: true, // 刷新表格数据
      isExpandAll: true // 表格数据是否全部展开
    }),
    actions: {
      /** 重置操作表单 */
      resetFormData() {
        this.formData = {
          jobId: undefined,
          jobName: undefined,
          jobGroup: undefined,
          invokeTarget: undefined,
          cronExpression: undefined,
          misfirePolicy: 1,
          concurrent: 1,
          status: '0'
        }
      },
      // 查询任务数据
      async getJobData() {
        this.loading = true
        try {
          let { rows, total }: any = await listJob(this.queryParams)
          this.jobData = rows
          this.total = total
        } catch (error: any) {
        } finally {
          this.loading = false
        }
      },
      // 获取任务信息
      async getJobInfo(JobId: number) {
        try {
          let { data }: any = await getJob(JobId)
          this.formData = data
        } catch {}
      },
      // 新增任务
      async addJob() {
        try {
          await addJob(this.formData)
        } catch {}
      },
      // 修改任务
      async updateJob() {
        try {
          await updateJob(this.formData)
        } catch {}
      },
      // 删除任务
      async delJob() {
        try {
          await delJob(this.ids.join(','))
        } catch {}
      },
      // 执行任务
      async runJob(data: JobRun) {
        try {
          await runJob(data)
        } catch {}
      },
      // 更改任务状态
      async changeJobStatus(data: JobChangeStatus) {
        await changeJobStatus(data)
      }
    }
  }
)

export default useJobStore 