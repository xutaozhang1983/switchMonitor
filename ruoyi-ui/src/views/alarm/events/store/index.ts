import { EventsQueryParam } from '@/types/api/alarm/events'
import { listEvents } from "@/api/alarm/events"
import { listDeviceGroup } from '@/api/deviceGroup'
import { listDevice } from '@/api/device'
import dayjs from 'dayjs'

const useEventsStore = defineStore('useEventsStore',
  {
    state: () => ({
      loading: false, // 事件数据loading状态
      queryParams: { pageNum: 1, pageSize: 10 } as EventsQueryParam, // 查询参数
      showSearch: true, // 是否显示搜索区域
      total: 0, // 事件数据总条目数
      eventsData: [] as any, // 事件数据
      deviceGroupData: [] as any, // 设备组数据
      deviceData: [] as any, // 设备数据
    }),
    actions: {
      // 查询设备组数据
      async getDeviceGroupData() {
        this.loading = true
        try {
          let { rows }: any = await listDeviceGroup(this.queryParams)
          this.deviceGroupData = rows
        } catch (error: any) {
        } finally {
          this.loading = false
        }
      },
      // 查询设备数据
      async getDeviceData(groupId: number) {
        this.loading = true
        try {
          let sendData = {
            pageNum: 1,
            pageSize: 9999,
            groupId: groupId
          }
          let { rows }: any = await listDevice(sendData)
          this.deviceData = rows
        } catch (error: any) {
        } finally {
          this.loading = false
        }
      },
      // 查询事件数据
      async getEventsData() {
        this.loading = true
        if (!this.queryParams.startTime) this.queryParams.startTime = dayjs().format("YYYY-MM-DD") + ' 00:00:00'
        if (!this.queryParams.endTime) this.queryParams.endTime = dayjs().format("YYYY-MM-DD") + ' 23:59:59'
        try {
          let { rows, total }: any = await listEvents(this.queryParams)
          this.eventsData = rows
          this.total = total
        } catch (error: any) {
        } finally {
          this.loading = false
        }
      }
    }
  }
)

export default useEventsStore 