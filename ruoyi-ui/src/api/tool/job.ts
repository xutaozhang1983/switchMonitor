import request from '@/utils/request'
import { JobQueryParam, JobFormData, JobChangeStatus, JobRun } from '@/types/api/tool/job'

// 查询定时任务调度列表
export function listJob(params: JobQueryParam) {
  return request({
    url: '/monitor/job/list',
    method: 'get',
    params
  })
}

// 查询定时任务调度详细
export function getJob(jobId: number) {
  return request({
    url: '/monitor/job/' + jobId,
    method: 'get'
  })
}

// 新增定时任务调度
export function addJob(data: JobFormData) {
  return request({
    url: '/monitor/job',
    method: 'post',
    data
  })
}

// 修改定时任务调度
export function updateJob(data: JobFormData) {
  return request({
    url: '/monitor/job',
    method: 'put',
    data: data
  })
}

// 删除定时任务调度
export function delJob(ids: string) {
  return request({
    url: '/monitor/job/' + ids,
    method: 'delete'
  })
}

// 任务状态修改
export function changeJobStatus(data: JobChangeStatus) {
  return request({
    url: '/monitor/job/changeStatus',
    method: 'put',
    data
  })
}


// 定时任务立即执行一次
export function runJob(data: JobRun) {
  return request({
    url: '/monitor/job/run',
    method: 'put',
    data
  })
}