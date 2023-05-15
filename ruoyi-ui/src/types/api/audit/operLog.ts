import { PageQueryParam } from "../base"

/**
 * 操作日志查询参数类型声明
 */
export interface OperLogQueryParam extends PageQueryParam {
  title?: string | undefined, // 系统模块
  operName: string | undefined, // 操作人员
  businessType: string | undefined, // 操作类型
  status?: string | undefined // 状态
}