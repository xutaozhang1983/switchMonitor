/**
 * 设备项监控明细查询参数类型声明
 */
export interface DeviceItemHisQueryParam {
  deviceId?: number, // 设备ID
  counters: string[], // 监控类型
  step?: number // 步数 
}