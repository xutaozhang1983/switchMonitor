import request from '@/utils/request'
import { RecordQueryParam } from '@/types/api/alarm/record'

// 查询记录列表
export function listRecord(query: RecordQueryParam) {
  return request({
    url: '/monitor/events/alarm/list',
    method: 'get',
    params: query
  })
}
