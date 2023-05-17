import { PageQueryParam } from "../base"

/**
 * 任务查询参数类型声明
 */
export interface JobQueryParam extends PageQueryParam {
  jobName: string | undefined, // 任务名称
  jobGroup: string | undefined, // 任务分组
  status: string | undefined // 状态
}

/**
 * 任务表单类型声明
 */
export interface JobFormData {
  jobId?: number | undefined, // 任务ID
  jobName: string | undefined, // 任务名称
  jobGroup: string | undefined, // 任务分组
  invokeTarget: string | undefined, // 调用目标方法
  cronExpression: string | undefined, // cron表达式
  misfirePolicy: number | undefined, // 执行策略
  concurrent: number | undefined, // 是否并发
  status: string | undefined, // 状态
  createTime?: string | undefined, // 创建时间
  nextValidTime?: string | undefined // 下次执行时间
}

/**
 * 任务状态修改类型声明
 */
export interface JobChangeStatus {
  jobId: number,
  status: string
}

/**
 * 任务立即执行类型声明
 */
export interface JobRun {
  jobId: number,
  jobGroup: string
}