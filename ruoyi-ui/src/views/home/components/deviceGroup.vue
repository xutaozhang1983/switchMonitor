<template>
  <el-card>
    <template #header>
      <div class="flowHeader">
        <div class="left">
          <div class="tag"></div>
          <div>设备组</div>
        </div>
      </div>
    </template>
    <ChartBar :option="deviceGroupOption" :height="200"></ChartBar>
  </el-card>
</template>

<script setup lang="ts">
  import ChartBar from '@/components/charts/bar.vue'

  import useHomeStore from '../store'

  const homeStore = useHomeStore()

  const xAxisData = computed(() => homeStore.deviceGroupData.xAxisData)
  const deviceNormal = computed(() => homeStore.deviceGroupData.deviceNormal)
  const deviceAbnormal = computed(() => homeStore.deviceGroupData.deviceAbnormal)

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
          color: '#60d7a7'
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
          color: '#f99f6b'
        }
      }
    ]
  })

</script>

<style lang="scss" scoped>
  .flowHeader {
    display: flex;
    justify-content: space-between;
    align-items: center;
    .left {
      display: flex;
      align-items: center;
      .tag {
        height: 20px;
        width: 10px;
        border-radius: 0px 10px 10px 0px;
        background-color: #64beff;
        margin-right: 10px;
      }
    }
  }
</style>