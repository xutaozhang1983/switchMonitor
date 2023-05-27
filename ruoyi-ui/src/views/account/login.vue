<template>
  <div class="login">
    <el-form ref="loginRef" :model="loginForm" :rules="loginRules" class="login-form">
      <div class="header">
        <img class="img" :src="Logo"/>
        <h3 class="title">SoftStarManager</h3>
      </div>
      <el-form-item prop="username">
        <el-input v-model="loginForm.username" type="text" size="large" auto-complete="off" placeholder="账号">
          <template #prefix>
            <svg-icon icon-class="user" class="el-input__icon input-icon"/>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input v-model="loginForm.password" type="password" size="large" auto-complete="off" placeholder="密码" @keyup.enter="handleLogin">
          <template #prefix>
            <svg-icon icon-class="password" class="el-input__icon input-icon"/>
          </template>
        </el-input>
      </el-form-item>
      <el-checkbox v-model="loginForm.rememberMe" style="margin: 0px 0px 25px 0px">记住密码</el-checkbox>
      <el-form-item style="width: 100%">
        <el-button :loading="loading" size="large" type="primary" style="width: 100%" @click.prevent="handleLogin">
          <span v-if="!loading">登 录</span>
          <span v-else>登 录 中...</span>
        </el-button>
        <div style="float: right" v-if="register">
          <router-link class="link-type" :to="'/register'">立即注册</router-link>
        </div>
      </el-form-item>
    </el-form>
    <!--  底部  -->
    <div class="el-login-footer">
      <span>Copyright © 2023 All Rights Reserved.</span>
    </div>
  </div>
</template>

<script setup lang="ts">
  import { encrypt, decrypt } from '@/utils/jsencrypt'
  import useUserStore from '@/store/modules/account'
  import { localStorage } from '@/utils/storage'
  import Logo from '@/assets/logo/logo.png'

  const userStore = useUserStore()
  const router = useRouter()
  const { proxy } = getCurrentInstance() as any

  const loginForm = ref({
    username: 'admin',
    password: 'admin123',
    rememberMe: false,
    uuid: ''
  })

  const loginRules = {
    username: [{ required: true, trigger: 'blur', message: '请输入您的账号' }],
    password: [{ required: true, trigger: 'blur', message: '请输入您的密码' }]
  }

  const loading = ref(false)

  // 注册开关
  const register = ref(false)
  const redirect = ref(undefined)

  // 登录
  function handleLogin() {
    proxy.$refs.loginRef.validate((valid: any) => {
      if (valid) {
        loading.value = true
        // 勾选了需要记住密码设置在 cookie 中设置记住用户名和密码
        if (loginForm.value.rememberMe) {
          localStorage.set('username', loginForm.value.username)
          localStorage.set('password', encrypt(loginForm.value.password))
          localStorage.set('rememberMe', loginForm.value.rememberMe.toString())
        } else {
          // 否则移除
          localStorage.remove('username')
          localStorage.remove('password')
          localStorage.remove('rememberMe')
        }
        // 调用action的登录方法
        userStore.login(loginForm.value).then(() => {
          router.push({ path: redirect.value || '/' })
        }).catch(() => {
          loading.value = false
        })
      }
    })
  }

  // 获取token
  function getCookie() {
    const username = localStorage.get('username')
    const password = localStorage.get('password')
    const rememberMe = localStorage.get('rememberMe')
    loginForm.value = {
      username: username === undefined ? loginForm.value.username : username,
      password:
        password === undefined ? loginForm.value.password : decrypt(password),
      rememberMe: rememberMe === undefined ? false : Boolean(rememberMe),
      uuid: ''
    }
  }

  getCookie()
</script>

<style lang="scss" scoped>
  .login {
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

  .login-form {
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
  .login-tip {
    font-size: 13px;
    text-align: center;
    color: #bfbfbf;
  }
  .login-code {
    width: 33%;
    height: 40px;
    float: right;
    img {
      cursor: pointer;
      vertical-align: middle;
    }
  }
  .el-login-footer {
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
  .login-code-img {
    height: 40px;
    padding-left: 12px;
  }
</style>
