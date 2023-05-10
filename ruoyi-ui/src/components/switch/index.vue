<template>
  <div class="container">
    <div class="body" v-for="(item1, index1) in props.data" :key="index1">
      <div class="box" v-for="(item2, index2) in item1" :key="index2">
        <div class="name">{{ transName(item2.itemName) }}</div>
        <div class="port">
          <img :src="SwitchOn" v-if="item2.status === '1'"/>
          <img :src="SwitchOff" v-else/>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
  import SwitchOn from '@/assets/images/switch-on.png'
  import SwitchOff from '@/assets/images/switch-off.png'

  interface Props {
    data: any
  }

  const props = defineProps<Props>()

  // 过滤名字
  function transName(name: string) {
    let result = name.match(/^(.).+(\d\/\d\/\d)$/)
    return result![1]+result![2]
  }

</script>

<style lang="scss">
  .container {
    display: flex;
    align-items: center;
    flex-wrap: wrap;
    padding: 20px;
    .body {
      margin-right: 10px;
      padding: 5px;
      background-color: #ebebec;
      border: 1px solid #d5d5d7;
      display: flex;
      align-items: center;
      margin-bottom: 40px;
      .box {
        position: relative;
        margin-right: 10px;
        width: 40px;
        height: 40px;
        background-color: #dcddde;
        display: flex;
        justify-content: center;
        align-items: center;
        .name {
          font-size: 12px;
          position: absolute;
          left: 0px;
          top: -25px;
          color: #A8ABB2;
        }
        .port {
          width: 30px;
          height: 30px;
          img {
            width: 100%;
            height: 100%;
          }
        }
        &:last-child {
          margin-right: 0px;
        }
      }
    }
  }
</style>