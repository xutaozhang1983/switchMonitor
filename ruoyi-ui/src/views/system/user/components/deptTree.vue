<template>
  <div class="head-container">
    <el-input v-model="deptName" placeholder="请输入部门名称" clearable prefix-icon="Search" style="margin-bottom: 20px"/>
  </div>
  <div class="head-container">
    <el-tree
      :data="userStore.deptTreeData"
      :props="defaultProps"
      :expand-on-click-node="false"
      :filter-node-method="filterNode"
      ref="deptTreeRef"
      node-key="id"
      highlight-current
      default-expand-all
      @node-click="handleNodeClick"
    />
  </div>
</template>

<script setup lang="ts">
  import { ElTree } from 'element-plus'
  import useUserStore from '../store'

  const userStore = useUserStore()

  const defaultProps = {
    children: 'children',
    label: 'label',
  }

  const deptName = ref('')
  const deptTreeRef = ref<InstanceType<typeof ElTree> | null>(null)

  // 通过条件过滤节点
  const filterNode = (value: string, data: any) => {
    if (!value) return true
    return data.label.includes(value)
  }

  // 根据名称筛选部门树
  watch(deptName, (val) => {
    deptTreeRef.value!.filter(val)
  })

  // 监听是否需要重置部门树的key
  watchEffect(() => {
    if (userStore.resetDeptTreeKey) {
      (deptTreeRef.value as any).setCurrentKey(null)
      userStore.resetDeptTreeKey = false
    }
  })

  // 节点单击事件
  async function handleNodeClick(data: any) {
    userStore.queryParams.deptId = data.id
    await userStore.getUserData()
  }

</script>