import { PageQueryParam } from "../base"

/**
 * 登录日志查询参数类型声明
 */
export interface LoginLogQueryParam extends PageQueryParam {
  ipaddr?: string | undefined, // ip地址
  userName: string | undefined, // 用户名
  status?: string | undefined // 状态
}