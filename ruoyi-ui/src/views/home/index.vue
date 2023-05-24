<template>
  <div class="app-container">
    <!--设备总数-->
    <Device/>
    <el-row :gutter="10">
      <el-col :xs="24" :sm="12" :md="12" :lg="16" :xl="16">
        <!--告警事件-->
        <Events class="mt10"/>
      </el-col>
      <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8">
        <!--设备类型分布-->
        <DeviceType class="mt10"/>
      </el-col>
    </el-row>
    <el-row :gutter="10">
      <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8">
        <!--设备组-->
        <DeviceGroup class="mt10"/>
      </el-col>
      <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8">
        <!--设备流量TOP-->
        <DeviceFlowTop class="mt10"/>
      </el-col>
      <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8">
        <!--端口流量TOP-->
        <PortFlowTop class="mt10"/>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
  import Device from './components/device.vue'
  import DeviceGroup from './components/deviceGroup.vue'
  import Events from './components/events.vue'
  import DeviceType from './components/deviceType.vue'
  import DeviceFlowTop from './components/deviceFlowTop.vue'
  import PortFlowTop from './components/portFlowTop.vue'

  import useHomeStore from './store'

  const homeStore = useHomeStore()
  let timer: NodeJS.Timer | null = null

  homeStore.initPage()
  timer = setInterval(() => {
    homeStore.initPage()
  }, 300000)

  onUnmounted(() => {
    clearInterval(Number(timer))
  })
</script>

<style lang="scss" scoped>
  
</style>
