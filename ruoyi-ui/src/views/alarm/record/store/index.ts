import { RecordQueryParam } from '@/types/api/alarm/record'
import { listRecord } from "@/api/alarm/record"
import { listDeviceGroup } from '@/api/deviceGroup'
import { listDevice } from '@/api/device'
import dayjs from 'dayjs'

const useRecordStore = defineStore('useRecordStore',
  {
    state: () => ({
      loading: false, // 记录数据loading状态
      queryParams: { pageNum: 1, pageSize: 10 } as RecordQueryParam, // 查询参数
      showSearch: true, // 是否显示搜索区域
      total: 0, // 记录数据总条目数
      recordData: [] as any, // 记录数据
      deviceGroupData: [] as any, // 设备组数据
      deviceData: [] as any, // 设备数据
      showCloseDialog: false, // 显示关闭对话框
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
      // 查询记录数据
      async getRecordData() {
        this.loading = true
        if (!this.queryParams.startTime) this.queryParams.startTime = dayjs().format("YYYY-MM-DD") + ' 00:00:00'
        if (!this.queryParams.endTime) this.queryParams.endTime = dayjs().format("YYYY-MM-DD") + ' 23:59:59'
        try {
          let { rows, total }: any = await listRecord(this.queryParams)
          this.recordData = rows
          this.total = total
        } catch (error: any) {
        } finally {
          this.loading = false
        }
      }
    }
  }
)

export default useRecordStore 