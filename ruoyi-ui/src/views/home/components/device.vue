<template>
  <el-card header="设备统计">
    <el-row :gutter="10">
      <el-col :span="6">
        <div class="countCard">
          <div class="header" :style="{ 'background-color': '#2d8cf0' }">总数</div>
          <div class="body">
            <div class="text">
              <span>{{ deviceTotal }}</span>
            </div>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="countCard">
          <div class="header" :style="{ 'background-color': '#19be6b' }">正常</div>
          <div class="body">
            <div class="text">
              <span>{{ deviceNormal }}</span>
            </div>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="countCard">
          <div class="header" :style="{ 'background-color': '#ffa23a' }">异常</div>
          <div class="body">
            <div class="text">
              <span>{{ deviceAbnormal }}</span>
            </div>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="countCard">
          <div class="header" :style="{ 'background-color': '#c5c8ce' }">未监控</div>
          <div class="body">
            <div class="text">
              <span>{{ deviceUnknow }}</span>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
  </el-card>
</template>

<script setup lang="ts">
  import { getDeviceCount } from '@/api/home'

  const deviceTotal = ref(0)
  const deviceNormal = ref(0)
  const deviceAbnormal = ref(0)
  const deviceUnknow = ref(0)

  // 获取设备统计数据
  function getDeviceCountData () {
    getDeviceCount().then((response: any) => {
      deviceTotal.value = response.data.all || 0
      deviceNormal.value = response.data.ok || 0
      deviceAbnormal.value = response.data.err || 0
      deviceUnknow.value = response.data.unknow || 0
    })
  }

  getDeviceCountData()

</script>

<style lang="scss" scoped>
  .countCard {
    height: 200px;
    .header {
      height: 20%;
      display: flex;
      align-items: center;
      border-top-left-radius: 5px;
      border-top-right-radius: 5px;
      color: #ffffff;
      font-size: 16px;
      padding-left: 10px;
    }
    .body {
      height: 80%;
      border-left: 2px solid #E4E7ED;
      border-right: 2px solid #E4E7ED;
      border-bottom: 2px solid #E4E7ED;
      display: flex;
      justify-content: center;
      align-items: center;
      .text {
        font-size: 40px;
        .unit {
          font-size: 20px;
          color: #808695;
        }
      }
    }
  }
</style>