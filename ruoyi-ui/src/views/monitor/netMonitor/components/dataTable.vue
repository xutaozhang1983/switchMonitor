<template>
  <el-table v-loading="deviceStore.loading" :data="deviceStore.deviceData">
    <el-table-column label="设备名称" prop="deviceName"/>
    <el-table-column label="所在组" prop="groupName"/>
    <el-table-column label="设备状态" align="center">
      <template #default="scope">
        <div class="deviceStatus" v-if="scope.row.status === '0'">
          <el-icon class="mr5" color="#79c155" size="20px"><CircleCheckFilled /></el-icon>
          <span>正常</span>
        </div>
        <div class="deviceStatus" v-else>
          <el-icon class="mr5" color="#f67f7f"  size="20px"><WarningFilled /></el-icon>
          <span>异常</span>
        </div>
      </template>
    </el-table-column>
    <el-table-column label="设备型号" prop="deviceModel"/>
    <el-table-column label="IP地址" prop="deviceIp" />
    <el-table-column label="接口数量" prop="portNum" />
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
    @pagination="deviceStore.getDeviceData"
  />
</template>

<script setup lang="ts">
  import { ElTooltip } from 'element-plus'
  import useNetMonitorStore from '../store'

  const deviceStore = useNetMonitorStore()
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