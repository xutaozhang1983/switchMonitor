import { MenuQueryParam, MenuFormData } from '@/types/api/system/menu'
import { listMenu, getMenu, addMenu, updateMenu, delMenu } from "@/api/system/menu"
import { handleTree } from '@/utils/common'

const useMenuStore = defineStore('useMenuStore',
  {
    state: () => ({
      loading: false, // 菜单数据loading状态
      queryParams: { } as MenuQueryParam, // 查询参数
      formData: {} as MenuFormData, // 菜单新增、修改提交参数
      menuData: [] as any, // 菜单数据
      showSearch: true, // 是否显示搜索区域
      showFormDialog: false,  // 显示菜单新增、修改对话框
      formTitle: '', // 菜单新增、修改对话框标题
      refreshTable: true, // 刷新表格数据
      isExpandAll: false // 表格数据是否全部展开
    }),
    getters: {
      // 获取菜单树
      getMenuTree ({ menuData }) {
        const menu = { menuId: 0, menuName: '主类目', children: [] }
        menu.children = menuData
        return [ menu ]
      }
    },
    actions: {
      /** 重置操作表单 */
      resetFormData() {
        this.formData = {
          menuId: undefined,
          parentId: 0,
          menuName: undefined,
          icon: undefined,
          menuType: 'M',
          orderNum: undefined,
          isFrame: '1',
          isCache: '0',
          visible: '0',
          status: '0'
        }
      },
      // 查询菜单数据
      async getMenuData() {
        this.loading = true
        try {
          let { data }: any = await listMenu(this.queryParams)
          this.menuData = handleTree(data, 'menuId')
        } catch (error: any) {
        } finally {
          this.loading = false
        }
      },
      // 获取菜单信息
      async getMenuInfo(menuId: number) {
        try {
          let { data }: any = await getMenu(menuId)
          this.formData = data
        } catch {}
      },
      // 新增菜单
      async addMenu() {
        try {
          await addMenu(this.formData)
        } catch {}
      },
      // 修改菜单
      async updateMenu() {
        try {
          await updateMenu(this.formData)
        } catch {}
      },
      // 删除菜单
      async delMenu(menuId: number) {
        try {
          await delMenu(menuId)
        } catch {}
      }
    }
  }
)

export default useMenuStore 