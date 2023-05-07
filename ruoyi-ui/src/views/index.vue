<template>
  <div class="app-container">
    <el-row :gutter="16">
      <el-col :span="8">
        <el-card header="设备统计">
          <el-row :gutter="16">
            <el-col :span="8" style="padding:0 0">
              <div class="countCard">
                <div class="title" :style="{'background-color': '#409EFF'}">总数</div>
                <div class="value">{{ deviceTotal }}<span class="unit">台</span></div>
              </div>
            </el-col>
            <el-col :span="8">
              <div class="countCard">
                <div class="title" :style="{'background-color': '#67C23A'}">正常</div>
                <div class="value">{{ deviceNormal }}<span class="unit">台</span></div>
              </div>
            </el-col>
            <el-col :span="8">
              <div class="countCard">
                <div class="title" :style="{'background-color': '#F56C6C'}">异常</div>
                <div class="value">{{ deviceAbnormal }}<span class="unit">台</span></div>
              </div>
            </el-col>
          </el-row>
        </el-card>
        <el-card header="告警记录" class="mt10">
          <div class="seamlessScroll">
            <el-row class="header" :gutter="'16'">
              <el-col :span="3"><span>重要度</span></el-col>
              <el-col :span="17"><span>消息</span></el-col>
              <el-col :span="4"><span>来源</span></el-col>
            </el-row>
            <Vue3SeamlessScroll :list="alarmData" class="list" hover :limitScrollNum=12 :step=0.7 :singleWaitTime=0>
              <el-row v-for="(item, index) in alarmData" :key="index" class="item" :gutter="16">
                <el-col :span="3">
                  <el-icon class="mr5" color="#79c155" size="20px" v-if="item.importance === 1"><CircleCheckFilled/></el-icon>
                  <el-icon class="mr5" color="#E6A23C" size="20px" v-if="item.importance === 2"><WarningFilled/></el-icon>
                  <el-icon class="mr5" color="#F56C6C" size="20px" v-if="item.importance === 3"><CircleCloseFilled/></el-icon>
                </el-col>
                <el-col :span="17"><div class="message">{{ item.message }}</div></el-col>
                <el-col :span="4"><div class="message">{{ item.source }}</div></el-col>
              </el-row>
            </Vue3SeamlessScroll>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card header="不知道放啥" style="height:360px">
        </el-card>
        <el-card header="设备负载" class="mt10">
          <el-row :gutter="16">
            <el-col :span="12">
              <Liquidfill :option="cpuOption" :height="200"></Liquidfill>
            </el-col>
            <el-col :span="12">
              <Liquidfill :option="memOption" :height="200"></Liquidfill>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card>
          <template #header>
            <div class="flowHeader">
              <div>设备流量TOP5</div>
              <el-radio-group v-model="deviceFlowRadio" size="small">
                <el-radio-button label="in">入口</el-radio-button>
                <el-radio-button label="out">出口</el-radio-button>
              </el-radio-group>
            </div>
          </template>
          <el-row :gutter="16">
            <el-col :span="24">
              <ChartBar :option="deviceFlowOption" :height="275"></ChartBar>
            </el-col>
          </el-row>
        </el-card>
        <el-card class="mt10">
          <template #header>
            <div class="flowHeader">
              <div>端口流量TOP5</div>
              <el-radio-group v-model="portFlowRadio" size="small">
                <el-radio-button label="in">入口</el-radio-button>
                <el-radio-button label="out">出口</el-radio-button>
              </el-radio-group>
            </div>
          </template>
          <el-row :gutter="16">
            <el-col :span="24">
              <ChartBar :option="portFlowOption" :height="275"></ChartBar>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
  import Liquidfill from '@/components/charts/liquidfill.vue'
  import { Vue3SeamlessScroll } from 'vue3-seamless-scroll'
  import ChartBar from '@/components/charts/bar.vue'

  const deviceTotal = ref(47)
  const deviceNormal = ref(43)
  const deviceAbnormal = ref(4)
  const cpuData = ref(0.25)
  const memData = ref(0.55)
  const deviceFlowRadio = ref('in')
  const portFlowRadio = ref('in')

  const cpuOption = ref({
    title: { text: 'CPU利用率' },
    series: {
      data: [{ value: cpuData.value }],
      color: ['#00e298'] 
    }
  })

  const memOption = ref({
    title: { text: '内存利用率' },
    series: {
      data: [{ value: memData.value }],
      color: ['#E6A23C'] 
    }
  })

  const alarmData = ref<any[]>([
    { message: 'Network Path Monitor destination is reachable now', source: 'switch-1', alarmTime: '2023-05-03 14:30:30', importance: 1 },
    { message: 'Device Active and Responding', source: 'switch-2', alarmTime: '2023-05-03 14:30:30', importance: 2 },
    { message: 'The Agent Service is not communicating with server', source: 'switch-3', alarmTime: '2023-05-03 14:30:30', importance: 3 },
    { message: 'Network Path Monitor destination is reachable now', source: 'switch-1', alarmTime: '2023-05-03 14:30:30', importance: 1 },
    { message: 'Device Active and Responding', source: 'switch-2', alarmTime: '2023-05-03 14:30:30', importance: 2 },
    { message: 'The Agent Service is not communicating with server', source: 'switch-3', alarmTime: '2023-05-03 14:30:30', importance: 3 },
    { message: 'Network Path Monitor destination is reachable now', source: 'switch-1', alarmTime: '2023-05-03 14:30:30', importance: 1 },
    { message: 'Device Active and Responding', source: 'switch-2', alarmTime: '2023-05-03 14:30:30', importance: 2 },
    { message: 'The Agent Service is not communicating with server', source: 'switch-3', alarmTime: '2023-05-03 14:30:30', importance: 3 },
    { message: 'Network Path Monitor destination is reachable now', source: 'switch-1', alarmTime: '2023-05-03 14:30:30', importance: 1 },
    { message: 'Device Active and Responding', source: 'switch-2', alarmTime: '2023-05-03 14:30:30', importance: 2 },
    { message: 'Device Active and Responding', source: 'switch-2', alarmTime: '2023-05-03 14:30:30', importance: 2 }
  ])

  const deviceFlowOption = ref({
    yAxis: {
      data: ['device1', 'device2', 'device3', 'device4', 'device5']
    },
    series: {
      data: [998, 2587, 5894, 12534, 35264],
      itemStyle: {
        color: '#409EFF'
      }
    }
  })

  const portFlowOption = ref({
    yAxis: {
      data: ['port1', 'port2', 'port3', 'port4', 'port5']
    },
    series: {
      data: [998, 2587, 5894, 12534, 35264],
      itemStyle: {
        color: '#409EFF'
      }
    }
  })

  watchEffect(() => {
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
  })
</script>

<style lang="scss" scoped>
  .countCard {
    height: 150px;
    .title {
      border-top-left-radius: 5px;
      border-top-right-radius: 5px;
      color: #ffffff;
      font-size: 18px;
      padding: 5px;
    }
    .value {
      border: 2px solid #E4E7ED;
      text-align: center;
      font-size: 40px;
      padding: 20px 0 40px 0;
      .unit {
        font-size: 20px;
      }
    }
  }
  .seamlessScroll {
    height: 400px;
    width: 100%;
    margin: 0 auto;
    overflow: hidden;
    font-size: 14px;
    .header {
      background-color: #f5f5f5;
      padding: 5px 10px;
      z-index: 100;
    }
    .list {
      .item {
        padding: 5px 10px;
        border-top: 1px solid #E4E7ED;
        cursor: pointer;
        .message {
          overflow:hidden;
          text-overflow:ellipsis;
          white-space: nowrap;
        }
       
        &:hover {
          background-color: #d6eafa;
        }
      }
    }
  }

  .flowHeader {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
</style>
