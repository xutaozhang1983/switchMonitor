import request from '@/utils/request'
import { DeviceQueryParam, DeviceFormData, DeviceChangeEnable } from '@/types/api/device'

// 查询设备列表
export function listDevice(query: DeviceQueryParam) {
  return request({
    url: '/monitor/device/list',
    method: 'get',
    params: query
  })
}

// 查询设备详细
export function getDevice(deviceId: number) {
  return request({
    url: '/monitor/device/' + deviceId,
    method: 'get'
  })
}

// 新增设备
export function addDevice(data: DeviceFormData) {
  return request({
    url: '/monitor/device',
    method: 'post',
    data: data
  })
}

// 修改设备
export function updateDevice(data: DeviceFormData) {
  return request({
    url: '/monitor/device',
    method: 'put',
    data: data
  })
}

// 删除设备
export function delDevice(deviceId: string) {
  return request({
    url: '/monitor/device/' + deviceId,
    method: 'delete'
  })
}

// 设备监控状态修改
export function changeDeviceEnable(data: DeviceChangeEnable) {
  return request({
    url: '/monitor/device/changeEnable',
    method: 'put',
    data: data
  })
}
