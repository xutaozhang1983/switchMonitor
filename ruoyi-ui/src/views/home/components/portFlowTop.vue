<template>
  <el-card>
    <template #header>
      <div class="flowHeader">
        <div>端口流量TOP5</div>
        <el-radio-group v-model="portFlowRadio" size="small" @change="handleChange">
          <el-radio-button label="in">入口</el-radio-button>
          <el-radio-button label="out">出口</el-radio-button>
        </el-radio-group>
      </div>
    </template>
    <el-row :gutter="16">
      <el-col :span="24">
        <ChartBar :option="portFlowOption" :height="250"></ChartBar>
      </el-col>
    </el-row>
  </el-card>
</template>

<script setup lang="ts">
  import * as echarts from 'echarts'
  import ChartBar from '@/components/charts/bar.vue'

  import useHomeStore from '../store'

  const homeStore = useHomeStore()

  const portFlowRadio = ref('in')
  
  const inData: any = computed(() => homeStore.portFlowData.inData)
  const outData: any = computed(() => homeStore.portFlowData.outData)

  const portFlowOption = ref({
    grid: {
      left: '10%',
      right: '12%',
      top: '0',
      bottom: '10%'
    },
    label: {
      show: true,
      position: 'right',
      formatter: (params: any) => {
        return params.value + '（Kbps）'
      }
    },
    xAxis: {
      type: 'value',
      min: 0,
      splitLine: {
        lineStyle: {
          type: 'dashed'
        }
      }
    },
    yAxis: {
      axisTick: {
        show: false
      },
      axisLine: {
        show: false
      },
      axisLabel: {
        formatter: (val: string) => {
          return val.replace(/^(.)[a-zA-Z]+(.+)$/, "$1$2")
        }
      },
      type: 'category',
      data: []
    },
    series: {
      type: 'bar',
      data: [],
      showBackground: true,
      backgroundStyle: {
        color: 'rgba(180, 180, 180, 0.2)'
      },
      itemStyle: {}
    }
  })

  // 切换入口、出口流量开关
  function handleChange (val: string) {
    if (val === 'in') {
      portFlowOption.value.yAxis.data = inData.value.yAxisData
      portFlowOption.value.series.data = inData.value.seriesData
      portFlowOption.value.series.itemStyle = {
        color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [
          { offset: 0, color: '#0099e6' },
          { offset: 1, color: '#33cdfb' }
        ])
      }
    } else {
      portFlowOption.value.yAxis.data = outData.value.yAxisData
      portFlowOption.value.series.data = outData.value.seriesData
      portFlowOption.value.series.itemStyle = {
        color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [
          { offset: 0, color: '#4ccc66' },
          { offset: 1, color: '#bcf0d2' }
        ])
      }
    }
  }

  handleChange('in')

</script>

<style lang="scss" scoped>
  .flowHeader {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
</style>