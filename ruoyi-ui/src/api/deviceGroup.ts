import request from '@/utils/request'
import { DeviceGroupQueryParam, DeviceGroupFormData, DeviceGroupChangeStatus } from '@/types/api/deviceGroup'

// 查询设备组列表
export function listDeviceGroup(query: DeviceGroupQueryParam) {
  return request({
    url: '/monitor/group/list',
    method: 'get',
    params: query
  })
}

// 查询设备组详细
export function getDeviceGroup(deviceGroupId: number) {
  return request({
    url: '/monitor/group/' + deviceGroupId,
    method: 'get'
  })
}

// 新增设备组
export function addDeviceGroup(data: DeviceGroupFormData) {
  return request({
    url: '/monitor/group',
    method: 'post',
    data: data
  })
}

// 修改设备组
export function updateDeviceGroup(data: DeviceGroupFormData) {
  return request({
    url: '/monitor/group',
    method: 'put',
    data: data
  })
}

// 删除设备组
export function delDeviceGroup(deviceGroupId: string) {
  return request({
    url: '/monitor/group/' + deviceGroupId,
    method: 'delete'
  })
}

// 设备组状态修改
export function changeDeviceGroupStatus(data: DeviceGroupChangeStatus) {
  return request({
    url: '/monitor/group/changeStatus',
    method: 'put',
    data: data
  })
}
