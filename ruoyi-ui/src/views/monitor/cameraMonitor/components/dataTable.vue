<template>
  <el-table v-loading="deviceStore.loading" :data="deviceStore.cameraData">
    <el-table-column label="设备名称" prop="cameraName"/>
    <el-table-column label="所属编码设备" prop="groupName"/>
    <el-table-column label="设备状态" align="center">
      <template #default="scope">
        <div class="deviceStatus" v-if="scope.row.status === '0'">
          <el-icon class="mr5" color="#f67f7f" size="20px"><CircleCheckFilled /></el-icon>
          <span>不在线</span>
        </div>
        <div class="deviceStatus" v-else>
          <el-icon class="mr5" color="#79c155"  size="20px"><WarningFilled /></el-icon>
          <span>在线</span>
        </div>
      </template>
    </el-table-column>
    <el-table-column label="设备类型" prop="cameraType"/>
    <el-table-column label="安装位置" prop="installLocation" />
    <el-table-column label="传输协议" prop="transType" />
    <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
      <template #default="scope">
        <el-tooltip content="查看" placement="top">
          <el-button type="text" icon="View" @click.stop="handleView(scope.row)" v-hasPermi="['system:device:view']"></el-button>
        </el-tooltip>
      </template>
    </el-table-column>
  </el-table>
  <!--数据分页-->
  <pagination
    v-show="deviceStore.total > 0"
    :total="deviceStore.total"
    v-model:page="deviceStore.queryParams.pageNum"
    v-model:limit="deviceStore.queryParams.pageSize"
    @pagination="deviceStore.getCameraData"
  />
</template>

<script setup lang="ts">
  import { ElTooltip } from 'element-plus'
  import useCameraMonitorStore from '../store'

  const deviceStore = useCameraMonitorStore()
  const router = useRouter()

  // 查看按钮操作
  async function handleView(row: any) {
    router.push('/monitor/netMonitor/view/' + row.id)
  }
</script>

<style lang="scss" scoped>
  .deviceStatus {
    display: flex;
    justify-content: center;
    align-items: center;
  }
</style>