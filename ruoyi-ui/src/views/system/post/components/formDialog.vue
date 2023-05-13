<template>
  <el-dialog :title="postStore.formTitle" v-model="postStore.showFormDialog" width="500px" append-to-body>
    <el-form ref="postRef" :model="postStore.formData" :rules="rules" label-width="80px">
      <el-form-item label="岗位名称" prop="postName">
        <el-input v-model="postStore.formData.postName" placeholder="请输入岗位名称" />
      </el-form-item>
      <el-form-item label="岗位编码" prop="postCode">
        <el-input v-model="postStore.formData.postCode" placeholder="请输入编码名称" />
      </el-form-item>
      <el-form-item label="岗位顺序" prop="postSort">
        <el-input-number
          v-model="postStore.formData.postSort"
          controls-position="right"
          :min="0" />
      </el-form-item>
      <el-form-item label="岗位状态" prop="status">
        <el-radio-group v-model="postStore.formData.status">
          <el-radio
            v-for="dict in sys_normal_disable"
            :key="dict.value"
            :label="dict.value"
            >{{ dict.label }}</el-radio
          >
        </el-radio-group>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input
          v-model="postStore.formData.remark"
          type="textarea"
          placeholder="请输入内容" />
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
  import { ElMessage } from 'element-plus'
  import usePostStore from '../store'

  const { proxy } = getCurrentInstance() as any
  const postStore = usePostStore()

  const { sys_normal_disable, sys_show_hide }: 
    { sys_normal_disable: DictType[], sys_show_hide: DictType[] } = proxy.useDict(
    "sys_normal_disable",
    "sys_show_hide"
  )
  
  const rules = ref({
    parentId: [
      { required: true, message: '上级岗位不能为空', trigger: 'blur' }
    ],
    deptName: [
      { required: true, message: '岗位名称不能为空', trigger: 'blur' }
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
    proxy.$refs["postRef"].validate(async (valid: boolean) => {
      if (valid) {
        if (postStore.formData.postId != undefined) {
          await postStore.updatePost()
          ElMessage.success("修改成功")
          postStore.showFormDialog = false
          postStore.getPostData()
        } else {
          await postStore.addPost()
          ElMessage.success("新增成功")
          postStore.showFormDialog = false
          postStore.getPostData()
        }
      }
    })
  }

  // 取消按钮
  function cancel() {
    postStore.resetFormData()
    postStore.showFormDialog = false
  }
</script>