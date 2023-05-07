<template>
  <el-table v-loading="userStore.loading" :data="userStore.userData" @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="50" align="center" />
    <el-table-column label="用户编号" align="center" key="userId" prop="userId" v-if="userStore.columns[0].visible"/>
    <el-table-column label="用户名称" align="center" key="userName" prop="userName" v-if="userStore.columns[1].visible" :show-overflow-tooltip="true"/>
    <el-table-column label="用户昵称" align="center" key="nickName" prop="nickName" v-if="userStore.columns[2].visible" :show-overflow-tooltip="true"/>
    <el-table-column label="部门" align="center" key="deptName" prop="dept.deptName" v-if="userStore.columns[3].visible" :show-overflow-tooltip="true"/>
    <el-table-column label="手机号码" align="center" key="phonenumber" prop="phonenumber" v-if="userStore.columns[4].visible" width="120"/>
    <el-table-column label="状态" align="center" key="status" v-if="userStore.columns[5].visible">
      <template #default="scope">
        <el-switch
          v-model="scope.row.status"
          active-value="0"
          inactive-value="1"
          @change="handleStatusChange(scope.row)"
        ></el-switch>
      </template>
    </el-table-column>
    <el-table-column label="创建时间" align="center" prop="createTime" v-if="userStore.columns[6].visible" width="160">
      <template #default="scope">
        <span>{{ dayjs(scope.row.createTime).format('YYYY-MM-DD HH:mm:ss') }}</span>
      </template>
    </el-table-column>
    <el-table-column label="操作" align="center" width="150" class-name="small-padding fixed-width">
      <template #default="scope">
        <el-tooltip content="修改" placement="top" v-if="scope.row.userId !== 1">
          <el-button type="text" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:user:edit']"></el-button>
        </el-tooltip>
        <el-tooltip content="删除" placement="top" v-if="scope.row.userId !== 1">
          <el-button type="text" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['system:user:remove']"></el-button>
        </el-tooltip>
        <el-tooltip content="重置密码" placement="top" v-if="scope.row.userId !== 1">
          <el-button type="text" icon="Key" @click="handleResetPwd(scope.row)" v-hasPermi="['system:user:resetPwd']"></el-button>
        </el-tooltip>
        <el-tooltip content="分配角色" placement="top" v-if="scope.row.userId !== 1">
          <el-button type="text" icon="CircleCheck" @click="handleAuthRole(scope.row)" v-hasPermi="['system:user:edit']"></el-button>
        </el-tooltip>
      </template>
    </el-table-column>
  </el-table>
  <!--数据分页-->
  <pagination
    v-show="userStore.total > 0"
    :total="userStore.total"
    v-model:page="userStore.queryParams.pageNum"
    v-model:limit="userStore.queryParams.pageSize"
    @pagination="userStore.getUserData"
  />
</template>

<script setup lang="ts">
  import { ElMessageBox, ElMessage } from 'element-plus'
  import useUserStore from '../store'
  import { dayjs } from 'element-plus'

  const userStore = useUserStore()
  const router = useRouter();

  // 选择条数
  function handleSelectionChange(selection: any) {
    userStore.ids = selection.map((item: any) => item.userId)
    userStore.single = selection.length != 1
    userStore.multiple = !selection.length
  }

  // 用户状态修改
  function handleStatusChange(row: any) {
    let text = row.status === "0" ? "启用" : "停用";
    ElMessageBox.confirm('确认要"' + text + '""' + row.userName + '"用户吗?', '系统提示', { type: 'warning' }).then(async () => {
      let sendData = {
        userId: row.userId,
        status: row.status
      }
      await userStore.changeUserStatus(sendData)
    }).then(() => {
      ElMessage.success(text + "成功")
    }).catch(function () {
      row.status = row.status === "0" ? "1" : "0"
    })
  }

  // 删除按钮操作
  function handleDelete(row: any) {
    userStore.ids = [ row.userId ]
    ElMessageBox.confirm('是否确认删除用户编号为"' + userStore.ids + '"的数据项？', '系统提示', { type: 'warning' }).then(async () => {
      await userStore.delUser()
      userStore.getUserData()
      ElMessage.success("删除成功")
    }).catch(() => {})
  }

  // 修改按钮操作
  async function handleUpdate(row: any) {
    userStore.resetFormData()
    await userStore.getUserInfo(row.userId)
    userStore.formTitle = "修改用户";
    userStore.showFormDialog = true;
  }

  // 重置密码按钮操作
  function handleResetPwd(row: any) {
    ElMessageBox.prompt('请输入"' + row.userName + '"的新密码', "提示", {
      confirmButtonText: "确定",
      cancelButtonText: "取消",
      closeOnClickModal: false,
      inputPattern: /^.{5,20}$/,
      inputErrorMessage: "用户密码长度必须介于 5 和 20 之间"
    }).then(async ({ value }) => {
      let sendData = {
        userId: row.userId,
        password: value
      }
      await userStore.resetUserPwd(sendData)
      ElMessage.success("修改成功，新密码是：" + value);
    })
    .catch(() => {});
  }

  // 跳转角色分配
  function handleAuthRole(row: any) {
    const userId = row.userId;
    router.push("/system/user-auth/role/" + userId);
  }
  
</script>