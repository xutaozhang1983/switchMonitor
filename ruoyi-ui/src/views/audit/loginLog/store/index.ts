import { LoginLogQueryParam } from '@/types/api/audit/loginLog'
import { listLoginLog, delLoginLog, cleanLoginLog, unlockAccount } from "@/api/audit/loginLog"

const useLoginLogStore = defineStore('useLoginLogStore',
  {
    state: () => ({
      loading: false, // 登录日志数据loading状态
      queryParams: { pageNum: 1, pageSize: 10 } as LoginLogQueryParam, // 查询参数
      showSearch: true, // 是否显示搜索区域
      total: 0, // 登录日志数据总条目数
      loginLogData: [] as any[], // 登录日志数据
      single: true, // 选择1条数据
      multiple: true, // 选择多条数据
      ids: [] as number[], // 选择数据的id集合
      selectName: [] as string[], // 选择数据的userName集合
    }),
    actions: {
      // 查询登录日志数据
      async getLoginLogData() {
        this.loading = true
        try {
          let { rows, total }: any = await listLoginLog(this.queryParams)
          this.loginLogData = rows
          this.total = total
        } catch (error: any) {
        } finally {
          this.loading = false
        }
      },
      // 删除登录日志
      async delLoginLog() {
        try {
          await delLoginLog(this.ids.join(','))
        } catch {}
      },
      // 清空登录日志
      async cleanLoginLog() {
        try {
          await cleanLoginLog()
        } catch {}
      },
      // 解锁用户
      async unlockAccount() {
        try {
          await unlockAccount(this.selectName.join(','))
        } catch {}
      }
    }
  }
)

export default useLoginLogStore 