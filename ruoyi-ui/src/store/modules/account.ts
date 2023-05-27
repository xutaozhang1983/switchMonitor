import { login, logout, getInfo } from '@/api/account'
import { LoginFormData } from '@/types/api/account/index'
import { localStorage } from '@/utils/storage'
import defAva from '@/assets/images/profile.jpg'

const useAccountStore = defineStore('useAccountStore', {
  state: () => ({
    token: localStorage.get('token') || '',
    name: '',
    avatar: '',
    roles: [] as any[],
    permissions: [] as any[],
    license: {} as any
  }),
  actions: {
    // 登录
    login(loginData: LoginFormData) {
      loginData.username = loginData.username.trim()
      return new Promise((resolve, reject) => {
        login(loginData)
          .then((res: any) => {
            localStorage.set('token', res.token)
            this.token = res.token
            resolve(true)
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
    // 获取用户信息
    getInfo() {
      return new Promise((resolve, reject) => {
        getInfo().then((res: any) => {
          const user = res.user
          const avatar = user.avatar == '' || user.avatar == null ? defAva : import.meta.env.VITE_APP_BASE_API + user.avatar
          if (res.roles && res.roles.length > 0) {
            // 验证返回的roles是否是一个非空数组
            this.roles = res.roles
            this.permissions = res.permissions
          } else {
            this.roles = ['ROLE_DEFAULT']
          }
          this.name = user.userName
          this.avatar = avatar
          this.license = res.license ? JSON.parse(res.license) : {}
          resolve(res)
        })
        .catch((error) => {
          reject(error)
        })
      })
    },
    // 退出系统
    logOut() {
      return new Promise((resolve, reject) => {
        logout()
          .then(() => {
            this.token = ''
            this.roles = []
            this.permissions = []
            localStorage.remove('token')
            resolve(true)
          })
          .catch((error) => {
            reject(error)
          })
      })
    }
  }
})

export default useAccountStore
