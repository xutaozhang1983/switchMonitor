<template>
  <v-chart class="chart" :style="{'--height': props.height + 'px'}" :option="option" autoresize></v-chart>
</template>

<script setup lang="ts">
  import { use } from 'echarts/core'
  import { CanvasRenderer } from 'echarts/renderers'
  import { BarChart } from 'echarts/charts'
  import VChart from 'vue-echarts'
  import { TitleComponent, TooltipComponent, GridComponent, LegendComponent } from 'echarts/components'
  import merge from 'deepmerge'

  interface Props {
    height: number,
    option?: any
  }

  const props = withDefaults(defineProps<Props>(), {
    height: 300,
    option: () => {}
  })

  const option = ref({})
  const defaultOption = ref({
    tooltip: {
      trigger: 'item'
    },
    legend: {},
    grid: {
      left: '15%',
      top: '10%',
      bottom: '10%'
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
      type: 'category'
    },
    series: {
      type: 'bar',
      showBackground: true
    }
  })

  watchEffect(() => {
    option.value = merge(defaultOption.value, props.option)
  })

  use([
    CanvasRenderer,
    BarChart,
    GridComponent,
    TitleComponent,
    TooltipComponent,
    LegendComponent
  ])
</script>

<style lang="scss" scoped>
  .chart {
    height: var(--height)
  }
</style>
