<template>
  <el-dialog :title="userStore.formTitle" v-model="userStore.showFormDialog" width="600px" append-to-body>
    <el-form :model="userStore.formData" :rules="rules" ref="userRef" label-width="80px">
      <el-row>
        <el-col :span="12">
          <el-form-item label="用户昵称" prop="nickName">
            <el-input v-model="userStore.formData.nickName" placeholder="请输入用户昵称" maxlength="30"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="归属部门" prop="deptId">
            <el-tree-select
              v-model="userStore.formData.deptId"
              :data="userStore.deptTreeData"
              :props="{ value: 'id', label: 'label', children: 'children' }"
              value-key="id"
              placeholder="请选择归属部门"
              check-strictly
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="手机号码" prop="phonenumber">
            <el-input v-model="userStore.formData.phonenumber" placeholder="请输入手机号码" maxlength="11"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="userStore.formData.email" placeholder="请输入邮箱" maxlength="50"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item v-if="userStore.formData.userId == undefined" label="用户名称" prop="userName">
            <el-input v-model="userStore.formData.userName" placeholder="请输入用户名称" maxlength="30"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item v-if="userStore.formData.userId == undefined" label="用户密码" prop="password">
            <el-input v-model="userStore.formData.password" placeholder="请输入用户密码" type="password" maxlength="20" show-password/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="用户性别">
            <el-select v-model="userStore.formData.sex" placeholder="请选择">
              <el-option v-for="dict in sys_user_sex" :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="状态">
            <el-radio-group v-model="userStore.formData.status">
              <el-radio v-for="dict in sys_normal_disable" :key="dict.value" :label="dict.value">{{ dict.label }}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="岗位">
            <el-select v-model="userStore.formData.postIds" multiple placeholder="请选择">
              <el-option v-for="item in userStore.postData" :key="item.postId" :label="item.postName" :value="item.postId" :disabled="item.status == 1"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="角色">
            <el-select v-model="userStore.formData.roleIds" multiple placeholder="请选择">
              <el-option v-for="item in userStore.roleData" :key="item.roleId" :label="item.roleName" :value="item.roleId" :disabled="item.status == 1"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-form-item label="备注">
            <el-input v-model="userStore.formData.remark" type="textarea" placeholder="请输入内容"></el-input>
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
  import { ElMessage } from 'element-plus'
  import useUserStore from '../store'

  const { proxy } = getCurrentInstance() as any;
  const userStore = useUserStore()

  const { sys_normal_disable, sys_user_sex }: { sys_normal_disable: DictType[], sys_user_sex: DictType[] } = proxy.useDict(
    "sys_normal_disable",
    "sys_user_sex"
  )

  const rules = {
    userName: [
      { required: true, message: "用户名称不能为空", trigger: "blur" },
      { min: 2, max: 20, message: "用户名称长度必须介于 2 和 20 之间", trigger: "blur" }
    ],
    nickName: [
      { required: true, message: "用户昵称不能为空", trigger: "blur" }
    ],
    password: [
      { required: true, message: "用户密码不能为空", trigger: "blur" },
      { min: 5, max: 20, message: "用户密码长度必须介于 5 和 20 之间", trigger: "blur" }
    ],
    email: [
      { type: "email", message: "请输入正确的邮箱地址", trigger: ["blur", "change"] }
    ],
    phonenumber: [
      { pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: "请输入正确的手机号码", trigger: "blur" }
    ]
  }
  
  // 提交按钮
  function submitForm() {
    proxy.$refs["userRef"].validate(async (valid: boolean) => {
      if (valid) {
        if (userStore.formData.userId != undefined) {
          await userStore.updateUser()
          ElMessage.success("修改成功")
          userStore.showFormDialog = false
          userStore.getUserData()
        } else {
          await userStore.addUser()
          ElMessage.success("新增成功")
          userStore.showFormDialog = false
          userStore.getUserData()
        }
      }
    });
  }

  // 取消按钮
  function cancel() {
    userStore.resetFormData()
    userStore.showFormDialog = false
  }
</script>