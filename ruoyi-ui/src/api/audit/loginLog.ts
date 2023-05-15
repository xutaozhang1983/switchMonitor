import request from '@/utils/request'
import { LoginLogQueryParam } from '@/types/api/audit/loginLog'

// 查询登录日志列表
export function listLoginLog(query: LoginLogQueryParam) {
  return request({
    url: '/monitor/logininfor/list',
    method: 'get',
    params: query
  })
}

// 删除登录日志
export function delLoginLog(ids: string) {
  return request({
    url: '/monitor/logininfor/' + ids,
    method: 'delete'
  })
}

// 解锁用户登录状态
export function unlockAccount(selectName: string) {
  return request({
    url: '/monitor/logininfor/unlock/' + selectName,
    method: 'get'
  })
}

// 清空登录日志
export function cleanLoginLog() {
  return request({
    url: '/monitor/logininfor/clean',
    method: 'delete'
  })
}
