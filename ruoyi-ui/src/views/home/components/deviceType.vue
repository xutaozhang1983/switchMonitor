<template>
  <el-card header="设备类型分布">
    <ChartPie :option="deviceTypeOption" :height="200"></ChartPie>
  </el-card>
</template>

<script setup lang="ts">
  import ChartPie from '@/components/charts/pie.vue'

  import useHomeStore from '../store'

  const homeStore = useHomeStore()

  const seriesData = computed(() => homeStore.deviceTypeData)
  const total = computed(() => homeStore.deviceData.total)

  const deviceTypeOption = ref({
    legend: {
      orient: 'vertical',
      icon: 'circle',
      aligh: 'right',
      right: '20',
      top: '20',
      formatter: (name: string) => {
        if (name === '网络设备') {
          return `${name} {v|100%}`
        } else {
          return `${name} {v|0%}`
        }
      },
      textStyle: {
        rich: {
          v: {
            color: '#999999'
          }
        }
      }
    },
    series: {
      name: '设备类型分布',
      type: 'pie',
      radius: ['60%', '90%'],
      center: ['30%', '50%'],
      avoidLabelOverlap: false,
      itemStyle: {
        borderRadius: 5,
        borderColor: '#fff',
        borderWidth: 2
      },
      label: {
        show: true,
        position: 'center',
        formatter: () => {
          return `{l|总数}\n{v|${total.value}}`
        },
        rich: {
          l: {
            color: '#999999',
            fontSize: 16
          },
          v: {
            color: '#555555',
            fontWeight: 'bold',
            fontSize: 40
          }
        }
      },
      labelLine: {
        show: false
      },
      data: seriesData
    }
  })
</script>

<style lang="scss" scoped>
  
</style>