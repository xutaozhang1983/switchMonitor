import { PageQueryParam } from "../base"

/**
 * 记录查询参数类型声明
 */
export interface RecordQueryParam extends PageQueryParam {
  startTime?: string | undefined, // 开始时间
  endTime?: string | undefined, // 结束时间
  userName?: string | undefined, // 接收人
  status?: string | undefined, // 发送状态
}