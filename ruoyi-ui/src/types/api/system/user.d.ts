import { PageQueryParam } from "../base"

/**
 * 用户查询参数类型声明
 */
export interface UserQueryParam extends PageQueryParam {
  userName?: string | undefined,
  phonenumber?: string | undefined,
  status?: string | undefined,
  deptId?: number | undefined,
  beginTime?: string | undefined,
  endTime?: string | undefined
}

/**
 * 用户表单类型声明
 */
export interface UserFormData {
  userId: number | undefined,
  deptId: number | undefined,
  userName: string | undefined,
  nickName: string | undefined,
  password: string | undefined,
  phonenumber: string | undefined,
  email: string | undefined,
  sex: string | undefined,
  status: string | undefined,
  remark: string | undefined,
  postIds: number[] | undefined,
  roleIds: number[] | undefined
}

/**
 * 用户状态修改类型声明
 */
export interface UserChangeStatus {
  userId: number,
  status: string
}

/**
 * 用户密码重置类型声明
 */
export interface UserResetPassword {
  userId: number,
  password: string
}

/**
 * 更新用户密码
 */
export interface UserUpdatePassword {
  oldPassword: string,
  newPassword: string
}

/**
 * 更新授权角色
 */
export interface UserUpdateAuthRole {
  userId: number,
  roleIds: string
}