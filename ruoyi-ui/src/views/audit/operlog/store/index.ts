import { OperLogQueryParam } from '@/types/api/audit/operLog'
import { listOperLog, delOperLog, cleanOperLog } from "@/api/audit/operLog"

const useOperLogStore = defineStore('useOperLogStore',
  {
    state: () => ({
      loading: false, // 操作日志数据loading状态
      queryParams: { pageNum: 1, pageSize: 10 } as OperLogQueryParam, // 查询参数
      showSearch: true, // 是否显示搜索区域
      total: 0, // 操作日志数据总条目数
      operLogData: [] as any[], // 操作日志数据
      showDetailDialog: false, // 显示日志详情对话框
      detailForm: {} as any, // 日志详情表单
      single: true, // 选择1条数据
      multiple: true, // 选择多条数据
      ids: [] as number[] // 选择数据的id集合
    }),
    actions: {
      // 查询操作日志数据
      async getOperLogData() {
        this.loading = true
        try {
          let { rows, total }: any = await listOperLog(this.queryParams)
          this.operLogData = rows
          this.total = total
        } catch (error: any) {
        } finally {
          this.loading = false
        }
      },
      // 删除操作日志
      async delOperLog() {
        try {
          await delOperLog(this.ids.join(','))
        } catch {}
      },
      // 清空操作日志
      async cleanOperLog() {
        try {
          await cleanOperLog()
        } catch {}
      }
    }
  }
)

export default useOperLogStore 