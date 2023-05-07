<template>
  <el-dialog :title="deptStore.formTitle" v-model="deptStore.showFormDialog" width="600px" append-to-body>
    <el-form ref="deptRef" :model="deptStore.formData" :rules="rules" label-width="80px">
      <el-row>
        <el-col :span="24" v-if="deptStore.formData.parentId !== 0">
          <el-form-item label="上级部门" prop="parentId">
            <el-tree-select
              v-model="deptStore.formData.parentId"
              :data="deptStore.deptTreeData"
              :props="{
                value: 'deptId',
                label: 'deptName',
                children: 'children'
              }"
              value-key="deptId"
              placeholder="选择上级部门"
              check-strictly />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="部门名称" prop="deptName">
            <el-input v-model="deptStore.formData.deptName" placeholder="请输入部门名称" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="显示排序" prop="orderNum">
            <el-input-number v-model="deptStore.formData.orderNum" controls-position="right" :min="0" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="负责人" prop="leader">
            <el-input v-model="deptStore.formData.leader" placeholder="请输入负责人" maxlength="20" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="联系电话" prop="phone">
            <el-input v-model="deptStore.formData.phone" placeholder="请输入联系电话" maxlength="11" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="deptStore.formData.email" placeholder="请输入邮箱" maxlength="50" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="部门状态">
            <el-radio-group v-model="deptStore.formData.status">
              <el-radio v-for="dict in sys_normal_disable" :key="dict.value" :label="dict.value">{{ dict.label }}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
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
  import useDeptStore from '../store'

  const { proxy } = getCurrentInstance() as any
  const deptStore = useDeptStore()

  const { sys_normal_disable, sys_show_hide }: 
    { sys_normal_disable: DictType[], sys_show_hide: DictType[] } = proxy.useDict(
    "sys_normal_disable",
    "sys_show_hide"
  )
  
  const rules = ref({
    parentId: [
      { required: true, message: '上级部门不能为空', trigger: 'blur' }
    ],
    deptName: [
      { required: true, message: '部门名称不能为空', trigger: 'blur' }
    ],
    orderNum: [
      { required: true, message: '显示排序不能为空', trigger: 'blur' }
    ],
    email: [
      { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
    ],
    phone: [
      { pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: '请输入正确的手机号码', trigger: 'blur' }
    ]
  })
  
  // 提交按钮
  function submitForm() {
    proxy.$refs["deptRef"].validate(async (valid: boolean) => {
      if (valid) {
        if (deptStore.formData.deptId != undefined) {
          await deptStore.updateDept()
          ElMessage.success("修改成功")
          deptStore.showFormDialog = false
          deptStore.getDeptData()
        } else {
          await deptStore.addDept()
          ElMessage.success("新增成功")
          deptStore.showFormDialog = false
          deptStore.getDeptData()
        }
      }
    })
  }

  // 取消按钮
  function cancel() {
    deptStore.resetFormData()
    deptStore.showFormDialog = false
  }
</script>