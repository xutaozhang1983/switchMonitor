<template>
  <el-dialog :title="roleStore.formTitle" v-model="roleStore.showFormDialog" width="500px" append-to-body>
    <el-form ref="roleRef" :model="roleStore.formData" :rules="rules" label-width="100px">
      <el-form-item label="角色名称" prop="roleName">
        <el-input v-model="roleStore.formData.roleName" placeholder="请输入角色名称" />
      </el-form-item>
      <el-form-item prop="roleKey">
        <template #label>
          <span>
            <el-tooltip content="控制器中定义的权限字符，如：@PreAuthorize(`@ss.hasRole('admin')`)" placement="top">
              <el-icon><question-filled /></el-icon>
            </el-tooltip>
            权限字符
          </span>
        </template>
        <el-input v-model="roleStore.formData.roleKey" placeholder="请输入权限字符" />
      </el-form-item>
      <el-form-item label="角色顺序" prop="roleSort">
        <el-input-number v-model="roleStore.formData.roleSort" controls-position="right" :min="0"/>
      </el-form-item>
      <el-form-item label="状态">
        <el-radio-group v-model="roleStore.formData.status">
          <el-radio v-for="dict in dictNormalDisable" :key="dict.value" :label="dict.value">{{ dict.label }}</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="菜单权限">
        <el-checkbox v-model="roleStore.menuExpand" @change="handleCheckedTreeExpand($event)">展开/折叠</el-checkbox>
        <el-checkbox v-model="roleStore.menuNodeAll" @change="handleCheckedTreeNodeAll($event)">全选/全不选</el-checkbox>
        <el-checkbox v-model="roleStore.formData.menuCheckStrictly" @change="handleCheckedTreeConnect($event)">父子联动</el-checkbox>
        <el-tree
          class="tree-border"
          :data="roleStore.menuTreeData"
          show-checkbox
          ref="menuRef"
          node-key="id"
          :check-strictly="!roleStore.formData.menuCheckStrictly"
          empty-text="加载中，请稍候"
          :props="{ label: 'label', children: 'children' }"
        ></el-tree>
      </el-form-item>
      <el-form-item label="备注">
        <el-input v-model="roleStore.formData.remark" type="textarea" placeholder="请输入内容"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
  import { ElMessage, ElTree } from 'element-plus'
  import useRoleStore from '../store'
  import { dictNormalDisable } from '@/utils/dict'

  const { proxy } = getCurrentInstance() as any
  const roleStore = useRoleStore()

  const menuRef = ref<InstanceType<typeof ElTree>>()
  
  const rules = {
    roleName: [
      { required: true, message: "角色名称不能为空", trigger: "blur" },
    ],
    roleKey: [
      { required: true, message: "权限字符不能为空", trigger: "blur" },
    ],
    roleSort: [
      { required: true, message: "角色顺序不能为空", trigger: "blur" },
    ]
  }

  // 树权限（展开/折叠)
  function handleCheckedTreeExpand(value: boolean) {
    for (let i = 0; i < roleStore.menuTreeData.length; i++) {
      menuRef.value!.store.nodesMap[roleStore.menuTreeData[i].id].expanded = value
    }
  }

  // 树权限（全选/全不选)
  function handleCheckedTreeNodeAll(value: boolean) {
    menuRef.value!.setCheckedNodes(value ? roleStore.menuTreeData : [])
  }

  // 树权限（父子联动
  function handleCheckedTreeConnect(value: boolean) {
    roleStore.formData.menuCheckStrictly = value ? true : false
  }

  // 所有菜单节点数据
  function getMenuAllCheckedKeys() {
    // 目前被选中的菜单节点
    let checkedKeys = menuRef.value!.getCheckedKeys()
    // 半选中的菜单节点
    let halfCheckedKeys = menuRef.value!.getHalfCheckedKeys()
    checkedKeys.unshift.apply(checkedKeys, halfCheckedKeys)
    return checkedKeys
  }

  // 设置角色授权菜单默认选中状态
  watchEffect(() => {
    if (roleStore.showFormDialog && roleStore.formTitle === '修改角色') {
      roleStore.menuCheckedKeys.forEach((val: number) => {
        nextTick(() => {
          (menuRef.value as any).setChecked(val, true, false)    
        })
      })
    }
  })
  
  // 提交按钮
  function submitForm() {
    proxy.$refs["roleRef"].validate(async (valid: boolean) => {
      if (valid) {
        if (roleStore.formData.roleId != undefined) {
          roleStore.formData.menuIds = getMenuAllCheckedKeys() as number[]
          await roleStore.updateRole()
          ElMessage.success("修改成功")
          roleStore.showFormDialog = false
          roleStore.getRoleData()
        } else {
          roleStore.formData.menuIds = getMenuAllCheckedKeys() as number[]
          await roleStore.addRole()
          ElMessage.success("新增成功")
          roleStore.showFormDialog = false
          roleStore.getRoleData()
        }
      }
    })
  }

  // 取消按钮
  function cancel() {
    roleStore.resetFormData()
    roleStore.showFormDialog = false
  }
</script>