import { DeviceQueryParam, DeviceFormData } from '@/types/api/device'
import { listDevice, getDevice, addDevice, updateDevice, delDevice } from "@/api/device"

const useDeviceStore = defineStore('useDeviceStore',
  {
    state: () => ({
      loading: false, // 设备数据loading状态
      queryParams: { pageNum: 1, pageSize: 10 } as DeviceQueryParam, // 查询参数
      formData: {} as DeviceFormData, // 设备新增、修改提交参数
      showSearch: true, // 是否显示搜索区域
      total: 0, // 设备数据总条目数
      deviceData: [] as any[], // 设备数据
      showFormDialog: false, // 显示设备新增、修改对话框
      formTitle: '', // 设备新增、修改对话框标题
      single: true, // 选择1条数据
      multiple: true, // 选择多条数据
      ids: [] as number[], // 选择数据的id集合
    }),
    actions: {
      /** 重置操作表单 */
      resetFormData() {
        let key: keyof any
        for (key in this.formData) {
          this.formData[key] = undefined
        }
        this.formData.status = '0'
      },
      // 查询设备数据
      async getDeviceData() {
        this.loading = true
        try {
          let { rows, total }: any = await listDevice(this.queryParams)
          this.deviceData = rows
          this.total = total
        } catch (error: any) {
        } finally {
          this.loading = false
        }
      },
      // 查询设备详情
      async getDeviceInfo(id: number) {
        try {
          //await addRole(this.formData)
        } catch {}
      },
      // 新增设备
      async addDevice() {
        try {
          //await addRole(this.formData)
        } catch {}
      },
      // 修改设备
      async updateDevice() {
        try {
          //await updateRole(this.formData)
        } catch {}
      },
      // 删除设备
      async delDevice() {
        try {
          // await delRole(this.ids.join(','))
        } catch {}
      },
      // 更改设备状态
      async changeDeviceStatus(data: any) {
        try {
          //await changeRoleStatus(data)
        } catch {}
      }
    }
  }
)

export default useDeviceStore 