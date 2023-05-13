<template>
  <div class="app-container">
    <el-row :gutter="10">
      <el-col :span="12">
        <!--设备统计-->
        <Device/>
      </el-col>
      <el-col :span="12">
        <!--设备组-->
        <Group/>
      </el-col>
    </el-row>
    <el-row :gutter="10">
      <el-col :span="24">
        <!--告警事件-->
        <Events class="mt10"/>
      </el-col>
    </el-row>
    <el-row :gutter="10">
      <el-col :span="12">
        <!--设备流量TOP-->
        <DeviceFlowTop class="mt10"/>
      </el-col>
      <el-col :span="12">
        <!--端口流量TOP-->
        <PortFlowTop class="mt10"/>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
  import Device from './components/device.vue'
  import Group from './components/group.vue'
  import Events from './components/events.vue'
  import DeviceFlowTop from './components/deviceFlowTop.vue'
  import PortFlowTop from './components/portFlowTop.vue'
 
  /*watchEffect(() => {
    if (deviceFlowRadio.value == 'in') {
      deviceFlowOption.value.series.data = [998, 2587, 5894, 12534, 35264]
      deviceFlowOption.value.series.itemStyle.color = '#409EFF'
    } else {
      deviceFlowOption.value.series.data = [1234, 2598, 5894, 12534, 35264]
      deviceFlowOption.value.series.itemStyle.color = '#67C23A'
    }
    if (portFlowRadio.value == 'in') {
      portFlowOption.value.series.data = [998, 2587, 5894, 12534, 35264]
      portFlowOption.value.series.itemStyle.color = '#409EFF'
    } else {
      portFlowOption.value.series.data = [1234, 2598, 5894, 12534, 35264]
      portFlowOption.value.series.itemStyle.color = '#67C23A'
    }
  })*/

  
</script>

<style lang="scss" scoped>
  
</style>
