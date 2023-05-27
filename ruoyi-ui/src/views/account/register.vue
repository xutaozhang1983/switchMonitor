<template>
  <div class="register">
    <el-form ref="registerRef" :model="registerForm" :rules="registerRules" class="register-form">
      <div class="header">
        <img class="img" :src="Logo"/>
        <h3 class="title">SoftStarManager</h3>
      </div>
      <el-form-item prop="username">
        <el-input v-model="registerForm.username" type="text" size="large" auto-complete="off" placeholder="账号">
          <template #prefix>
            <svg-icon icon-class="user" class="el-input__icon input-icon"/>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input v-model="registerForm.password" type="password" size="large" auto-complete="off" placeholder="密码" @keyup.enter="handleRegister">
          <template #prefix>
            <svg-icon icon-class="password" class="el-input__icon input-icon"/>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="confirmPassword">
        <el-input v-model="registerForm.confirmPassword" type="password" size="large" auto-complete="off" placeholder="确认密码" @keyup.enter="handleRegister">
          <template #prefix>
            <svg-icon icon-class="password" class="el-input__icon input-icon"/>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item style="width: 100%">
        <el-button :loading="loading" size="large" type="primary" style="width: 100%" @click.prevent="handleRegister">
          <span v-if="!loading">注 册</span>
          <span v-else>注 册 中...</span>
        </el-button>
        <div style="float: right">
          <router-link class="link-type" :to="'/login'">使用已有账户登录</router-link>
        </div>
      </el-form-item>
    </el-form>
    <!--  底部  -->
    <div class="el-register-footer">
      <span>Copyright © 2023 All Rights Reserved.</span>
    </div>
  </div>
</template>

<script setup lang="ts">
  import { ElMessageBox } from 'element-plus'
  import { register } from '@/api/account/index'
  import Logo from '@/assets/logo/logo.png'

  const router = useRouter()
  const { proxy } = getCurrentInstance() as any

  const registerForm = ref({
    username: '',
    password: '',
    confirmPassword: '',
    uuid: ''
  })

  const equalToPassword = (rule: any, value: string, callback: any) => {
    if (registerForm.value.password !== value) {
      callback(new Error('两次输入的密码不一致'))
    } else {
      callback()
    }
  }

  const registerRules = {
    username: [
      { required: true, trigger: 'blur', message: '请输入您的账号' },
      {
        min: 2,
        max: 20,
        message: '用户账号长度必须介于 2 和 20 之间',
        trigger: 'blur'
      }
    ],
    password: [
      { required: true, trigger: 'blur', message: '请输入您的密码' },
      {
        min: 5,
        max: 20,
        message: '用户密码长度必须介于 5 和 20 之间',
        trigger: 'blur'
      }
    ],
    confirmPassword: [
      { required: true, trigger: 'blur', message: '请再次输入您的密码' },
      { required: true, validator: equalToPassword, trigger: 'blur' }
    ]
  }

  const loading = ref(false)

  // 注册
  function handleRegister() {
    proxy.$refs.registerRef.validate((valid: boolean) => {
      if (valid) {
        loading.value = true
        register(registerForm.value).then(() => {
          const username = registerForm.value.username
          ElMessageBox.alert(
            "<font color='red'>恭喜你，您的账号 " + username + ' 注册成功！</font>',
            '系统提示',
            {
              dangerouslyUseHTMLString: true,
              type: 'success'
            }
          ).then(() => {
            router.push('/login')
          }).catch(() => {})
        }).catch(() => {
          loading.value = false
        })
      }
    })
  }

</script>

<style lang="scss" scoped>
  .register {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
    background-image: url('../../assets/images/login-background.jpg');
    background-size: cover;
  }

  .header {
    display: flex;
    justify-content: center;
    align-items: center;
    .title {
      text-align: center;
      color: #606266;
      font-weight: bold;
    }
    .img {
      margin-right: 10px;
      width: 30px;
      height: 30px;
    }
  }

  .register-form {
    border-radius: 6px;
    background: #ffffff;
    width: 400px;
    padding: 25px 25px 5px 25px;
    .el-input {
      height: 40px;
      input {
        height: 40px;
      }
    }
    .input-icon {
      height: 39px;
      width: 14px;
      margin-left: 0px;
    }
  }
  .register-tip {
    font-size: 13px;
    text-align: center;
    color: #bfbfbf;
  }
  .register-code {
    width: 33%;
    height: 40px;
    float: right;
    img {
      cursor: pointer;
      vertical-align: middle;
    }
  }
  .el-register-footer {
    height: 40px;
    line-height: 40px;
    position: fixed;
    bottom: 0;
    width: 100%;
    text-align: center;
    color: #fff;
    font-family: Arial;
    font-size: 12px;
    letter-spacing: 1px;
  }
  .register-code-img {
    height: 40px;
    padding-left: 12px;
  }
</style>
