<template>
  <div class="app-container">
    <h4 class="form-header h4">基本信息</h4>
    <el-form :model="authRole.user" label-width="80px">
      <el-row>
        <el-col :span="8" :offset="2">
          <el-form-item label="用户昵称" prop="nickName">
            <el-input v-model="authRole.user.nickName" disabled />
          </el-form-item>
        </el-col>
        <el-col :span="8" :offset="2">
          <el-form-item label="登录账号" prop="userName">
            <el-input v-model="authRole.user.userName" disabled />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <h4 class="form-header h4">角色信息</h4>
    <el-table
      v-loading="loading"
      :row-key="getRowKey"
      @row-click="clickRow"
      ref="roleRef"
      @selection-change="handleSelectionChange"
      :data="authRole.roles.slice((pageNum - 1) * pageSize, pageNum * pageSize)">
      <el-table-column label="序号" width="55" type="index" align="center">
        <template #default="scope">
          <span>{{ (pageNum - 1) * pageSize + scope.$index + 1 }}</span>
        </template>
      </el-table-column>
      <el-table-column type="selection" :reserve-selection="true" width="55"></el-table-column>
      <el-table-column label="角色编号" align="center" prop="roleId" />
      <el-table-column label="角色名称" align="center" prop="roleName" />
      <el-table-column label="权限字符" align="center" prop="roleKey" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template #default="scope">
          <span>{{ dayjs(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="pageNum" v-model:limit="pageSize"/>

    <el-form label-width="100px">
      <div style="text-align: center; margin-left: -120px; margin-top: 30px">
        <el-button type="primary" @click="submitForm()">提交</el-button>
        <el-button @click="close()">返回</el-button>
      </div>
    </el-form>
  </div>
</template>

<script setup name="AuthRole" lang="ts">
  import { ElMessage } from 'element-plus'
  import { UserUpdateAuthRole } from '@/types/api/system/user'
  import { getAuthRole, updateAuthRole } from "@/api/system/user"
  import { dayjs } from "element-plus"

  const route = useRoute()
  const { proxy } = getCurrentInstance() as any

  const loading = ref(true)
  const total = ref(0)
  const pageNum = ref(1)
  const pageSize = ref(10)
  const ids = ref([])

  const authRole = ref<any>({}) // 授权角色

  // 单击选中行数据
  function clickRow(row: any) {
    proxy.$refs["roleRef"].toggleRowSelection(row);
  }
  // 多选框选中数据
  function handleSelectionChange(selection: any) {
    ids.value = selection.map((item: any) => item.roleId);
  }
  // 保存选中的数据编号
  function getRowKey(row: any) {
    return row.roleId;
  }
  // 关闭按钮
  function close() {
    const obj = { path: "/system/user" };
    proxy.$tab.closeOpenPage(obj);
  }
  // 提交按钮
  async function submitForm() {
    let sendData: UserUpdateAuthRole = {
      userId: authRole.user.userId,
      roleIds: ids.value.join(',')
    }
    updateAuthRole(sendData).then(() => {
      ElMessage.success("授权成功")
    })
    close()
  }

  onBeforeMount(async () => {
    const userId = route.params && route.params.userId
    if (userId) {
      loading.value = true
      // 获取授权角色
      getAuthRole(Number(userId)).then((response: any) => {
        authRole.value.user = response.user
        authRole.value.roles = response.roles
      })
      loading.value = false
      authRole.roles.forEach((row: any) => {
        if (row.flag) {
          proxy.$refs["roleRef"].toggleRowSelection(row)
        }
      })
    }
  })

</script>
