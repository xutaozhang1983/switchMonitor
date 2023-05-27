<template>
  <div class="head-container">
    <el-input v-model="groupName" placeholder="请输入设备组名称" clearable prefix-icon="Search" style="margin-bottom: 20px"/>
  </div>
  <div class="head-container">
    <el-tree
      :data="deviceStore.deviceGroupData"
      :props="defaultProps"
      :expand-on-click-node="false"
      :filter-node-method="filterNode"
      ref="deviceGroupTreeRef"
      node-key="id"
      highlight-current
      default-expand-all
      @node-click="handleNodeClick"
    />
  </div>
</template>

<script setup lang="ts">
  import { ElTree } from 'element-plus'
  import useNetMonitorStore from '../store'

  const deviceStore = useNetMonitorStore()

  const defaultProps = {
    children: 'children',
    label: 'label',
  }

  const groupName = ref('')
  const deviceGroupTreeRef = ref<InstanceType<typeof ElTree> | null>(null)

  // 通过条件过滤节点
  const filterNode = (value: string, data: any) => {
    if (!value) return true
    return data.label.includes(value)
  }

  // 根据名称筛选设备组树
  watch(groupName, (val) => {
    deviceGroupTreeRef.value!.filter(val)
  })

  // 监听是否需要重置部门树的key
  watchEffect(() => {
    if (deviceStore.resetDeviceGroupKey) {
      (deviceGroupTreeRef.value as any).setCurrentKey(null)
      deviceStore.resetDeviceGroupKey = false
    }
  })

  // 节点单击事件
  async function handleNodeClick(data: any) {
    deviceStore.queryParams.groupId = data.id ? data.id : undefined
    await deviceStore.getDeviceData()
  }

</script>