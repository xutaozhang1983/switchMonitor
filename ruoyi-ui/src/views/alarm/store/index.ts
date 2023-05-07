const usealarmStore = defineStore('usealarmStore',
  {
    state: () => ({
      loading: false, // 告警数据loading状态
      queryParams: { pageNum: 1, pageSize: 10 } as any, // 查询参数
      formData: {} as any, // 告警新增、修改提交参数
      showSearch: true, // 是否显示搜索区域
      total: 0, // 告警数据总条目数
      alarmData: [] as any, // 告警数据
      showFormDialog: false, // 显示告警新增、修改对话框
      formTitle: '', // 告警新增、修改对话框标题
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
      // 查询告警数据
      async getalarmData() {
        this.loading = true
        try {
          //let { rows, total }: any = await listRole(this.queryParams)
          let rows = [
            { message: 'Network Path Monitor destination is reachable now', source: 'switch-1', alarmTime: '2023-05-03 14:30:30', importance: 1 },
            { message: 'Device Active and Responding', source: 'switch-2', alarmTime: '2023-05-03 14:30:30', importance: 2 },
            { message: 'The Agent Service is not communicating with server', source: 'switch-3', alarmTime: '2023-05-03 14:30:30', importance: 3 }
          ]
          let total = 3
          this.alarmData = rows
          this.total = total
        } catch (error: any) {
        } finally {
          this.loading = false
        }
      },
      // 查询告警详情
      async getAlarmInfo(id: number) {
        try {
          //await addRole(this.formData)
        } catch {}
      },
      // 新增告警
      async addAlarm() {
        try {
          //await addRole(this.formData)
        } catch {}
      },
      // 修改告警
      async updateAlarm() {
        try {
          //await updateRole(this.formData)
        } catch {}
      },
      // 删除告警
      async delAlarm() {
        try {
          // await delRole(this.ids.join(','))
        } catch {}
      },
      // 更改告警状态
      async changeAlarmStatus(data: any) {
        try {
          //await changeRoleStatus(data)
        } catch {}
      }
    }
  }
)

export default usealarmStore 