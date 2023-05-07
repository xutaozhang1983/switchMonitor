<template>
  <v-chart class="chart" :style="{'--height': props.height + 'px'}" :option="option" autoresize></v-chart>
</template>

<script setup lang="ts">
  import { use } from 'echarts/core'
  import { CanvasRenderer } from 'echarts/renderers'
  import { LineChart } from 'echarts/charts'
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
    xAxis: [],
    yAxis: [
      {
        name: 'Mbit/s',
        type: 'value'
      }
    ],
    series: []
  })

  watchEffect(() => {
    option.value = merge(defaultOption.value, props.option)
  })

  use([
    CanvasRenderer,
    LineChart,
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
