<template>
  <el-card :shadow="props.shadow" :title="props.title">
    <p slot="title" class="title">{{ title }}</p>
    <svg-icon :icon-class="props.icon"/>
    <slot></slot>
  </el-card>
</template>

<script setup lang="ts">
  interface Props {
    title: string,
    icon: string,
    color: string,
    shadow?: boolean
  }

  const props = withDefaults(defineProps<Props>(), {
    shadow: false
  })
  
</script>
<style lang="scss" scoped>
  .title {
    color: #515a6e;
  }
</style>
