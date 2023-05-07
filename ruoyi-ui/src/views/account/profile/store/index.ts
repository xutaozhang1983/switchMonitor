import { ElMessage } from 'element-plus'
import { UserUpdatePassword } from '@/types/api/system/user'
import { getUserProfile, updateUserProfile, updateUserPwd } from "@/api/system/user";

const useProfileStore = defineStore('useProfileStore',
  {
    state: () => ({
      userProFile: {} as any // 用户个人信息
    }),
    actions: {
      // 查询用户个人信息
      async getUserProfile() {
        try {
          let { data, postGroup, roleGroup }: any = await getUserProfile()
          this.userProFile = data
          this.userProFile.postGroup = postGroup
          this.userProFile.roleGroup = roleGroup
        } catch {}
      },
      // 更新用户个人信息
      async updateUserProfile() {
        try {
          await updateUserProfile(this.userProFile)
        } catch {}
      },
      // 更新用户密码
      async updateUserPwd(data: UserUpdatePassword) {
        try {
          await updateUserPwd(data)
          ElMessage.success("修改成功");
        } catch {}
      }
    }
  }
)

export default useProfileStore 