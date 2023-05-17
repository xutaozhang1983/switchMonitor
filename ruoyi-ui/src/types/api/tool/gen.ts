import { PageQueryParam } from "../base"

/**
 * 代码查询参数类型声明
 */
export interface GenQueryParam extends PageQueryParam {
  tableName: string | undefined, // 表名称
  tableComment: string | undefined // 表描述
}