/**
 * 部门查询参数类型声明
 */
export interface DeptQueryParam {
  deptName: string | undefined, // 部门名称
  status: string | undefined // 状态
}

/**
 * 部门表单类型声明
 */
export interface DeptFormData {
  deptId?: number | undefined, // 部门ID
  parentId: number | undefined, // 父ID
  deptName: string | undefined, // 部门名称
  orderNum: number | undefined, // 排序
  leader: string | undefined, // 领导
  phone?: string | undefined, // 电话
  email?: string | undefined, // 邮箱
  status: string | undefined // 状态
}