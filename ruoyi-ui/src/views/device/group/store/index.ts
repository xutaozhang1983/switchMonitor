import { DeviceGroupQueryParam, DeviceGroupFormData } from '@/types/api/deviceGroup'
import { listDeviceGroup, getDeviceGroup, addDeviceGroup, updateDeviceGroup, delDeviceGroup, changeDeviceGroupStatus } from "@/api/deviceGroup"
import { listUser } from '@/api/system/user'

const useDeviceGroupStore = defineStore('useDeviceGroupStore',
  {
    state: () => ({
      loading: false, // 设备组数据loading状态
      queryParams: { pageNum: 1, pageSize: 10 } as DeviceGroupQueryParam, // 查询参数
      formData: {} as DeviceGroupFormData, // 设备组新增、修改提交参数
      showSearch: true, // 是否显示搜索区域
      total: 0, // 设备组数据总条目数
      deviceGroupData: [] as any[], // 设备组数据
      userData: [] as any[], // 用户数据
      showFormDialog: false, // 显示设备组新增、修改对话框
      formTitle: '', // 设备组新增、修改对话框标题
      single: true, // 选择1条数据
      multiple: true, // 选择多条数据
      ids: [] as number[], // 选择数据的id集合
    }),
    actions: {
      /** 重置操作表单 */
      resetFormData() {
        let key: keyof DeviceGroupFormData
        for (key in this.formData) {
          this.formData[key] = undefined
        }
        this.formData.status = '0'
        this.formData.alarmMaxTimes = 0
      },
      // 查询用户数据
      async getUserData() {
        try {
          let { rows }: any = await listUser({ pageNum: 1, pageSize: 9999 })
          this.userData = rows
        } catch {}
      },
      // 查询设备组数据
      async getDeviceGroupData() {
        this.loading = true
        try {
          let { rows, total }: any = await listDeviceGroup(this.queryParams)
          this.deviceGroupData = rows
          this.total = total
        } catch (error: any) {
        } finally {
          this.loading = false
        }
      },
      // 查询设备组详情
      async getDeviceGroupInfo(id: number) {
        try {
          let { data } = await getDeviceGroup(id)
          this.formData = data
        } catch {}
      },
      // 新增设备组
      async addDeviceGroup() {
        try {
          await addDeviceGroup(this.formData)
        } catch {}
      },
      // 修改设备组
      async updateDeviceGroup() {
        try {
          await updateDeviceGroup(this.formData)
        } catch {}
      },
      // 删除设备组
      async delDeviceGroup() {
        try {
          await delDeviceGroup(this.ids.join(','))
        } catch {}
      },
      // 更改设备组状态
      async changeDeviceGroupStatus(data: any) {
        try {
          await changeDeviceGroupStatus(data)
        } catch {}
      }
    }
  }
)

export default useDeviceGroupStore 