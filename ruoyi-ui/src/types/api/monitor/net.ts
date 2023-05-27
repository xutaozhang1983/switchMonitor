/**
 * 设备项查询参数类型声明
 */
export interface DeviceItemQueryParam {
  deviceId?: number // 设备ID
}

/**
 * 设备监控数据查询参数类型声明
 */
export interface DeviceItemHisQueryParam {
  deviceId: number, // 设备ID
  itemId?: number, // 指标ID
  counters: string[], // 监控类型
  startClock?: string, // 起始时间
  endClock?: string, // 结束时间
  step?: number // 步数
}