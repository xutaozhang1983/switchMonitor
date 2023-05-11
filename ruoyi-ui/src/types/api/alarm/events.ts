import { PageQueryParam } from "../base"

/**
 * 事件查询参数类型声明
 */
export interface EventsQueryParam extends PageQueryParam {
  startTime?: string | undefined, // 开始时间
  endTime?: string | undefined, // 结束时间
  deviceId?: number | undefined, // 设备ID
  alarmLevel?: string | undefined, // 告警级别
  status?: string | undefined, // 关闭状态
}