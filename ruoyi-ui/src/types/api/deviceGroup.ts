import { PageQueryParam } from "./base"

/**
 * 设备组查询参数类型声明
 */
export interface DeviceGroupQueryParam extends PageQueryParam {
  groupName?: string | undefined, // 设备组名字
  status?: string | undefined // 状态
}

/**
 * 设备组表单类型声明
 */
export interface DeviceGroupFormData {
  id?: number | undefined, // 设备组ID
  groupName: string | undefined, // 设备组名称
  alarmUserGrpId: string | undefined, // 报警接收用户组id
  alarmMaxTimes: number | undefined, // 报警发送次数
  status: string | undefined, // 状态
  deviceCount?: number | undefined, // 设备数
  remark: string | undefined, // 备注
}

/**
 * 设备组状态修改类型声明
 */
export interface DeviceGroupChangeStatus {
  id: number,
  status: string
}