import request from '@/utils/request'
import { GenQueryParam } from '@/types/api/tool/gen'

// 查询生成表数据
export function listTable(query: GenQueryParam) {
  return request({
    url: '/tool/gen/list',
    method: 'get',
    params: query
  })
}
// 查询db数据库列表
export function listDbTable(query: GenQueryParam) {
  return request({
    url: '/tool/gen/db/list',
    method: 'get',
    params: query
  })
}

// 查询表详细信息
export function getGenTable(tableId: number) {
  return request({
    url: '/tool/gen/' + tableId,
    method: 'get'
  })
}

// 修改代码生成信息
export function updateGenTable(data: any) {
  return request({
    url: '/tool/gen',
    method: 'put',
    data: data
  })
}

// 导入表
export function importTable(data: { tables: string }) {
  return request({
    url: '/tool/gen/importTable',
    method: 'post',
    params: data
  })
}

// 预览生成代码
export function previewTable(tableId: number) {
  return request({
    url: '/tool/gen/preview/' + tableId,
    method: 'get'
  })
}

// 删除表数据
export function delTable(ids: string) {
  return request({
    url: '/tool/gen/' + ids,
    method: 'delete'
  })
}

// 生成代码（自定义路径）
export function genCode(tableNames: string) {
  return request({
    url: '/tool/gen/genCode/' + tableNames,
    method: 'get'
  })
}

// 同步数据库
export function synchDb(tableName: string) {
  return request({
    url: '/tool/gen/synchDb/' + tableName,
    method: 'get'
  })
}
