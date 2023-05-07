import request from '@/utils/request'
import { DeptQueryParam, DeptFormData } from '@/types/api/system/dept'

// 查询部门列表
export function listDept(query: DeptQueryParam) {
  return request({
    url: '/system/dept/list',
    method: 'get',
    params: query
  })
}

// 查询部门列表（排除节点）
export function listDeptExcludeChild(deptId: number) {
  return request({
    url: '/system/dept/list/exclude/' + deptId,
    method: 'get'
  })
}

// 查询部门详细
export function getDept(deptId: number) {
  return request({
    url: '/system/dept/' + deptId,
    method: 'get'
  })
}

// 新增部门
export function addDept(data: DeptFormData) {
  return request({
    url: '/system/dept',
    method: 'post',
    data: data
  })
}

// 修改部门
export function updateDept(data: DeptFormData) {
  return request({
    url: '/system/dept',
    method: 'put',
    data: data
  })
}

// 删除部门
export function delDept(deptId: number) {
  return request({
    url: '/system/dept/' + deptId,
    method: 'delete'
  })
}