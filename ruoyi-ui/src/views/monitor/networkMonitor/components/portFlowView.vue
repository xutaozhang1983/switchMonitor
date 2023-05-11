<template>
  <el-dialog title="流量图" v-model="props.showDialog" width="1000px" append-to-body :show-close="false">
    <el-form :model="queryParams" ref="queryRef" :inline="true">
      <el-form-item label="时间范围" prop="deviceName">
        <el-date-picker
          v-model="timeRange"
          type="datetimerange"
          range-separator="至"
          start-placeholder="开始时间"
          end-placeholder="结束时间"
          format="YYYY-MM-DD HH:mm"
          value-format="YYYY-MM-DD HH:mm"
          @change="handleQuery"/>
      </el-form-item>
    </el-form>
    <el-divider style="margin-top: 5px"/>
    <ChartLine :height="'200px'" :option="flowOption" autoresize></ChartLine>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="close">关闭</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
  import ChartLine from '@/components/charts/line.vue'
  import { getItemHisByItemId } from '@/api/deviceItemHis'
  import dayjs from 'dayjs'
  
  interface Props {
    showDialog: boolean,
    deviceId: number,
    portId: number
  }

  const props = defineProps<Props>()
  const emits = defineEmits(['update:showDialog'])

  const timeRange = ref([
    dayjs().subtract(5, 'day').format('YYYY-MM-DD HH') + ':00',
    dayjs().format('YYYY-MM-DD HH') + ':00',
  ]) // 时间范围

  const queryParams = ref({ // 查询参数
    deviceId: props.deviceId,
    counters: ['ifIn', 'ifOut'],
    startClock: timeRange.value[0] + ':00',
    endClock: timeRange.value[1] + ':00',
    step: 300,
    itemId: props.portId
  })
  const flowData = ref<any>({ // 流量数据
    in: [] as any,
    out: [] as any
  })

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
  
  // 查询
  function handleQuery() {
    console.log(timeRange)
    queryParams.value.startClock = timeRange.value[0] + ':00'
    queryParams.value.endClock = timeRange.value[1] + ':00'
    getItemHisByItemId(queryParams.value).then((response: any) => {
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
      xAxisData.push(dayjs(item.clock).format('HH:mm'))
      inSeriesData.push((item.value/8/1024).toFixed(2))
    }
    for (let item of flowData.value.out) {
      outSeriesData.push((item.value/8/1024).toFixed(2))
    }
    flowOption.value.xAxis.data = xAxisData
    flowOption.value.series[0].data = inSeriesData
    flowOption.value.series[1].data = outSeriesData
  }

  // 关闭按钮
  function close() {
    emits('update:showDialog', false)
  }

  handleQuery()
</script>

<style lang="scss" scoped>

</style>