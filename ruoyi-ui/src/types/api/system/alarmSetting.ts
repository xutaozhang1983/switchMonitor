/**
 * 告警设置表单类型声明
 */
export interface AlarmFormData {
  mediaType: string | undefined, // 发送类型 EMAIL或 SMS
  host?: string | undefined, // 服务器名
  port?: number | undefined, // 端口
  sender?: string | undefined, // 发送邮箱
  enableSsl?: string | undefined, // 启用ssl
  enableTls?: string | undefined, // 启用tls
  username?: string | undefined, // 用户名
  passwd?: string | undefined, // 密码
  status?: string | undefined
}