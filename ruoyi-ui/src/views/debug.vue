<template>
  <input v-myDirctive="'test'" />
</template>

<script setup lang="ts">
  interface AxiosResponse<T = any, D = any> {
    data: T
    status: number
    statusText: string
    headers: string
    config: string
    request?: any
  }

  interface PageQueryParam {
    pageNum: number
    pageSize: number
  }

  let obj: AxiosResponse<PageQueryParam> = {
    data: {
      pageNum: 1,
      pageSize: 2
    },
    status: 20,
    statusText: 'aa',
    headers: 'bb',
    config: 'cc'
  }
</script>
