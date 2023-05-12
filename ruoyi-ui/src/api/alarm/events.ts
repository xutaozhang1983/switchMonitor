import request from '@/utils/request'
import { EventsQueryParam, EventsClose } from '@/types/api/alarm/events'

// 查询事件列表
export function listEvents(query: EventsQueryParam) {
  return request({
    url: '/monitor/events/list',
    method: 'get',
    params: query
  })
}

// 事件关闭
export function closeEvents(data: EventsClose) {
  return request({
    url: '/monitor/events/close',
    method: 'post',
    data
  })
}
