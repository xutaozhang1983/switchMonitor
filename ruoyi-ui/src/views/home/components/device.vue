<template>
  <el-row :gutter="10">
    <el-col :xs="24" :sm="12" :md="12" :lg="6" :xl="6">
      <div class="countCard" :style="{'background': `url(${DeviceBg}) center / 100% no-repeat`}">
        <div class="body">
          <div>设备总数</div>
          <div class="value">{{ homeStore.deviceData.total }}</div>
        </div>
      </div>
    </el-col>
    <el-col :xs="24" :sm="12" :md="12" :lg="6" :xl="6">
      <div class="countCard" :style="{'background': `url(${MonitorBg}) center / 100% no-repeat`}">
        <div class="body">
          <div>监控设备数</div>
          <div class="value">{{ homeStore.deviceData.monitor }}</div>
        </div>
      </div>
    </el-col>
    <el-col :xs="24" :sm="12" :md="12" :lg="6" :xl="6">
      <div class="countCard" :style="{'background': `url(${DeviceNormalBg}) center / 100% no-repeat`}">
        <div class="body">
          <div>正常设备数</div>
          <div class="value">{{ homeStore.deviceData.normal }}</div>
        </div>
      </div>
    </el-col>
    <el-col :xs="24" :sm="12" :md="12" :lg="6" :xl="6">
      <div class="countCard" :style="{'background': `url(${DeviceAbnormalBg}) center / 100% no-repeat`}">
        <div class="body">
          <div>异常设备数</div>
          <div class="value">{{ homeStore.deviceData.abnormal }}</div>
        </div>
      </div>
    </el-col>
  </el-row>
</template>

<script setup lang="ts">
  import DeviceBg from '@/assets/images/deviceBg.png'
  import MonitorBg from '@/assets/images/monitorBg.png'
  import DeviceNormalBg from '@/assets/images/deviceNormalBg.png'
  import DeviceAbnormalBg from '@/assets/images/deviceAbnormalBg.png'

  import useHomeStore from '../store'

  const homeStore = useHomeStore()

</script>

<style lang="scss" scoped>
  .countCard {
    position: relative;
    height: 140px;
    color: #ffffff;
    .body {
      position: absolute;
      top: 25%;
      left: 48%;
      .value {
        font-size: 32px;
        font-weight: bold;
      }
    }
  }
</style>