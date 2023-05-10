<template>
  <v-chart class="chart" :option="option" autoresize></v-chart>
</template>

<script setup lang="ts">
  import { use } from 'echarts/core'
  import { CanvasRenderer } from 'echarts/renderers'
  import 'echarts-liquidfill'
  import VChart from 'vue-echarts'
  import merge from 'deepmerge'

  import {
    TitleComponent,
    TooltipComponent,
    LegendComponent
  } from 'echarts/components'

  const defaultOption = ref({
    title: {
      left: 'center',
      top: '30',
      textStyle: {
        fontSize: '16',
        color: '#606266',
        fontWeight: 'normal',
      }
    },
    series: {
      type: 'liquidFill',
      radius: '90%',
      center: [ '50%', '50%' ],
      backgroundStyle: {
        color: '#ffffff',
        borderColor: '#F0F2F5',
        borderWidth: 5,
        shadowColor: '#ffffff',
        shadowBlur: 20
      },
      label: {
        fontSize: "40",
        color: '#303133'
      },
      outline: {
        itemStyle: {
          borderColor: '#DCDCDC',
          borderWidth: 3
        },
        borderDistance: 0
      },
      itemStyle: {
        opacity: 0.95,
        shadowColor: 'rgba(0, 0, 0, 0)'
      }
    }
  })
  const option = ref({})

  interface Props {
    height: string,
    option?: any
  }

  const props = withDefaults(defineProps<Props>(), {
    option: () => {}
  })

  watchEffect(() => {
    option.value = merge(defaultOption.value, props.option)
  })

  use([
    CanvasRenderer,
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
