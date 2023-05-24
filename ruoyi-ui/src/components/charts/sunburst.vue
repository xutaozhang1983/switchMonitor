<template>
  <v-chart ref="chartRef" class="chart" :style="{'--height': props.height + 'px'}" :option="option" autoresize></v-chart>
</template>

<script setup lang="ts">
  import { use } from 'echarts/core'
  import { CanvasRenderer } from 'echarts/renderers'
  import { SunburstChart } from 'echarts/charts'
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

  const chartRef = ref<any>(null)

  const option = ref({})
  const defaultOption = ref({})

  use([
    CanvasRenderer,
    SunburstChart,
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
    height: var(--height)
  }
</style>
