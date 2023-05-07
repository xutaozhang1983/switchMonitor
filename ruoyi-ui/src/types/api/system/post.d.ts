import { PageQueryParam } from "../base"

/**
 * 岗位查询参数类型声明
 */
export interface PostQueryParam extends PageQueryParam {
  postCode: string | undefined, // 岗位编码
  postName: string | undefined, // 岗位名称
  status: string | undefined // 状态
}

/**
 * 岗位表单类型声明
 */
export interface PostFormData {
  postId?: number | undefined, // 岗位ID
  postCode: string | undefined, // 岗位编码
  postName: string | undefined, // 岗位名称
  postSort: number | undefined, // 排序
  status: string | undefined, // 状态
  remark: string | undefined, // 备注
}