import { DeviceQueryParam } from '@/types/api/device'
import { listDevice } from "@/api/device"
import { listDeviceGroup } from '@/api/deviceGroup'

const useNetMonitorStore = defineStore('useNetMonitorStore',
  {
    state: () => ({
      loading: false, // 设备数据loading状态
      queryParams: { pageNum: 1, pageSize: 10, deviceType: '1' } as DeviceQueryParam, // 查询参数
      showSearch: true, // 是否显示搜索区域
      total: 0, // 设备数据总条目数
      deviceData: [] as any[], // 设备数据
      deviceGroupData: [] as any[], // 设备组数据
      resetDeviceGroupKey: false // 是否重置设备组树的key
    }),
    actions: {
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
      }
    }
  }
)

export default useNetMonitorStore 