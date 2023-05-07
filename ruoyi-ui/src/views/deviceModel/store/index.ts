const useDeviceModelStore = defineStore('useDeviceModelStore',
  {
    state: () => ({
      loading: false, // 型号数据loading状态
      queryParams: { pageNum: 1, pageSize: 10 } as any, // 查询参数
      formData: {} as any, // 型号新增、修改提交参数
      showSearch: true, // 是否显示搜索区域
      total: 0, // 型号数据总条目数
      deviceModelData: [] as any, // 型号数据
      showFormDialog: false, // 显示型号新增、修改对话框
      formTitle: '', // 型号新增、修改对话框标题
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
      // 查询型号数据
      async getDeviceModelData() {
        this.loading = true
        try {
          //let { rows, total }: any = await listRole(this.queryParams)
          let rows = [
            { DeviceModelId: 1, DeviceModelName: 'CBS110-16T-CN', factoryName: 'Cisco', port: 16, params: '16口千兆交换机', createTime: '2023-05-03 14:30:30', remark: '' },
            { DeviceModelId: 2, DeviceModelName: 'CBS110-24T-CN', factoryName: 'Cisco', port: 24, params: '24口千兆交换机', createTime: '2023-05-03 14:30:30', remark: '' },
            { DeviceModelId: 3, DeviceModelName: 'SG220-52-K9-CN', factoryName: 'Cisco', port: 24, params: '24口千兆交换机', createTime: '2023-05-03 14:30:30', remark: '' }
          ]
          let total = 3
          this.deviceModelData = rows
          this.total = total
        } catch (error: any) {
        } finally {
          this.loading = false
        }
      },
      // 查询型号详情
      async getDeviceModelInfo(id: number) {
        try {
          //await addRole(this.formData)
        } catch {}
      },
      // 新增型号
      async addDeviceModel() {
        try {
          //await addRole(this.formData)
        } catch {}
      },
      // 修改型号
      async updateDeviceModel() {
        try {
          //await updateRole(this.formData)
        } catch {}
      },
      // 删除型号
      async delDeviceModel() {
        try {
          // await delRole(this.ids.join(','))
        } catch {}
      },
      // 更改型号状态
      async changeDeviceModelStatus(data: any) {
        try {
          //await changeRoleStatus(data)
        } catch {}
      }
    }
  }
)

export default useDeviceModelStore 