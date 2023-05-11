import request from '@/utils/request'
import { EventsQueryParam } from '@/types/api/alarm/events'

// 查询事件列表
export function listEvents(query: EventsQueryParam) {
  return request({
    url: '/monitor/events/list',
    method: 'get',
    params: query
  })
}
