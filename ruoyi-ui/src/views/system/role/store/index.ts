import { RoleQueryParam, RoleFormData, RoleChangeStatus } from '@/types/api/system/role'
import { getDeptTreeByRoleId, listRole, getRoleInfo, addRole, updateRole, delRole, changeRoleStatus, dataScope } from "@/api/system/role"
import { getMenuTree, getMenuTreeByRoleId } from "@/api/system/menu"

const useRoleStore = defineStore('useRoleStore',
  {
    state: () => ({
      loading: false, // 角色数据loading状态
      queryParams: { pageNum: 1, pageSize: 10 } as RoleQueryParam, // 查询参数
      formData: {} as RoleFormData, // 角色新增、修改提交参数
      showSearch: true, // 是否显示搜索区域
      total: 0, // 角色数据总条目数
      roleData: [] as any, // 角色数据
      menuTreeData: [] as any, // 菜单树结构
      deptTreeData: [] as any, // 部门数据
      menuCheckedKeys: [] as number[], // 角色已授权的菜单
      deptCheckedKeys: [] as number[], // 角色已授权的部门
      showFormDialog: false, // 显示角色新增、修改对话框
      showAuthDataDialog: false, // 显示数据授权对话框
      formTitle: '', // 角色新增、修改对话框标题
      single: true, // 选择1条数据
      multiple: true, // 选择多条数据
      ids: [] as number[], // 选择数据的id集合
      menuExpand: false, // 菜单树展开
      menuNodeAll: false, // 菜单树全部选中
      deptExpand: false, // 部门树展开
      deptNodeAll: false // 部门树全部选中
    }),
    actions: {
      /** 重置操作表单 */
      resetFormData() {
        this.menuExpand = false
        this.menuNodeAll = false
        this.deptExpand = false
        this.deptNodeAll = false
        this.formData = {
          roleId: undefined,
          roleName: undefined,
          roleKey: undefined,
          roleSort: 0,
          status: '0',
          menuIds: [],
          deptIds: [],
          menuCheckStrictly: true,
          deptCheckStrictly: true,
          remark: undefined
        }
      },
      // 查询菜单下拉树数据
      async getMenuTree() {
        try {
          let { data } = await getMenuTree()
          this.menuTreeData = data
        } catch {}
      },
      // 根据角色ID查询菜单下拉树结构
      async getMenuTreeByRoleId(roleId: number) {
        try {
          let { menus, checkedKeys }: any = await getMenuTreeByRoleId(roleId)
          this.menuTreeData = menus
          this.menuCheckedKeys = checkedKeys
        } catch {}
      },
      // 根据角色ID查询部门下拉树结构
      async getDeptTreeByRoleId(roleId: number) {
        try {
          let { depts, checkedKeys }: any = await getDeptTreeByRoleId(roleId)
          this.deptTreeData = depts
          this.deptCheckedKeys = checkedKeys
        } catch {}
      },
      // 查询角色数据
      async getRoleData() {
        this.loading = true
        try {
          let { rows, total }: any = await listRole(this.queryParams)
          this.roleData = rows
          this.total = total
        } catch (error: any) {
        } finally {
          this.loading = false
        }
      },
      // 获取角色信息
      async getRoleInfo(roleId: number) {
        try {
          let { data }: any = await getRoleInfo(roleId)
          this.formData = data
        } catch {}
      },
      // 新增角色
      async addRole() {
        try {
          await addRole(this.formData)
        } catch {}
      },
      // 修改角色
      async updateRole() {
        try {
          await updateRole(this.formData)
        } catch {}
      },
      // 删除角色
      async delRole() {
        try {
          await delRole(this.ids.join(','))
        } catch {}
      },
      // 更改角色状态
      async changeRoleStatus(data: RoleChangeStatus) {
        try {
          await changeRoleStatus(data)
        } catch {}
      },
      // 授权角色数据
      async dataScope() {
        try {
          await dataScope(this.formData)
        } catch {}
      }
    }
  }
)

export default useRoleStore 