<template>
  <el-card>
    <template #header>
      <div class="flowHeader">
        <div>设备流量TOP5</div>
        <el-radio-group v-model="deviceFlowRadio" size="small" @change="handleChange">
          <el-radio-button label="in">入口</el-radio-button>
          <el-radio-button label="out">出口</el-radio-button>
        </el-radio-group>
      </div>
    </template>
    <el-row :gutter="16">
      <el-col :span="24">
        <ChartBar :option="deviceFlowOption" :height="250"></ChartBar>
      </el-col>
    </el-row>
  </el-card>
</template>

<script setup lang="ts">
  import * as echarts from 'echarts'
  import ChartBar from '@/components/charts/bar.vue'
  import { getFlowCount } from '@/api/home'

  const deviceFlowRadio = ref('in')
  const inData: any = ref({
    yAxisData: [],
    seriesData: []
  })
  const outData: any = ref({
    yAxisData: [],
    seriesData: []
  })

  const deviceFlowOption = ref({
    grid: {
      left: '15%',
      top: '15%',
      bottom: '10%'
    },
    label: {
      show: true,
      position: 'right'
    },
    xAxis: {
      type: 'value',
      min: 0,
      boundaryGap : [ 0.2, 0.2 ]
    },
    yAxis: {
      name: '单位: Kbps',
      axisTick: {
        show: false
      },
      axisLine: {
        show: false
      },
      type: 'category',
      data: []
    },
    series: {
      type: 'bar',
      data: [],
      itemStyle: {}
    }
  })

  // 切换入口、出口流量开关
  function handleChange (val: string) {
    if (val === 'in') {
      deviceFlowOption.value.yAxis.data = inData.value.yAxisData
      deviceFlowOption.value.series.data = inData.value.seriesData
      deviceFlowOption.value.series.itemStyle = {
        color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [
          { offset: 0, color: '#0099e6' },
          { offset: 1, color: '#33cdfb' }
        ])
      }
    } else {
      deviceFlowOption.value.yAxis.data = outData.value.yAxisData
      deviceFlowOption.value.series.data = outData.value.seriesData
      deviceFlowOption.value.series.itemStyle = {
        color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [
          { offset: 0, color: '#4ccc66' },
          { offset: 1, color: '#bcf0d2' }
        ])
      }
    }
  }

  // 获取流量统计数据
  function getFlowCountData () {
    let sendData = {
      hours: 4,
      topType: 2
    }
    getFlowCount(sendData).then((response: any) => {
      for (let item of response.data.ifIn) {
        inData.value.yAxisData.push(item.deviceName)
        inData.value.seriesData.push((item.value/8/1024).toFixed(2))
      }
      for (let item of response.data.ifOut) {
        outData.value.yAxisData.push(item.deviceName)
        outData.value.seriesData.push((item.value/8/1024).toFixed(2))
      }
      handleChange('in')
    })
  }

  getFlowCountData()
</script>

<style lang="scss" scoped>
  .flowHeader {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
</style>