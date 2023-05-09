const useMonitorStore = defineStore('useMonitorStore',
  {
    state: () => ({
      loading: false, // 监控数据loading状态
      queryParams: { pageNum: 1, pageSize: 10 } as any, // 查询参数
      formData: {} as any, // 监控新增、修改提交参数
      showSearch: true, // 是否显示搜索区域
      total: 0, // 监控数据总条目数
      monitorData: [] as any, // 监控数据
      showFormDialog: false, // 显示监控新增、修改对话框
      formTitle: '', // 监控新增、修改对话框标题
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
      // 查询监控数据
      async getMonitorData() {
        this.loading = true
        try {
          //let { rows, total }: any = await listRole(this.queryParams)
          let rows = [
            { name: 'P1', in: '133.5', out: '20.1', status: '0', mac: '98:5a:eb:d5:0a:fd', ip: '192.168.0.1' },
            { name: 'P2', in: '133.5', out: '20.1', status: '1', mac: '98:5a:eb:d5:0a:fd', ip: '192.168.0.1' },
            { name: 'P3', in: '133.5', out: '20.1', status: '1', mac: '98:5a:eb:d5:0a:fd', ip: '192.168.0.1' },
            { name: 'P4', in: '133.5', out: '20.1', status: '0', mac: '98:5a:eb:d5:0a:fd', ip: '192.168.0.1' },
            { name: 'P5', in: '133.5', out: '20.1', status: '0', mac: '98:5a:eb:d5:0a:fd', ip: '192.168.0.1' },
            { name: 'P6', in: '133.5', out: '20.1', status: '0', mac: '98:5a:eb:d5:0a:fd', ip: '192.168.0.1' },
            { name: 'P7', in: '133.5', out: '20.1', status: '0', mac: '98:5a:eb:d5:0a:fd', ip: '192.168.0.1' },
            { name: 'P8', in: '133.5', out: '20.1', status: '0', mac: '98:5a:eb:d5:0a:fd', ip: '192.168.0.1' },
            { name: 'P9', in: '133.5', out: '20.1', status: '0', mac: '98:5a:eb:d5:0a:fd', ip: '192.168.0.1' },
            { name: 'P10', in: '133.5', out: '20.1', status: '0', mac: '98:5a:eb:d5:0a:fd', ip: '192.168.0.1' },
            { name: 'P11', in: '133.5', out: '20.1', status: '0', mac: '98:5a:eb:d5:0a:fd', ip: '192.168.0.1' },
            { name: 'P12', in: '133.5', out: '20.1', status: '0', mac: '98:5a:eb:d5:0a:fd', ip: '192.168.0.1' },
            { name: 'P13', in: '133.5', out: '20.1', status: '1', mac: '98:5a:eb:d5:0a:fd', ip: '192.168.0.1' },
            { name: 'P14', in: '133.5', out: '20.1', status: '1', mac: '98:5a:eb:d5:0a:fd', ip: '192.168.0.1' },
            { name: 'P15', in: '133.5', out: '20.1', status: '0', mac: '98:5a:eb:d5:0a:fd', ip: '192.168.0.1' },
            { name: 'P16', in: '133.5', out: '20.1', status: '1', mac: '98:5a:eb:d5:0a:fd', ip: '192.168.0.1' },
            { name: 'P17', in: '133.5', out: '20.1', status: '1', mac: '98:5a:eb:d5:0a:fd', ip: '192.168.0.1' },
            { name: 'P18', in: '133.5', out: '20.1', status: '0', mac: '98:5a:eb:d5:0a:fd', ip: '192.168.0.1' }
          ]
          let total = 3
          this.monitorData = rows
          this.total = total
        } catch (error: any) {
        } finally {
          this.loading = false
        }
      },
      // 查询监控详情
      async getMonitorInfo(id: number) {
        try {
          //await addRole(this.formData)
        } catch {}
      },
      // 新增监控
      async addMonitor() {
        try {
          //await addRole(this.formData)
        } catch {}
      },
      // 修改监控
      async updateMonitor() {
        try {
          //await updateRole(this.formData)
        } catch {}
      },
      // 删除监控
      async delMonitor() {
        try {
          // await delRole(this.ids.join(','))
        } catch {}
      },
      // 更改监控状态
      async changeMonitorStatus(data: any) {
        try {
          //await changeRoleStatus(data)
        } catch {}
      }
    }
  }
)

export default useMonitorStore 