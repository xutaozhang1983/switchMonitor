import request from '@/utils/request'
import { DeviceItemHisQueryParam } from '@/types/api/deviceItemHis'

// 查询设备项监控明细
export function listDeviceItemHis(data: DeviceItemHisQueryParam) {
  return request({
    url: '/monitor/item/his/device/graph',
    method: 'post',
    data: data
  })
}
