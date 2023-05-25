import { DeviceQueryParam, DeviceFormData } from '@/types/api/device'
import { listDevice, getDevice, addDevice, updateDevice, delDevice, changeDeviceEnable } from "@/api/device"
import { listDeviceGroup } from '@/api/deviceGroup'

const useNetMonitorStore = defineStore('useNetMonitorStore',
  {
    state: () => ({
      loading: false, // 设备数据loading状态
      queryParams: { pageNum: 1, pageSize: 10, deviceType: '1' } as DeviceQueryParam, // 查询参数
      formData: {} as DeviceFormData, // 设备新增、修改提交参数
      showSearch: true, // 是否显示搜索区域
      total: 0, // 设备数据总条目数
      deviceData: [] as any[], // 设备数据
      deviceGroupData: [] as any[], // 设备组数据
      showFormDialog: false, // 显示设备新增、修改对话框
      formTitle: '', // 设备新增、修改对话框标题
      single: true, // 选择1条数据
      multiple: true, // 选择多条数据
      ids: [] as number[], // 选择数据的id集合
      resetDeviceGroupKey: false // 是否重置设备组的key
    }),
    actions: {
      /** 重置操作表单 */
      resetFormData() {
        let key: keyof DeviceFormData
        for (key in this.formData) {
          this.formData[key] = undefined
        }
        this.formData.deviceType = '1'
        this.formData.enable = '0'
        this.formData.snmpCommunity = 'public'
        this.formData.snmpPort = 161
      },
      // 查询设备组数据
      async getDeviceGroupData() {
        try {
          let { rows }: any = await listDeviceGroup({ pageNum: 1, pageSize: 9999 })
          this.deviceGroupData = [{ id: 0, label: '全部', children: [] }]
          if (rows.length) {
            for (let item of rows) {
              this.deviceGroupData[0].children.push(
                { id: item.id, label: item.groupName }
              )
            }
          }
        } catch {}
      },
      // 查询设备数据
      async getDeviceData() {
        this.loading = true
        try {
          let { rows, total }: any = await listDevice(this.queryParams)
          rows.map((item: any) => { 
            item.enable = item.enable.toString()
            item.snmpVersion = item.snmpVersion.toString()
          })
          this.deviceData = rows
          this.total = total
        } catch (error: any) {
        } finally {
          this.loading = false
        }
      },
      // 查询设备详情
      async getDeviceInfo(deviceId: number) {
        try {
          let {data} = await getDevice(deviceId)
          data.enable = data.enable.toString()
          data.snmpVersion = data.snmpVersion.toString()
          this.formData = data
        } catch {}
      },
      // 新增设备
      async addDevice() {
        try {
          await addDevice(this.formData)
        } catch {}
      },
      // 修改设备
      async updateDevice() {
        try {
          await updateDevice(this.formData)
        } catch {}
      },
      // 删除设备
      async delDevice() {
        try {
          await delDevice(this.ids.join(','))
        } catch {}
      },
      // 启用或关闭设备
      async changeDeviceEnable(data: any) {
        try {
          await changeDeviceEnable(data)
        } catch {}
      }
    }
  }
)

export default useNetMonitorStore 