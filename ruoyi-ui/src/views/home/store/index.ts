import { getDeviceCount, getDeviceGroupCount } from "@/api/home"
import { listEvents } from '@/api/alarm/events'

import dayjs from 'dayjs'

const useHomeStore = defineStore('useHomeStore',
  {
    state: () => ({
      deviceData: { 
        total: 0,
        monitor: 0,
        normal: 0,
        abnormal: 0
      }, // 设备数据
      eventsData: [] as any[], // 告警数据
      deviceTypeData: [] as any[], // 设备类型数据
      deviceGroupData: {
        xAxisData: [] as string[],
        deviceMonitor: [] as number[],
        deviceNormal: [] as number[],
        deviceAbnormal: [] as number[]
      }, // 设备组数据
      showFormDialog: false, // 显示设备新增、修改对话框
      formTitle: '', // 设备新增、修改对话框标题
      single: true, // 选择1条数据
      multiple: true, // 选择多条数据
      ids: [] as number[], // 选择数据的id集合
    }),
    actions: {
      // 初始化界面
      async initPage() {
        await this.getDeviceCount()
        await this.listEvents()
        await this.getDeviceGroupCount()
      },
      // 查询设备数据
      async getDeviceCount() {
        try {
          let { data }: any = await getDeviceCount()
          this.deviceData.total = data.all || 0
          this.deviceData.monitor = this.deviceData.total - data.unknow || 0
          this.deviceData.normal = data.ok || 0
          this.deviceData.abnormal = data.err || 0
          this.deviceTypeData = [
            { value: this.deviceData.total, name: '网络设备' },
            { value: 0, name: '摄像头' }
          ]
        } catch (error: any) {
        }
      },
      // 查询告警数据
      async listEvents() {
        try {
          let sendData = {
            pageNum: 1,
            pageSize: 9999,
            startTime: dayjs().format("YYYY-MM-DD") + ' 00:00:00',
            endTime: dayjs().format("YYYY-MM-DD") + ' 23:59:59'
          }
          let { rows }: any = await listEvents(sendData)
          this.eventsData = rows
        } catch (error: any) {
        }
      },
       // 查询设备组数据
      async getDeviceGroupCount() {
        try {
          let { data }: any = await getDeviceGroupCount()
          for (let key in data) {
            this.deviceGroupData.xAxisData.push(key)
            this.deviceGroupData.deviceMonitor.push(data[key].unknow)
            this.deviceGroupData.deviceNormal.push(data[key].ok)
            this.deviceGroupData.deviceAbnormal.push(data[key].err)
          }
        } catch (error: any) {
        }
      }
    }
  }
)

export default useHomeStore 