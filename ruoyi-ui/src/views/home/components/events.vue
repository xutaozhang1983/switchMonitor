<template>
  <el-card>
    <template #header>
      <div class="seamlessScroll-header">
        <div>告警事件</div>
        <div class="seamlessScroll-header-tips">
          <div class="seamlessScroll-header-tips-item">
            <el-icon class="mr5" color="#808695" size="20px"><WarningFilled/></el-icon>
            INFO
          </div>
          <div class="seamlessScroll-header-tips-item">
            <el-icon class="mr5" color="#f99f6b" size="20px"><WarningFilled/></el-icon>
            WARNING
          </div>
          <div class="seamlessScroll-header-tips-item">
            <el-icon class="mr5" color="#ed4014" size="20px"><WarningFilled/></el-icon>
            ERROR
          </div>
        </div>
      </div>
    </template>
    <div class="seamlessScroll-box">
      <el-row class="header" :gutter="'16'">
        <el-col :span="3"><span>级别</span></el-col>
        <el-col :span="17"><span>内容</span></el-col>
        <el-col :span="4"><span>来源</span></el-col>
      </el-row>
      <Vue3SeamlessScroll :list="homeStore.eventsData" class="list" hover :limitScrollNum=12 :step=0.7 :singleWaitTime=0>
        <el-row v-for="(item, index) in homeStore.eventsData" :key="index" class="item" :gutter="16">
          <el-col :span="3">
            <el-icon class="mr5" color="#808695" size="20px" v-if="item.alarmLevel === 'INFO'"><WarningFilled/></el-icon>
            <el-icon class="mr5" color="#f99f6b" size="20px" v-if="item.alarmLevel === 'WARNING'"><WarningFilled/></el-icon>
            <el-icon class="mr5" color="#ed4014" size="20px" v-if="item.alarmLevel === 'ERROR'"><WarningFilled/></el-icon>
          </el-col>
          <el-col :span="17"><div class="message">{{ item.alarmContent }}</div></el-col>
          <el-col :span="4"><div class="message">{{ item.deviceName }}</div></el-col>
        </el-row>
      </Vue3SeamlessScroll>
    </div>
  </el-card>
</template>

<script setup lang="ts">
  import { Vue3SeamlessScroll } from 'vue3-seamless-scroll'

  import useHomeStore from '../store'

  const homeStore = useHomeStore()
</script>

<style lang="scss" scoped>
  .seamlessScroll-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    &-tips {
      display: flex;
      align-items: center;
      &-item {
        margin-right: 10px;
        display: flex;
        align-items: center;
        font-size: 12px;
        color: #909399;
      }
    }
  }
  
  .seamlessScroll-box {
    height: 200px;
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
</style>