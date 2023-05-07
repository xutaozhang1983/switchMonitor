/**
 * 登录表单类型声明
 */
export interface LoginFormData {
  username: string,
  password: string,
  code: string,
  uuid: string
}

/**
 * 注册表单类型声明
 */
interface RegisterFormData {
  username: string,
  password: string,
  confirmPassword: string,
  code: string,
  uuid: string
}