import { PageQueryParam } from "./base"

/**
 * 设备查询参数类型声明
 */
export interface DeviceQueryParam extends PageQueryParam {
  deviceCode: string | undefined, // 设备编码
  deviceName: string | undefined, // 设备名称
  status: string | undefined // 状态
}

/**
 * 设备表单类型声明
 */
export interface DeviceFormData {
  deviceId?: number | undefined, // 设备ID
  deviceCode: string | undefined, // 设备编码
  deviceName: string | undefined, // 设备名称
  deviceSort: number | undefined, // 排序
  status: string | undefined, // 状态
  remark: string | undefined, // 备注
}