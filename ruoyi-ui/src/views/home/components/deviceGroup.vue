<template>
  <el-card header="设备组">
    <ChartBar :option="deviceGroupOption" :height="200"></ChartBar>
  </el-card>
</template>

<script setup lang="ts">
  import ChartBar from '@/components/charts/bar.vue'

  import useHomeStore from '../store'

  const homeStore = useHomeStore()

  const deviceGroupOption = ref({
    legend: { orient: 'vertical', aligh: 'right', right: '0' },
    grid: { left: '5%', right: '15%', top: '10%', bottom: '10%' },
    xAxis: { type: 'category', data: homeStore.deviceGroupData.xAxisData },
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
        data: homeStore.deviceGroupData.deviceNormal,
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
        data: homeStore.deviceGroupData.deviceAbnormal,
        itemStyle: {
          color: '#ffa23a'
        }
      },
      {
        name: '已监控',
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
        data: homeStore.deviceGroupData.deviceMonitor,
        itemStyle: {
          color: '#52aafe'
        }
      }
    ]
  })

</script>

<style lang="scss" scoped>
  
</style>