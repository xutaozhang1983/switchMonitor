<template>
  <div class="app-container">
    <el-row>
      <el-col :span="24">
        <el-card class="card" header="端口视图">
          <Switch :data="portViewData"></Switch>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="10" class="mt10">
      <el-col :span="16">
        <el-card header="流量总图">
          <ChartLine :height="'200px'" :option="flowOption" autoresize></ChartLine>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card header="设备负载">
          <el-row :gutter="10">
            <el-col :span="12">
              <Liquidfill :option="cpuOption" :height="'200px'"></Liquidfill>
            </el-col>
            <el-col :span="12">
              <Liquidfill :option="memoryOption" :height="'200px'"></Liquidfill>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
    </el-row>
    <el-card style="margin-top: 10px" header="端口">
      <el-table v-loading="loading" :data="portData">
        <el-table-column label="名称" prop="itemName"/>
        <el-table-column label="状态" align="left">
          <template #default="scope">
            <div class="portStatus" v-if="scope.row.status === '1'">
              <el-icon class="mr5" color="#79c155" size="20px"><CircleCheckFilled /></el-icon>
              <span>up</span>
            </div>
            <div class="portStatus" v-else>
              <el-icon class="mr5" color="#909399"  size="20px"><WarningFilled /></el-icon>
              <span>down</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="入口(Kbps)">
          <template #default="scope">
            <span>{{ (scope.row.value.split(',')[0]/8/1024).toFixed(2) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="出口(Kbps)">
          <template #default="scope">
            <span>{{ (scope.row.value.split(',')[1]/8/1024).toFixed(2) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="数据更新时间" align="center">
          <template #default="scope">
            <div>{{ dayjs.unix(scope.row.clock).format('YYYY-MM-DD HH:mm:ss') }}</div>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup lang="ts">
  import { ElMessageBox, ElMessage } from 'element-plus'
  import { listDeviceItem } from '@/api/deviceItem'
  import { listDeviceItemHis } from '@/api/deviceItemHis'
  import Liquidfill from '@/components/charts/liquidfill.vue'
  import ChartLine from '@/components/charts/line.vue'
  import Switch from '@/components/switch/index.vue'
  import dayjs from 'dayjs'

  const route = useRoute()

  const deviceId = ref(Number(route.query.id))
  const loading = ref(false)
  const portData = ref<any[]>([]) // 端口数据
  const loadData = ref<any>({ cpu: 0, memory: 0 }) // 负载数据
  const flowData = ref<any>({
    in: [] as any,
    out: [] as any
  })

  // 端口视图数据
  const portViewData = computed(() => {
    let result:any = []
    if (portData.value.length) {
      for (let i = 0, j = 0; j < portData.value.length; i+=1, j+=4) {
        result[i] = []
        if (portData.value[j]) result[i].push(portData.value[j])
        if (portData.value[j+1]) result[i].push(portData.value[j+1])
        if (portData.value[j+2]) result[i].push(portData.value[j+2])
        if (portData.value[j+3]) result[i].push(portData.value[j+3])
      }
    }
    return result
  })

  // cpu图表option
  const cpuOption = ref({
    title: { text: 'CPU利用率' },
    series: { data: [{ value: loadData.value.cpu }], color: ['#00e298'] }
  })

  // 内存图表option
  const memoryOption = ref({
    title: { text: '内存利用率' },
    series: { data: [{ value: loadData.value.memory }], color: ['#E6A23C'] }
  })
  
  // 流量图表option
  const flowOption = ref({
    legend: {
      data: ['入口', '出口']
    },
    xAxis: {
      type: 'category',
      boundaryGap: false,
      data: []
    },
    grid: {
      top: '15%',
      bottom: '15%'
    },
    series: [
      {
        name: "入口",
        type: 'line',
        stack: 'Total',
        data: [],
        itemStyle: {
          color: '#409EFF'
        }
      },
      {
        name: '出口',
        type: 'line',
        stack: 'Total',
        data: [],
        itemStyle: {
          color: '#67C23A'
        }
      }
    ]
  })

  // 获取监控数据
  function getMonitorData() {
    let sendData = {
      deviceId: deviceId.value
    }
    loading.value = true
    listDeviceItem(sendData).then((response: any) => {
      loading.value = false
      portData.value = response.rows.filter((item: any) => item.isPort === 0)
      loadData.value = {
        cpu: (response.rows.filter((item: any) => item.itemName == "CPU")[0] as any)?.value || 0,
        memory: (response.rows.filter((item: any) => item.itemName == "Memory")[0] as any)?.value || 0
      }
    })
  }

  // 获取流量图表数据
  function getFlowChartData() {
    let sendData = {
      deviceId: deviceId.value,
      counters: [ 'ifIn', 'ifOut' ]
    }
    listDeviceItemHis(sendData).then((response: any) => {
      flowData.value.in = (response.data.filter((item: any) => item.counter === 'ifIn')[0] as any)?.values || []
      flowData.value.out = (response.data.filter((item: any) => item.counter === 'ifOut')[0] as any)?.values || []
      setFlowOptionData()
    })
  }

  // 设置流量图表数据
  function setFlowOptionData() {
    let xAxisData: any = []
    let inSeriesData: any = []
    let outSeriesData: any = []
    for (let item of flowData.value.in) {
      xAxisData.push(item.clock)
      inSeriesData.push((item.value/8/1024).toFixed(2))
    }
    for (let item of flowData.value.out) {
      outSeriesData.push((item.value/8/1024).toFixed(2))
    }
    flowOption.value.xAxis.data = xAxisData
    flowOption.value.series[0].data = inSeriesData
    flowOption.value.series[1].data = outSeriesData
  }

  getMonitorData()
  getFlowChartData()
</script>

<style lang="scss" scoped>

  .card {
    height: 250px;
  }
  .portStatus {
    display: flex;
    align-items: center;
    cursor: pointer;
  }
</style>
