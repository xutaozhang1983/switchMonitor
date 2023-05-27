<template>
  <div class="head-container">
    <el-input v-model="name" placeholder="请输入编码设备名称" clearable prefix-icon="Search" style="margin-bottom: 20px"/>
  </div>
  <div class="head-container">
    <el-tree
      :data="deviceStore.encodeData"
      :props="defaultProps"
      :expand-on-click-node="false"
      :filter-node-method="filterNode"
      ref="encodeTreeRef"
      node-key="id"
      highlight-current
      default-expand-all
      @node-click="handleNodeClick"
    />
  </div>
</template>

<script setup lang="ts">
  import { ElTree } from 'element-plus'
  import useCameraMonitorStore from '../store'

  const deviceStore = useCameraMonitorStore()

  const defaultProps = {
    children: 'children',
    label: 'label',
  }

  const name = ref('')
  const encodeTreeRef = ref<InstanceType<typeof ElTree> | null>(null)

  // 通过条件过滤节点
  const filterNode = (value: string, data: any) => {
    if (!value) return true
    return data.label.includes(value)
  }

  // 根据名称筛选编码设备树
  watch(name, (val) => {
    encodeTreeRef.value!.filter(val)
  })

  // 监听是否需要重置部门树的key
  watchEffect(() => {
    if (deviceStore.resetEncodeKey) {
      (encodeTreeRef.value as any).setCurrentKey(null)
      deviceStore.resetEncodeKey = false
    }
  })

  // 节点单击事件
  async function handleNodeClick(data: any) {
    deviceStore.queryParams.encodeDevIndexCode = data.id ? data.id : undefined
    await deviceStore.getCameraData()
  }

</script>