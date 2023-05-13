<template>
  <el-card header="设备组">
    <ChartBar :option="deviceGroupOption" :height="200"></ChartBar>
  </el-card>
</template>

<script setup lang="ts">
  import { getDeviceGroupCount } from '@/api/home'
  import ChartBar from '@/components/charts/bar.vue'

  const xAxisData = ref<string[]>([])
  const deviceNormal = ref<number[]>([])
  const deviceAbnormal = ref<number[]>([])
  const deviceUnknow = ref<number[]>([])


  const deviceGroupOption = ref({
    legend: { orient: 'vertical', aligh: 'right', right: '0' },
    grid: { left: '5%', right: '15%', top: '10%', bottom: '10%' },
    xAxis: { type: 'category', data: xAxisData.value },
    yAxis: { type:'value' },
    series: [
      {
        name: '正常',
        type: 'bar',
        stack: 'total',
        label: {
          show: true,
          color: '#fff',
          formatter: (params: any) => {
            return params.value ? params.value : ''
          }
        },
        emphasis: {
          focus: 'series'
        },
        data: deviceNormal.value,
        itemStyle: {
          color: '#19be6b'
        }
      },
      {
        name: '异常',
        type: 'bar',
        stack: 'total',
        label: {
          show: true,
          color: '#fff',
          formatter: (params: any) => {
            return params.value ? params.value : ''
          }
        },
        emphasis: {
          focus: 'series'
        },
        data: deviceAbnormal.value,
        itemStyle: {
          color: '#ffa23a'
        }
      },
      {
        name: '未监控',
        type: 'bar',
        stack: 'total',
        label: {
          show: true,
          color: '#fff',
          formatter: (params: any) => {
            return params.value ? params.value : ''
          }
        },
        emphasis: {
          focus: 'series'
        },
        data: deviceUnknow.value,
        itemStyle: {
          color: '#c5c8ce'
        }
      }
    ]
  })

  // 获取设备组统计数据
  function getDeviceGroupCountData () {
    getDeviceGroupCount().then((response: any) => {
      for (let key in response.data) {
        xAxisData.value.push(key)
        deviceNormal.value.push(response.data[key].ok)
        deviceAbnormal.value.push(response.data[key].err)
        deviceUnknow.value.push(response.data[key].unknow)
      }
    })
  }

  getDeviceGroupCountData()
</script>

<style lang="scss" scoped>
  
</style>