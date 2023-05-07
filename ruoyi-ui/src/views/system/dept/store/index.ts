import { DeptQueryParam, DeptFormData } from '@/types/api/system/dept'
import { listDept, getDept, addDept, updateDept, delDept, listDeptExcludeChild } from "@/api/system/dept"
import { handleTree } from '@/utils/common'

const useDeptStore = defineStore('useDeptStore',
  {
    state: () => ({
      loading: false, // 部门数据loading状态
      queryParams: { } as DeptQueryParam, // 查询参数
      formData: {} as DeptFormData, // 部门新增、修改提交参数
      deptData: [] as any[], // 部门数据
      deptTreeData: [] as any[], // 部门树数据
      showSearch: true, // 是否显示搜索区域
      showFormDialog: false,  // 显示部门新增、修改对话框
      formTitle: '', // 部门新增、修改对话框标题
      refreshTable: true, // 刷新表格数据
      isExpandAll: true // 表格数据是否全部展开
    }),
    actions: {
      /** 重置操作表单 */
      resetFormData() {
        this.formData = {
          deptId: undefined,
          parentId: 0,
          deptName: undefined,
          orderNum: 0,
          leader: undefined,
          phone: undefined,
          email: undefined,
          status: '0'
        }
      },
      // 查询部门数据
      async getDeptData() {
        this.loading = true
        try {
          let { data }: any = await listDept(this.queryParams)
          this.deptData = handleTree(data, 'deptId')
          this.deptTreeData = this.deptData
        } catch (error: any) {
        } finally {
          this.loading = false
        }
      },
      // 查询部门列表（排除节点)
      async listDeptExcludeChild(deptId: number) {
        try {
          let { data }: any = await listDeptExcludeChild(deptId)
          this.deptTreeData = handleTree(data, 'deptId')
        } catch {}
      },
      // 获取部门信息
      async getDeptInfo(deptId: number) {
        try {
          let { data }: any = await getDept(deptId)
          this.formData = data
        } catch {}
      },
      // 新增部门
      async addDept() {
        try {
          await addDept(this.formData)
        } catch {}
      },
      // 修改部门
      async updateDept() {
        try {
          await updateDept(this.formData)
        } catch {}
      },
      // 删除部门
      async delDept(deptId: number) {
        try {
          await delDept(deptId)
        } catch {}
      }
    }
  }
)

export default useDeptStore 