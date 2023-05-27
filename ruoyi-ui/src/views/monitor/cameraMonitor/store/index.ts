import { CameraQueryParam } from '@/types/api/monitor/camera'
import { listEncode, listCamera } from '@/api/monitor/camera'

const useCameraMonitorStore = defineStore('useCameraMonitorStore',
  {
    state: () => ({
      loading: false, // 摄像头数据loading状态
      queryParams: { pageNum: 1, pageSize: 10, deviceType: '1' } as CameraQueryParam, // 查询参数
      showSearch: true, // 是否显示搜索区域
      total: 0, // 摄像头数据总条目数
      cameraData: [] as any[], // 摄像头数据
      encodeData: [] as any[], // 编码设备数据
      resetEncodeKey: false // 是否重置编码设备树的key
    }),
    actions: {
      // 查询编码设备数据
      async getEncodeData() {
        try {
          let { rows }: any = await listEncode({ pageNum: 1, pageSize: 9999 })
          this.encodeData = [{ id: 0, label: '全部', children: [] }]
          if (rows.length) {
            for (let item of rows) {
              this.encodeData[0].children.push(
                { id: item.id, label: item.name }
              )
            }
          }
        } catch {}
      },
      // 查询摄像头数据
      async getCameraData() {
        this.loading = true
        try {
          let { rows, total }: any = await listCamera(this.queryParams)
          this.cameraData = rows
          this.total = total
        } catch (error: any) {
        } finally {
          this.loading = false
        }
      }
    }
  }
)

export default useCameraMonitorStore 