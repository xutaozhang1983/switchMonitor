import { GenQueryParam } from '@/types/api/tool/gen'
import { listTable, previewTable, delTable, genCode, synchDb } from "@/api/tool/gen"

const useGenStore = defineStore('useGenStore',
  {
    state: () => ({
      loading: false, // 代码数据loading状态
      queryParams: { pageNum: 1, pageSize: 10 } as GenQueryParam, // 查询参数
      genData: [] as any[], // 代码数据
      preview: {
        open: false,
        title: '代码预览',
        data: {},
        activeName: 'domain.java'
      },
      showSearch: true, // 是否显示搜索区域
      showImportDialog: false,  // 显示表导入对话框
      showDetailDialog: false, // 显示代码详情对话框
      showCronDialog: false, // 显示cron表达式生成器对话框
      total: 0, // 用户数据总条目数
      single: true, // 选择1条数据
      multiple: true, // 选择多条数据
      ids: [] as number[], // 选择数据的id集合
      tableNames: [] as string[], // 选择数据的name集合
      formTitle: '', // 代码新增、修改对话框标题
      refreshTable: true, // 刷新表格数据
      isExpandAll: true // 表格数据是否全部展开
    }),
    actions: {
      // 查询代码数据
      async getGenData() {
        this.loading = true
        try {
          let { rows, total }: any = await listTable(this.queryParams)
          this.genData = rows
          this.total = total
        } catch (error: any) {
        } finally {
          this.loading = false
        }
      },
      // 生成代码
      async genCode() {
        try {
          await genCode(this.tableNames.join(','))
        } catch {}
      },
      // 预览代码
      async previewTable(tableId: number) {
        try {
          let { data } = await previewTable(tableId)
          this.preview.data = data
          this.preview.open = true
          this.preview.activeName = 'domain.java'
          console.log(this.preview)
        } catch {}
      },
      // 删除代码
      async delGen() {
        try {
          await delTable(this.ids.join(','))
        } catch {}
      },
      // 同步数据库
      async synchDb(tableName: string) {
        try {
          await synchDb(tableName)
        } catch {}
      }
    }
  }
)

export default useGenStore 