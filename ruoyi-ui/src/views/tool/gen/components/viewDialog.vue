<template>
  <!-- 预览界面 -->
  <el-dialog :title="genStore.preview.title" v-model="genStore.preview.open" width="80%" top="5vh" append-to-body custom-class="scrollbar">
    <el-tabs v-model="genStore.preview.activeName">
      <el-tab-pane v-for="(value, key) in genStore.preview.data"
        :label="(key as any).substring((key as any).lastIndexOf('/') + 1, (key as any).indexOf('.vm'))"
        :name="(key as any).substring((key as any).lastIndexOf('/') + 1, (key as any).indexOf('.vm'))" :key="value">
        <el-link :underline="false" icon="DocumentCopy" v-copyText="value" v-copyText:callback="copyTextSuccess"
          style="float:right">&nbsp;复制</el-link>
        <pre>{{ value }}</pre>
      </el-tab-pane>
    </el-tabs>
  </el-dialog>
</template>

<script setup lang="ts">
  import { ElMessage } from 'element-plus'
  import useGenStore from '../store'

  const genStore = useGenStore()

  /** 复制代码成功 */
  function copyTextSuccess() {
    ElMessage.success('复制成功')
  }

</script>