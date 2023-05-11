import request from '@/utils/request'
import { DeviceItemHisQueryParam } from '@/types/api/deviceItemHis'

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
