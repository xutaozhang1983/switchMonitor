import request from '@/utils/request'
import { DeviceQueryParam, DeviceFormData } from '@/types/api/device'

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
export function addDevice(data: deviceFormData) {
  return request({
    url: '/monitor/device',
    method: 'post',
    data: data
  })
}

// 修改设备
export function updateDevice(data: deviceFormData) {
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
