<template>
  <div :class="{ hidden: hidden }" class="pagination-container">
    <el-pagination
      :background="background"
      v-model:current-page="currentPage"
      v-model:page-size="pageSize"
      :layout="layout"
      :page-sizes="pageSizes"
      :pager-count="pagerCount"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </div>
</template>

<script setup lang="ts">
  import { scrollTo } from "@/utils/scroll-to";

  interface Props {
    total: number,
    page?: number,
    limit?: number,
    pageSizes?: number[],
    pagerCount?: number,
    layout?: string,
    background?: boolean,
    autoScroll?: boolean,
    hidden?: boolean
  }

  interface Emits {
    (e: 'update:page', val: number): void,
    (e: 'update:limit', val: number): void,
    (e: 'pagination', ob: { page: number, limit: number }): void
  }

  const props = withDefaults(defineProps<Props>(), {
    page: 1,
    limit: 20,
    pageSizes: () => [10, 20, 30, 50],
    // 移动端页码按钮的数量端默认值5
    pagerCount: document.body.clientWidth < 992 ? 5 : 7,
    layout: "total, sizes, prev, pager, next, jumper",
    background: true,
    autoScroll: true,
    hidden: false
  })

  const emits = defineEmits<Emits>()

  const currentPage = computed({
    get() {
      return props.page;
    },
    set(val: number) {
      emits("update:page", val);
    },
  })

  const pageSize = computed({
    get() {
      return props.limit;
    },
    set(val) {
      emits("update:limit", val);
    },
  })

  function handleSizeChange(val: number) {
    if (currentPage.value * val > props.total) {
      currentPage.value = 1;
    }
    emits("pagination", { page: currentPage.value, limit: val });
    if (props.autoScroll) {
      scrollTo(0, 800, () => {});
    }
  }

  function handleCurrentChange(val: number) {
    emits("pagination", { page: val, limit: pageSize.value });
    if (props.autoScroll) {
      scrollTo(0, 800, () => {});
    }
  }
</script>

<style scoped>
  .pagination-container {
    background: #fff;
    padding: 32px 16px;
  }
  .pagination-container.hidden {
    display: none;
  }
</style>
