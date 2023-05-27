import request from '@/utils/request'
import { EncodeQueryParam, CameraQueryParam } from '@/types/api/monitor/camera'

// 查询编码设备列表
export function listEncode(query: EncodeQueryParam) {
  return request({
    url: '/monitor/encode/list',
    method: 'get',
    params: query
  })
}

// 查询摄像头列表
export function listCamera(query: CameraQueryParam) {
  return request({
    url: '/monitor/camera/list',
    method: 'get',
    params: query
  })
}