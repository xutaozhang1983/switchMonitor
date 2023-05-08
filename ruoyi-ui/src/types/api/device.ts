import { PageQueryParam } from "./base"

/**
 * 设备查询参数类型声明
 */
export interface DeviceQueryParam extends PageQueryParam {
  groupName: string | undefined, // 设备组
  deviceName: string | undefined, // 设备名称
  device_ip: string | undefined, // 设备IP
  status: string | undefined // 状态,
  enable: number | undefined // 是否启用监控
}

/**
 * 设备表单类型声明
 */
export interface DeviceFormData {
  deviceId?: number | undefined, // 设备ID
  groupId: number | undefined, // 设备组ID
  deviceName: string | undefined, // 设备名称
  deviceType: string | undefined, // 设备类型
  deviceModel: string | undefined, // 设备型号
  deviceIp: string | undefined, // 设备IP
  snmpCommunity: string | undefined, // snmp团体名
  snmpVersion: number | undefined, // snmp版本号
  snmpPort: number | undefined, // 端口数
  snmpUsername: string | undefined, // snmp用户名
  snmpPassword: string | undefined, // snmp密码
  manufacturer: string | undefined, // 制造商
  status: string | undefined, // 状态
  enable: number | undefined, // 是否启用监控
  remark: string | undefined, // 备注
}

/**
 * 设备监控状态修改类型声明
 */
export interface DeviceChangeEnable {
  deviceId: number,
  enable: number
}