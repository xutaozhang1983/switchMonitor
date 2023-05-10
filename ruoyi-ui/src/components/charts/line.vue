<template>
  <v-chart class="chart" :option="option" autoresize></v-chart>
</template>

<script setup lang="ts">
  import { use } from 'echarts/core'
  import { CanvasRenderer } from 'echarts/renderers'
  import { LineChart } from 'echarts/charts'
  import VChart from 'vue-echarts'
  import { TitleComponent, TooltipComponent, GridComponent, LegendComponent } from 'echarts/components'
  import merge from 'deepmerge'

  interface Props {
    height: string,
    option?: any
  }

  const props = withDefaults(defineProps<Props>(), {
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
        name: 'Kbps',
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
    height: v-bind('props.height')
  }
</style>
