// 字典类型
interface DictType {
  label: string,
  value: string
}

// 正常停用
export const dictNormalDisable: DictType[] = [
  { label: '正常', value: '0' },
  { label: '停用', value: '1' }
]

// 正常暂停
export const dictNormalPause: DictType[] = [
  { label: '正常', value: '0' },
  { label: '暂停', value: '1' }
]

// 正常异常
export const dictNormalAbnormal: DictType[] = [
  { label: '正常', value: '0' },
  { label: '异常', value: '1' }
]

// 成功失败
export const dictSuccessFail: DictType[] = [
  { label: '成功', value: '0' },
  { label: '失败', value: '1' }
]

// 显示隐藏
export const dictShowHide: DictType[] = [
  { label: '显示', value: '0' },
  { label: '隐藏', value: '1' }
]

// 开启关闭
export const dictOpenClose: DictType[] = [
  { label: '开启', value: '0' },
  { label: '关闭', value: '1' }
]

// 用户性别
export const dictUserSex: DictType[] = [
  { label: '男', value: '0' },
  { label: '女', value: '1' },
  { label: '未知', value: '2' }
]

// 任务分组
export const dictJobGroup: DictType[] = [
  { label: '默认', value: 'DEFAULT' },
  { label: '系统', value: 'SYSTEM' }
]

// 系统是否
export const dictYesNo: DictType[] = [
  { label: '是', value: 'Y' },
  { label: '否', value: 'N' }
]

// 操作类型
export const dictOperType: DictType[] = [
  { label: '新增', value: '1' },
  { label: '修改', value: '2' },
  { label: '删除', value: '3' },
  { label: '授权', value: '4' },
  { label: '导出', value: '5' },
  { label: '导入', value: '6' },
  { label: '强退', value: '7' },
  { label: '生成代码', value: '8' },
  { label: '清空数据', value: '9' },
  { label: '其他', value: '0' }
]

// 设备类型
export const dictDeviceType: DictType[] = [
  { label: '网络设备', value: '1' },
  { label: '服务器', value: '2' },
  { label: '视频', value: '3' },
  { label: '其他', value: '0' }
]

// 设备厂商
export const dictDeviceManufacturer: DictType[] = [
  { label: 'CISCO', value: '1' },
  { label: 'HUAWEI', value: '2' },
  { label: 'H3C', value: '3' },
  { label: 'Juniper', value: '4' },
  { label: 'UNKNOW', value: '0' }
]

// snmp版本
export const dictSnmpVersion: DictType[] = [
  { label: 'version1', value: '1' },
  { label: 'version2c', value: '2' },
  { label: 'version3', value: '3' }
]

// 告警级别
export const dictAlarmLevel: DictType[] = [
  { label: 'INFO', value: 'INFO' },
  { label: 'WARING', value: 'WARING' },
  { label: 'ERROR', value: 'ERROR' }
]

