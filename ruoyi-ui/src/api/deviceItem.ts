import request from '@/utils/request'
import { DeviceItemQueryParam } from '@/types/api/deviceItem'

// 查询设备项列表
export function listDeviceItem(query: DeviceItemQueryParam) {
  return request({
    url: '/monitor/item/list',
    method: 'get',
    params: query
  })
}
