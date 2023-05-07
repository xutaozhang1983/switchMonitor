/**
 * 菜单查询参数类型声明
 */
export interface MenuQueryParam {
  menuName: string | undefined, // 菜单名称
  status: string | undefined // 状态
}

/**
 * 菜单表单类型声明
 */
export interface MenuFormData {
  menuId?: number | undefined, // 菜单ID
  parentId: number | undefined, // 父ID
  menuName: string | undefined, // 菜单名称
  icon?: string | undefined, // 图标
  menuType: string | undefined, // 菜单类型
  orderNum: number | undefined, // 排序
  path?: string | undefined, // 路由
  component?: string | undefined, // 组件路径
  perms?: string | undefined, // 权限
  query?: string | undefined, // 路由参数
  isFrame: string | undefined, // 是否外链
  isCache: string | undefined, // 是否缓存
  visible: string | undefined, // 是否可见
  status: string | undefined // 状态
}