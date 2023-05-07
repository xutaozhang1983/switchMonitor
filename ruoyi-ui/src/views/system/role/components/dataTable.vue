<template>
  <el-table v-loading="roleStore.loading" :data="roleStore.roleData" @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="55" align="center" />
    <el-table-column label="角色编号" prop="roleId" width="120" />
    <el-table-column label="角色名称" prop="roleName" :show-overflow-tooltip="true" width="150"/>
    <el-table-column label="权限字符" prop="roleKey" :show-overflow-tooltip="true" width="150"/>
    <el-table-column label="显示顺序" prop="roleSort" width="100" />
    <el-table-column label="状态" align="center" width="100">
      <template #default="scope">
        <el-switch v-model="scope.row.status" active-value="0" inactive-value="1" @change="handleStatusChange(scope.row)"></el-switch>
      </template>
    </el-table-column>
    <el-table-column label="创建时间" align="center" prop="createTime">
      <template #default="scope">
        <span>{{ dayjs(scope.row.createTime).format('YYYY-MM-DD HH:mm:ss') }}</span>
      </template>
    </el-table-column>
    <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
      <template #default="scope">
        <el-tooltip content="修改" placement="top" v-if="scope.row.roleId !== 1">
          <el-button type="text" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:role:edit']"></el-button>
        </el-tooltip>
        <el-tooltip content="删除" placement="top" v-if="scope.row.roleId !== 1">
          <el-button type="text" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['system:role:remove']"></el-button>
        </el-tooltip>
        <el-tooltip content="数据权限" placement="top" v-if="scope.row.roleId !== 1">
          <el-button type="text" icon="CircleCheck" @click="handleDataScope(scope.row)" v-hasPermi="['system:role:edit']"></el-button>
        </el-tooltip>
        <el-tooltip content="分配用户" placement="top" v-if="scope.row.roleId !== 1">
          <el-button type="text" icon="User" @click="handleAuthUser(scope.row)" v-hasPermi="['system:role:edit']"></el-button>
        </el-tooltip>
      </template>
    </el-table-column>
  </el-table>
  <!--数据分页-->
  <pagination
    v-show="roleStore.total > 0"
    :total="roleStore.total"
    v-model:page="roleStore.queryParams.pageNum"
    v-model:limit="roleStore.queryParams.pageSize"
    @pagination="roleStore.getRoleData"
  />
</template>

<script setup lang="ts">
  import { ElMessageBox, ElMessage } from 'element-plus'
  import useRoleStore from '../store'
  import { dayjs } from 'element-plus'

  const roleStore = useRoleStore()
  const router = useRouter()

  // 选择条数
  function handleSelectionChange(selection: any) {
    roleStore.ids = selection.map((item: any) => item.roleId)
    roleStore.single = selection.length != 1
    roleStore.multiple = !selection.length
  }

  // 角色状态修改
  function handleStatusChange(row: any) {
    let text = row.status === "0" ? "启用" : "停用";
    ElMessageBox.confirm('确认要"' + text + '""' + row.roleName + '"角色吗?', '系统提示', { type: 'warning' }).then(async () => {
      let sendData = {
        roleId: row.roleId,
        status: row.status
      }
      await roleStore.changeRoleStatus(sendData)
    }).then(() => {
      ElMessage.success(text + "成功")
    }).catch(function () {
      row.status = row.status === "0" ? "1" : "0"
    })
  }

  // 删除按钮操作
  function handleDelete(row: any) {
    roleStore.ids = [ row.roleId ]
    ElMessageBox.confirm('是否确认删除角色编号为"' + roleStore.ids + '"的数据项？', '系统提示', { type: 'warning' }).then(async () => {
      await roleStore.delRole()
      roleStore.getRoleData()
      ElMessage.success("删除成功")
    }).catch(() => {})
  }

  // 修改按钮操作
  async function handleUpdate(row: any) {
    roleStore.resetFormData()
    await roleStore.getRoleInfo(row.roleId)
    await roleStore.getMenuTreeByRoleId(row.roleId)
    roleStore.formTitle = "修改角色"
    roleStore.showFormDialog = true
  }

  /** 分配数据权限操作 */
  async function handleDataScope(row: any) {
    roleStore.resetFormData()
    await roleStore.getRoleInfo(row.roleId)
    await roleStore.getDeptTreeByRoleId(row.roleId)
    roleStore.formTitle = "分配数据权限"
    roleStore.showAuthDataDialog = true
  }

  // 跳转角色分配
  function handleAuthUser(row: any) {
    const roleId = row.roleId
    router.push("/system/role-auth/user/" + roleId)
  }
  
</script>