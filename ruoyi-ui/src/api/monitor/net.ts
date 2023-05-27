import request from '@/utils/request'
import { DeviceItemQueryParam } from '@/types/api/monitor/net'
import { DeviceItemHisQueryParam } from '@/types/api/monitor/net'

// 查询设备项列表
export function listDeviceItem(query: DeviceItemQueryParam) {
  return request({
    url: '/monitor/item/list',
    method: 'get',
    params: query
  })
}

// 通过设备id查询监控数据
export function getItemHisByDeviceId(data: DeviceItemHisQueryParam) {
  return request({
    url: '/monitor/item/his/device/graph',
    method: 'post',
    data: data
  })
}

// 通过指标id查询监控数据
export function getItemHisByItemId(data: DeviceItemHisQueryParam) {
  return request({
    url: '/monitor/item/his/selectGraph',
    method: 'post',
    data: data
  })
}
