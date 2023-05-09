<template>
  <el-row :gutter="16">
    <el-col :span="12">
      <el-card class="card" header="端口视图">
        <Switch :data="switchData" :abnormal="5"></Switch>
      </el-card>
    </el-col>
    <el-col :span="12">
      <el-card header="流量总图" class="card">
        <ChartLine :height="300" :option="option" autoresize></ChartLine>
      </el-card>
    </el-col>
  </el-row>
  <el-card style="margin-top: 10px" header="端口">
    <el-table v-loading="monitorStore.loading" :data="monitorStore.monitorData">
      <el-table-column label="名称" prop="name" width="50"/>
      <el-table-column label="状态" align="center">
        <template #default="scope">
          <div class="portStatus" v-if="scope.row.status === '0'">
            <el-icon class="mr5" color="#79c155" size="20px"><CircleCheckFilled /></el-icon>
            <span>正常</span>
          </div>
          <div class="portStatus" v-else>
            <el-icon class="mr5" color="#f67f7f"  size="20px"><WarningFilled /></el-icon>
            <span>异常</span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="入口(Mbit/s)" prop="in" />
      <el-table-column label="出口(Mbit/s)" prop="out" />
      <el-table-column label="连接的MAC" prop="mac" />
      <el-table-column label="连接的IP" prop="ip" />
    </el-table>
  </el-card>
  <!--数据分页-->
  <pagination
    v-show="monitorStore.total > 0"
    :total="monitorStore.total"
    v-model:page="monitorStore.queryParams.pageNum"
    v-model:limit="monitorStore.queryParams.pageSize"
    @pagination="monitorStore.getMonitorData"
  />
</template>

<script setup lang="ts">
  import { ElMessageBox, ElMessage } from 'element-plus'
  import * as echarts from 'echarts'
  import ChartLine from '@/components/charts/line.vue'
  import Switch from '@/components/switch/index.vue'
  import useMonitorStore from '../store'
  import dayjs from 'dayjs'

  const monitorStore = useMonitorStore()

  const switchData = ref({
    total: 18,
    ports: [
      { name: 'P1', status: 0 },
      { name: 'P2', status: 1 },
      { name: 'P3', status: 1 },
      { name: 'P4', status: 0 },
      { name: 'P5', status: 0 },
      { name: 'P6', status: 0 },
      { name: 'P7', status: 0 },
      { name: 'P8', status: 0 },
      { name: 'P9', status: 0 },
      { name: 'P10', status: 0 },
      { name: 'P11', status: 0 },
      { name: 'P12', status: 0 },
      { name: 'P13', status: 1 },
      { name: 'P14', status: 1 },
      { name: 'P15', status: 0 },
      { name: 'P16', status: 1 },
      { name: 'P17', status: 1 },
      { name: 'P18', status: 0 }
    ]
  })

  const axisData = computed(() => {
    let result = []
    for (let i = 0; i < 24; i++) {
      result.push(dayjs(new Date).subtract(i*5, 'm').format('mm:ss'))
    }
    return result
  })

  const seriesData1 = computed(() => {
    let result = []
    for (let i = 0; i < 24; i++) {
      result.push(Math.random() * (100 - 1) + 1)
    }
    return result
  })

  const seriesData2 = computed(() => {
    let result = []
    for (let i = 0; i < 24; i++) {
      result.push(Math.random() * (100 - 1) + 1)
    }
    return result
  })
  
  const option = ref({
    legend: {
      data: ['入口', '出口']
    },
    xAxis: [
      {
        type: 'category',
        boundaryGap: false,
        data: axisData
      }
    ],
    series: [
      {
        name: "入口",
        type: 'line',
        stack: 'Total',
        data: seriesData1,
        itemStyle: {
          color: '#f2a923'
        }
      },
      {
        name: '出口',
        type: 'line',
        stack: 'Total',
        data: seriesData2,
        itemStyle: {
          color: '#50cf8d'
        }
      }
    ]
  })
</script>

<style lang="scss" scoped>

  .card {
    height: 350px;
  }
  .portStatus {
    display: flex;
    justify-content: center;
    align-items: center;
    cursor: pointer;
  }
  .chart {
    height: 300px;
  }
</style>