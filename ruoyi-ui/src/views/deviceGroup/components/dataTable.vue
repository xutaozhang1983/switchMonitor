<template>
  <el-table v-loading="deviceGroupStore.loading" :data="deviceGroupStore.deviceGroupData" @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="55" align="center" />
    <el-table-column label="编号" prop="id" width="100"/>
    <el-table-column label="名称" prop="groupName"/>
    <el-table-column label="报警接收用户组" :show-overflow-tooltip="true">
      <template #default="scope">
        <div>{{ transFromUserNamesByIds(scope.row.alarmUserGrpId) }}</div>
      </template>
    </el-table-column>
    <el-table-column label="报警发送次数" prop="alarmMaxTimes"/>
    <el-table-column label="状态" align="center" prop="status">
      <template #default="scope">
        <el-switch
          v-model="scope.row.status"
          active-value="0"
          inactive-value="1"
          @change="handleStatusChange(scope.row)"
        ></el-switch>
      </template>
    </el-table-column>
    <el-table-column label="备注" prop="remark" />
    <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
      <template #default="scope">
        <el-tooltip content="修改" placement="top" v-if="scope.row.roleId !== 1">
          <el-button type="text" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:role:edit']"></el-button>
        </el-tooltip>
        <el-tooltip content="删除" placement="top" v-if="scope.row.roleId !== 1">
          <el-button type="text" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['system:role:remove']"></el-button>
        </el-tooltip>
      </template>
    </el-table-column>
  </el-table>
  <!--数据分页-->
  <pagination
    v-show="deviceGroupStore.total > 0"
    :total="deviceGroupStore.total"
    v-model:page="deviceGroupStore.queryParams.pageNum"
    v-model:limit="deviceGroupStore.queryParams.pageSize"
    @pagination="deviceGroupStore.getDeviceGroupData"
  />
</template>

<script setup lang="ts">
  import { ElMessageBox, ElMessage } from 'element-plus'
  import useDeviceGroupStore from '../store'

  const deviceGroupStore = useDeviceGroupStore()

  // 选择条数
  function handleSelectionChange(selection: any) {
    deviceGroupStore.ids = selection.map((item: any) => item.id)
    deviceGroupStore.single = selection.length != 1
    deviceGroupStore.multiple = !selection.length
  }

  // 将展示的用户id转为用户名
  function transFromUserNamesByIds (ids: string) {
    let result = []
    if (ids) {
      for (let id of ids.split(',')) {
        let item = deviceGroupStore.userData.filter((item: any) => item.userId === Number(id))
        if (item.length) result.push(item[0].userName)
      }
    }
    return result.join(',')
  }

  // 设备组状态修改
  function handleStatusChange(row: any) {
    if (row.status === '1' && row.deviceCount > 0) {
      ElMessage.warning('设备组存在设备，不允许禁用')
      row.status = row.status === "0" ? "1" : "0"
      return
    }
    let text = row.status === "0" ? "启用" : "停用";
    ElMessageBox.confirm('确认要"' + text + '""' + row.groupName + '"设备组吗?', '系统提示', { type: 'warning' }).then(async () => {
      let sendData = {
        id: row.id,
        status: row.status
      }
      await deviceGroupStore.changeDeviceGroupStatus(sendData)
    }).then(() => {
      ElMessage.success(text + "成功")
    }).catch(function () {
      row.status = row.status === "0" ? "1" : "0"
    })
  }

  // 删除按钮操作
  function handleDelete(row: any) {
    if (row.deviceCount > 0) {
      ElMessage.warning('设备组存在设备，不允许删除')
      return
    }
    deviceGroupStore.ids = [ row.id ]
    ElMessageBox.confirm('是否确认删除设备组编号为"' + deviceGroupStore.ids + '"的数据项？', '系统提示', { type: 'warning' }).then(async () => {
      await deviceGroupStore.delDeviceGroup()
      deviceGroupStore.getDeviceGroupData()
      ElMessage.success("删除成功")
    }).catch(() => {})
  }

  // 修改按钮操作
  async function handleUpdate(row: any) {
    deviceGroupStore.resetFormData()
    await deviceGroupStore.getDeviceGroupInfo(row.id)
    deviceGroupStore.formTitle = "修改设备组"
    deviceGroupStore.showFormDialog = true
  }  
</script>