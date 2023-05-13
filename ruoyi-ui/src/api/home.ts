import request from '@/utils/request'
import { FlowCount } from '@/types/api/home'

// 查询设备统计数据
export function getDeviceCount() {
  return request({
    url: '/monitor/device/status/stat',
    method: 'get'    
  })
}

// 查询设备组统计数据
export function getDeviceGroupCount() {
  return request({
    url: '/monitor/device/status/groupStat',
    method: 'get'    
  })
}

// 查询流量统计数据
export function getFlowCount(params: FlowCount) {
  return request({
    url: '/monitor/item/his/selectTop5',
    method: 'get',
    params
  })
}