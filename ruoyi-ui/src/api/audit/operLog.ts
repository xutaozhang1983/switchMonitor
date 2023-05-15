import request from '@/utils/request'
import { OperLogQueryParam } from '@/types/api/audit/operLog'

// 查询操作日志列表
export function listOperLog(query: OperLogQueryParam) {
  return request({
    url: '/monitor/operlog/list',
    method: 'get',
    params: query
  })
}

// 删除操作日志
export function delOperLog(ids: string) {
  return request({
    url: '/monitor/operlog/' + ids,
    method: 'delete'
  })
}

// 清空操作日志
export function cleanOperLog() {
  return request({
    url: '/monitor/operlog/clean',
    method: 'delete'
  })
}
