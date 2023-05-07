<template>
  <div class="top-right-btn" :style="style">
    <el-row>
      <el-tooltip class="item" effect="dark" :content="showSearch ? '隐藏搜索' : '显示搜索'" placement="top" v-if="search">
        <el-button circle icon="Search" @click="toggleSearch()" />
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="刷新" placement="top">
        <el-button circle icon="Refresh" @click="refresh()" />
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="显隐列" placement="top" v-if="columns.length">
        <el-button circle icon="Menu" @click="showColumn()" />
      </el-tooltip>
    </el-row>
    <el-dialog :title="title" v-model="open" append-to-body>
      <el-transfer :titles="['显示', '隐藏']" v-model="value" :data="columns" @change="dataChange"></el-transfer>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">

  interface Columns {
    key: number,
    label: string,
    visible: boolean
  }

  interface Props {
    showSearch?: boolean,
    columns?: Columns[],
    search?: boolean,
    gutter?: number
  }

  interface Emits {
    (e: 'update:showSearch', val: boolean): void,
    (e: 'queryTable'): void
  }

  const props = withDefaults(defineProps<Props>(), {
    showSearch: true,
    columns: () => [],
    search: true,
    gutter: 10
  })

  const emits = defineEmits<Emits>()

  // 显隐数据
  const value = ref<number[]>([])
  // 弹出层标题
  const title = ref("显示/隐藏")
  // 是否显示弹出层
  const open = ref(false)

  const style = computed(() => {
    const result = {} as any
    if (props.gutter) {
      result.marginRight = `${props.gutter / 2}px`
    }
    return result
  })

  // 搜索
  function toggleSearch() {
    emits("update:showSearch", !props.showSearch);
  }

  // 刷新
  function refresh() {
    emits("queryTable")
  }

  // 右侧列表元素变化
  function dataChange(data: any) {
    for (let item of props.columns) {
      const key = item.key;
      item.visible = !data.includes(key)
    }
  }

  // 打开显隐列dialog
  function showColumn() {
    open.value = true
  }

  // 显隐列初始默认隐藏列
  for (let item of props.columns) {
    if (item.visible === false) {
      value.value.push(item.key)
    }
  }
</script>

<style lang="scss" scoped>
  :deep(.el-transfer__button) {
    border-radius: 50%;
    display: block;
    margin-left: 0px;
  }
  :deep(.el-transfer__button:first-child) {
    margin-bottom: 10px;
  }

  .my-el-transfer {
    text-align: center;
  }
</style>
