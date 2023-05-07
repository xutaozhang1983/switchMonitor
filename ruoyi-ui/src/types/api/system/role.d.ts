import { PageQueryParam } from "../base"

/**
 * 角色查询参数类型声明
 */
export interface RoleQueryParam extends PageQueryParam{
  roleName: string | undefined,
  roleKey: string | undefined,
  status: string | undefined,
  beginTime: string | undefined,
  endTime: string | undefined
}

/**
 * 角色表单类型声明
 */
export interface RoleFormData {
  roleId: number | undefined,
  roleName: string | undefined,
  roleKey: string | undefined,
  roleSort: number | undefined,
  status: string | undefined,
  menuIds: number[] | undefined,
  deptIds: number[] | undefined,
  dataScope?: string | undefined,
  menuCheckStrictly: boolean | undefined,
  deptCheckStrictly: boolean | undefined,
  remark: string | undefined
}

/**
 * 角色状态修改类型声明
 */
export interface RoleChangeStatus {
  roleId: number,
  status: string
}

/**
 * 授权、未授权用户列表查询类型声明
 */
export interface AllocatedUserQueryParam extends PageQueryParam {
  roleId: number | undefined,
  userName: string | undefined,
  phonenumber: string | undefined
}