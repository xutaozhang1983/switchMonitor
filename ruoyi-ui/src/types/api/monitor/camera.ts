import { PageQueryParam } from "../base"

/**
 * 编码设备查询参数类型声明
 */
export interface EncodeQueryParam extends PageQueryParam {
  name?: string | undefined, // 编码设备名字
  status?: string | undefined // 状态
}

/**
 * 摄像头查询参数类型声明
 */
export interface CameraQueryParam extends PageQueryParam {
  cameraName?: string | undefined, // 摄像头名字
  encodeDevIndexCode?: number | undefined, // 编码设备id
  installLocation?: string | undefined, // 安装位置
  status?: string | undefined // 状态
}

