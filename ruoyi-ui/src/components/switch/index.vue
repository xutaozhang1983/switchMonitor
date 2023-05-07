<template>
  <div class="container">
    <div class="body" :style="{'--height': props.height + 'px', 'margin-top': b > 2 ? '30px' : '0'}" v-for="(b, index1) in bodyNum" :key="b">
      <div class="portBox" v-for="(p, index2) in 16" :key="p">
        <div class="port" v-if="props.data.ports[index2+(index1*16)]">
          <img :src="SwitchOn" v-if="props.data.ports[index2+(index1*16)].status === 0"/>
          <img :src="SwitchOff" v-else/>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
  import SwitchOn from '@/assets/images/switch-on.png'
  import SwitchOff from '@/assets/images/switch-off.png'

  interface Port {
    total: number,
    ports: { name: string, status: number }[]
  }

  interface Props {
    height?: number,
    data: Port
  }

  const props = withDefaults(defineProps<Props>(), {
    height: 90
  })

  const bodyNum = computed(() => {
    return Math.ceil(props.data.total/16)
  })
</script>

<style lang="scss">
  .container {
    display: flex;
    align-items: center;
    flex-wrap: wrap;
    .body {
      margin-right: 30px;
      width: 330px;
      padding: 10px;
      height: var(--height);
      background-color: #ebebec;
      border: 1px solid #d5d5d7;
      display: grid;
      grid-template-columns: repeat(8, 40px);
      grid-template-rows: repeat(2, 40px);
      .portBox {
        width: 30px;
        height: 30px;
        background-color: #dcddde;
        display: flex;
        justify-content: center;
        align-items: center;
        .port {
          width: 25px;
          height: 25px;
          img {
            width: 100%;
            height: 100%;
          }
        }
      }
    }
  }
</style>