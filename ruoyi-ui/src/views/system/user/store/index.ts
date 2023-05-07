import { localStorage } from '@/utils/storage'
import { UserQueryParam, UserFormData, UserChangeStatus, UserResetPassword } from '@/types/api/system/user'
import { changeUserStatus, listUser, resetUserPwd, delUser, getUserInfo, updateUser, addUser, deptTreeSelect } from "@/api/system/user";

const useUserStore = defineStore('useUserStore',
  {
    state: () => ({
      loading: false, // 用户数据loading状态
      queryParams: { pageNum: 1, pageSize: 10 } as UserQueryParam, // 查询参数
      formData: {} as UserFormData, // 用户新增、修改提交参数
      columns: [
        { key: 0, label: `用户编号`, visible: true },
        { key: 1, label: `用户名称`, visible: true },
        { key: 2, label: `用户昵称`, visible: true },
        { key: 3, label: `部门`, visible: true },
        { key: 4, label: `手机号码`, visible: true },
        { key: 5, label: `状态`, visible: true },
        { key: 6, label: `创建时间`, visible: true },
      ], // 用户数据表头
      uploadParams: {
        open: false, // 是否显示弹出层（用户导入）
        title: "", // 弹出层标题（用户导入）
        isUploading: false, // 是否禁用上传
        updateSupport: 0, // 是否更新已经存在的用户数据
        headers: { Authorization: "Bearer " + localStorage.get("token") }, // 设置上传的请求头部
        url: import.meta.env.VITE_APP_BASE_API + "/system/user/importData" // 上传的地址
      }, // 用户导入参数
      showSearch: true, // 是否显示搜索区域
      total: 0, // 用户数据总条目数
      deptTreeData: [] as any[], // 部门树结构
      resetDeptTreeKey: false, // 重置部门树的key
      userData: [] as any[], // 用户数据
      postData: [] as any[], // 岗位数据
      roleData: [] as any[], // 角色数据
      showFormDialog: false, // 显示用户新增、修改对话框
      formTitle: '', // 用户新增、修改对话框标题
      showImportDialog: false, // 显示用户导入对话框
      single: true, // 选择1条数据
      multiple: true, // 选择多条数据
      ids: [] as number[], // 选择数据的id集合
    }),
    actions: {
      /** 重置操作表单 */
      resetFormData() {
        let key: keyof UserFormData
        for (key in this.formData) {
          this.formData[key] = undefined
        }
      },
      // 查询部门下拉树结构
      async getDeptTreeData() {
        try {
          let { data } = await deptTreeSelect()
          this.deptTreeData = data
        } catch {}
      },
      // 查询用户数据
      async getUserData() {
        this.loading = true
        try {
          let { rows, total }: any = await listUser(this.queryParams)
          this.userData = rows
          this.total = total
        } catch (error: any) {
        } finally {
          this.loading = false
        }
      },
      // 获取用户信息
      async getUserInfo(userId?: number) {
        try {
          let { posts, roles, data, postIds, roleIds }: any = await getUserInfo(userId)
          this.postData = posts
          this.roleData = roles
          if (data) this.formData = data
          if (postIds) this.formData.postIds = postIds
          if (roleIds) this.formData.roleIds = roleIds
        } catch {}
      },
      // 新增用户
      async addUser() {
        try {
          await addUser(this.formData)
        } catch {}
      },
      // 修改用户
      async updateUser() {
        try {
          await updateUser(this.formData)
        } catch {}
      },
      // 删除用户
      async delUser() {
        try {
          await delUser(this.ids.join(','))
        } catch {}
      },
      // 更改用户状态
      async changeUserStatus(data: UserChangeStatus) {
        await changeUserStatus(data)
      },
      // 重置用户密码
      async resetUserPwd(data: UserResetPassword) {
        try {
          await resetUserPwd(data)
        } catch {}
      }
    }
  }
)

export default useUserStore 