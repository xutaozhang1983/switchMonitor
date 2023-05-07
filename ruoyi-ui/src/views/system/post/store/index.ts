import { PostQueryParam, PostFormData } from '@/types/api/system/post'
import { listPost, getPost, addPost, updatePost, delPost } from "@/api/system/post"

const usePostStore = defineStore('usePostStore',
  {
    state: () => ({
      loading: false, // 岗位数据loading状态
      queryParams: { pageNum: 1, pageSize: 10 } as PostQueryParam, // 查询参数
      formData: {} as PostFormData, // 岗位新增、修改提交参数
      postData: [] as any[], // 岗位数据
      showSearch: true, // 是否显示搜索区域
      showFormDialog: false,  // 显示岗位新增、修改对话框
      total: 0, // 用户数据总条目数
      single: true, // 选择1条数据
      multiple: true, // 选择多条数据
      ids: [] as number[], // 选择数据的id集合
      formTitle: '', // 岗位新增、修改对话框标题
      refreshTable: true, // 刷新表格数据
      isExpandAll: true // 表格数据是否全部展开
    }),
    actions: {
      /** 重置操作表单 */
      resetFormData() {
        this.formData = {
          postId: undefined,
          postCode: undefined,
          postName: undefined,
          postSort: 0,
          status: '0',
          remark: undefined
        }
      },
      // 查询岗位数据
      async getPostData() {
        this.loading = true
        try {
          let { rows, total }: any = await listPost(this.queryParams)
          this.postData = rows
          this.total = total
        } catch (error: any) {
        } finally {
          this.loading = false
        }
      },
      // 获取岗位信息
      async getPostInfo(PostId: number) {
        try {
          let { data }: any = await getPost(PostId)
          this.formData = data
        } catch {}
      },
      // 新增岗位
      async addPost() {
        try {
          await addPost(this.formData)
        } catch {}
      },
      // 修改岗位
      async updatePost() {
        try {
          await updatePost(this.formData)
        } catch {}
      },
      // 删除岗位
      async delPost() {
        try {
          await delPost(this.ids.join(','))
        } catch {}
      }
    }
  }
)

export default usePostStore 