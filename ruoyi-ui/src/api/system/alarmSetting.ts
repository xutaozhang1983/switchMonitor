import request from '@/utils/request'
import { AlarmFormData } from '@/types/api/system/alarmSetting'

// 告警介质修改
export function updateAlarmMedia(data: AlarmFormData) {
  return request({
    url: '/monitor/media',
    method: 'put',
    data: data
  })
}

// 查询介质详细
export function getAlarmMedia(mediaType: string) {
  return request({
    url: '/monitor/media/' + mediaType,
    method: 'get'
  })
}

// 发送测试邮件
export function sendTestMail(params: { mediaType: string, receive: string }) {
  return request({
    url: '/monitor/media/emailTest',
    method: 'get',
    params
  })
}
