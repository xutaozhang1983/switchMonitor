import { getDeviceCount, getDeviceGroupCount, getFlowCount } from "@/api/home"
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
        deviceNormal: [] as number[],
        deviceAbnormal: [] as number[]
      }, // 设备组数据
      deviceFlowData: {
        inData: {
          yAxisData: [] as string[],
          seriesData: [] as (string|number)[]
        },
        outData: {
          yAxisData: [] as string[],
          seriesData: [] as (string|number)[]
        }
      }, // 设备流量数据
      portFlowData: {
        inData: {
          yAxisData: [] as string[],
          seriesData: [] as (string|number)[]
        },
        outData: {
          yAxisData: [] as string[],
          seriesData: [] as (string|number)[]
        }
      } // 端口流量数据
    }),
    actions: {
      // 初始化界面
      initPage() {
        this.getDeviceCount()
        this.listEvents()
        this.getDeviceGroupCount()
        this.getDeviceFlowCount()
        this.getPortFlowCount()
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
        } catch {}
      },
      // 查询告警数据
      async listEvents() {
        try {
          let sendData = {
            pageNum: 1,
            pageSize: 9999,
            startTime: dayjs().subtract(7, 'day').format("YYYY-MM-DD") + ' 00:00:00',
            endTime: dayjs().format("YYYY-MM-DD") + ' 23:59:59'
          }
          let { rows }: any = await listEvents(sendData)
          this.eventsData = rows
        } catch {}
      },
      // 查询设备组数据
      async getDeviceGroupCount() {
        try {
          let { data }: any = await getDeviceGroupCount()
          this.deviceGroupData.xAxisData.splice(0)
          this.deviceGroupData.deviceNormal.splice(0)
          this.deviceGroupData.deviceAbnormal.splice(0)
          for (let key in data) {
            this.deviceGroupData.xAxisData.push(key)
            this.deviceGroupData.deviceNormal.push(data[key].ok)
            this.deviceGroupData.deviceAbnormal.push(data[key].err)
          }
        } catch {}
      },
      // 查询设备流量排名
      async getDeviceFlowCount() {
        try {
          let sendData = {
            //hours: 4,
            hours: 1000,
            topType: 2
          }
          let { data }: any = await getFlowCount(sendData)
          this.deviceFlowData.inData.yAxisData.splice(0)
          this.deviceFlowData.inData.seriesData.splice(0)
          this.deviceFlowData.outData.yAxisData.splice(0)
          this.deviceFlowData.outData.seriesData.splice(0)
          for (let item of data.ifIn) {
            this.deviceFlowData.inData.yAxisData.push(item.deviceName)
            this.deviceFlowData.inData.seriesData.push((item.value/8/1024).toFixed(2))
          }
          for (let item of data.ifOut) {
            this.deviceFlowData.outData.yAxisData.push(item.deviceName)
            this.deviceFlowData.outData.seriesData.push((item.value/8/1024).toFixed(2))
          }
        } catch {}
      },
      // 查询端口流量排名
      async getPortFlowCount() {
        try {
          let sendData = {
            //hours: 4,
            hours: 1000,
            topType: 1
          }
          let { data }: any = await getFlowCount(sendData)
          this.portFlowData.inData.yAxisData.splice(0)
          this.portFlowData.inData.seriesData.splice(0)
          this.portFlowData.outData.yAxisData.splice(0)
          this.portFlowData.outData.seriesData.splice(0)
          for (let item of data.ifIn) {
            this.portFlowData.inData.yAxisData.push(item.itemName)
            this.portFlowData.inData.seriesData.push((item.value/8/1024).toFixed(2))
          }
          for (let item of data.ifOut) {
            this.portFlowData.outData.yAxisData.push(item.itemName)
            this.portFlowData.outData.seriesData.push((item.value/8/1024).toFixed(2))
          }
        } catch {}
      }
    }
  }
)

export default useHomeStore 