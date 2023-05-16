<template>
  <el-dialog :title="roleStore.formTitle" v-model="roleStore.showAuthDataDialog" width="500px" append-to-body>
    <el-form :model="roleStore.formData" label-width="80px">
      <el-form-item label="角色名称">
        <el-input v-model="roleStore.formData.roleName" :disabled="true" />
      </el-form-item>
      <el-form-item label="权限字符">
        <el-input v-model="roleStore.formData.roleKey" :disabled="true" />
      </el-form-item>
      <el-form-item label="权限范围">
        <el-select v-model="roleStore.formData.dataScope" @change="dataScopeSelectChange">
          <el-option v-for="item in dataScopeOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="数据权限" v-show="roleStore.formData.dataScope == '2'">
        <el-checkbox v-model="roleStore.deptExpand" @change="handleCheckedTreeExpand($event)">展开/折叠</el-checkbox>
        <el-checkbox v-model="roleStore.deptNodeAll" @change="handleCheckedTreeNodeAll($event)">全选/全不选</el-checkbox>
        <el-checkbox v-model="roleStore.formData.deptCheckStrictly" @change="handleCheckedTreeConnect($event)">父子联动</el-checkbox>
        <el-tree
          class="tree-border"
          :data="roleStore.deptTreeData"
          show-checkbox
          default-expand-all
          ref="deptRef"
          node-key="id"
          :check-strictly="!roleStore.formData.deptCheckStrictly"
          empty-text="加载中，请稍候"
          :props="{ label: 'label', children: 'children' }"
        ></el-tree>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button type="primary" @click="submitDataScope">确 定</el-button>
        <el-button @click="cancelDataScope">取 消</el-button>
      </div>
    </template>
  </el-dialog>
</template>
<script setup lang="ts">
  import { ElMessage, ElTree } from 'element-plus'
  import useRoleStore from '../store'
  
  const roleStore = useRoleStore()

  const deptRef = ref<InstanceType<typeof ElTree>>()

  /** 数据范围选项*/
  const dataScopeOptions = ref([
    { value: "1", label: "全部数据权限" },
    { value: "2", label: "自定数据权限" },
    { value: "3", label: "本部门数据权限" },
    { value: "4", label: "本部门及以下数据权限" },
    { value: "5", label: "仅本人数据权限" }
  ])

  /** 选择角色权限范围触发 */
  function dataScopeSelectChange(value: string) {
    if (value !== "2") {
      deptRef.value!.setCheckedKeys([])
    }
  }

  /** 树权限（展开/折叠）*/
  function handleCheckedTreeExpand(value: boolean) {
    for (let i = 0; i < roleStore.deptTreeData.length; i++) {
      deptRef.value!.store.nodesMap[roleStore.deptTreeData[i].id].expanded = value
    }
  }
  /** 树权限（全选/全不选） */
  function handleCheckedTreeNodeAll(value: boolean) {
    deptRef.value!.setCheckedNodes(value ? roleStore.deptTreeData : [])
  }

  /** 树权限（父子联动） */
  function handleCheckedTreeConnect(value: boolean) {
    roleStore.formData.deptCheckStrictly = value ? true : false
  }

  /** 所有部门节点数据 */
  function getDeptAllCheckedKeys() {
    // 目前被选中的部门节点
    let checkedKeys = deptRef.value!.getCheckedKeys();
    // 半选中的部门节点
    let halfCheckedKeys = deptRef.value!.getHalfCheckedKeys();
    checkedKeys.unshift.apply(checkedKeys, halfCheckedKeys);
    return checkedKeys;
  }

  // 设置角色权限部门默认选中状态
  watchEffect(() => {
    if (roleStore.showAuthDataDialog && roleStore.formTitle === '分配数据权限') {
      roleStore.deptCheckedKeys.forEach((val: number) => {
        nextTick(() => {
          (deptRef.value as any).setChecked(val, true, false)    
        })
      })
    }
  })

  /** 提交按钮（数据权限） */
  async function submitDataScope() {
    if (roleStore.formData.roleId != undefined) {
      roleStore.formData.deptIds = getDeptAllCheckedKeys() as number[]
      await roleStore.dataScope()
      ElMessage.success("修改成功")
      roleStore.showAuthDataDialog = false
      roleStore.getRoleData()
    }
  }
  /** 取消按钮（数据权限）*/
  function cancelDataScope() {
    roleStore.showAuthDataDialog = false
    roleStore.resetFormData()
  }
</script>