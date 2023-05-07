const useFactoryStore = defineStore('useFactoryStore',
  {
    state: () => ({
      loading: false, // 厂商数据loading状态
      queryParams: { pageNum: 1, pageSize: 10 } as any, // 查询参数
      formData: {} as any, // 厂商新增、修改提交参数
      showSearch: true, // 是否显示搜索区域
      total: 0, // 厂商数据总条目数
      factoryData: [] as any, // 厂商数据
      showFormDialog: false, // 显示厂商新增、修改对话框
      formTitle: '', // 厂商新增、修改对话框标题
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
      // 查询厂商数据
      async getFactoryData() {
        this.loading = true
        try {
          //let { rows, total }: any = await listRole(this.queryParams)
          let rows = [
            { factoryId: 1, factoryName: 'Cisco', createTime: '2023-05-03 14:30:30', remark: '供应商xxxx: 010-000000' },
            { factoryId: 2, factoryName: 'Huawei', createTime: '2023-05-03 14:30:30', remark: '供应商xxxx: 010-000000' },
            { factoryId: 3, factoryName: 'h3c', createTime: '2023-05-03 14:30:30', remark: '供应商xxxx: 010-000000' }
          ]
          let total = 3
          this.factoryData = rows
          this.total = total
        } catch (error: any) {
        } finally {
          this.loading = false
        }
      },
      // 查询厂商详情
      async getFactoryInfo(id: number) {
        try {
          //await addRole(this.formData)
        } catch {}
      },
      // 新增厂商
      async addFactory() {
        try {
          //await addRole(this.formData)
        } catch {}
      },
      // 修改厂商
      async updateFactory() {
        try {
          //await updateRole(this.formData)
        } catch {}
      },
      // 删除厂商
      async delFactory() {
        try {
          // await delRole(this.ids.join(','))
        } catch {}
      },
      // 更改厂商状态
      async changeFactoryStatus(data: any) {
        try {
          //await changeRoleStatus(data)
        } catch {}
      }
    }
  }
)

export default useFactoryStore 