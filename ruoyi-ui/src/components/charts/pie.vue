<template>
  <div :style="{ '--height': props.height + 'px' }">
    <v-chart ref="chartRef" class="chart"  :option="option" autoresize></v-chart>
  </div>
</template>

<script setup lang="ts">
  import { use } from 'echarts/core'
  import { CanvasRenderer } from 'echarts/renderers'
  import { PieChart } from 'echarts/charts'
  import VChart from 'vue-echarts'
  import {
    TitleComponent,
    TooltipComponent,
    GridComponent,
    LegendComponent
  } from 'echarts/components'
  import merge from 'deepmerge'

  interface Props {
    height: number
    option?: any
  }

  const props = withDefaults(defineProps<Props>(), {
    height: 300,
    option: () => {}
  })

  const chartRef = ref<any>(null)

  const option = ref({})
  const defaultOption = ref({
    color: ['#f5bc31', '#60d7a7', '#1e84ff', '#c879f9', '#64beff'],
    tooltip: {
      trigger: 'item'
    },
    legend: {},
    grid: {}
  })

  use([
    CanvasRenderer,
    PieChart,
    GridComponent,
    TitleComponent,
    TooltipComponent,
    LegendComponent
  ])

  watchEffect(() => {
    option.value = merge(defaultOption.value, props.option)
    if (chartRef.value) {
      chartRef.value.setOption(option.value, true)
    }
  })

</script>

<style lang="scss" scoped>
  .chart {
    height: var(--height);
  }
</style>
