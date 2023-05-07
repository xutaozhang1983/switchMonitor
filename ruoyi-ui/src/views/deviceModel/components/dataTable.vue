<template>
  <el-table v-loading="deviceModelStore.loading" :data="deviceModelStore.deviceModelData" @selection-change="handleSelectionChange">
    <el-table-column DeviceModel="selection" width="55" align="center" />
    <el-table-column label="型号编号" prop="DeviceModelId"/>
    <el-table-column label="型号名称" prop="DeviceModelName" :show-overflow-tooltip="true"/>
    <el-table-column label="接口数量" prop="port" :show-overflow-tooltip="true"/>
    <el-table-column label="厂商" prop="factoryName" :show-overflow-tooltip="true"/>
    <el-table-column label="参数" prop="params" :show-overflow-tooltip="true"/>
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
    v-show="deviceModelStore.total > 0"
    :total="deviceModelStore.total"
    v-model:page="deviceModelStore.queryParams.pageNum"
    v-model:limit="deviceModelStore.queryParams.pageSize"
    @pagination="deviceModelStore.getDeviceModelData"
  />
</template>

<script setup lang="ts">
  import { ElMessageBox, ElMessage } from 'element-plus'
  import useDeviceModelStore from '../store'
  import { dayjs } from 'element-plus'

  const deviceModelStore = useDeviceModelStore()
  const router = useRouter()
  const emits = defineEmits(['setMenuChecked', 'setDeptChecked'])

  // 选择条数
  function handleSelectionChange(selection: any) {
    deviceModelStore.ids = selection.map((item: any) => item.roleId)
    deviceModelStore.single = selection.length != 1
    deviceModelStore.multiple = !selection.length
  }

  // 型号状态修改
  function handleStatusChange(row: any) {
    let text = row.status === "0" ? "启用" : "停用";
    ElMessageBox.confirm('确认要"' + text + '""' + row.roleName + '"型号吗?', '系统提示', { type: 'warning' }).then(async () => {
      let sendData = {
        roleId: row.roleId,
        status: row.status
      }
      await deviceModelStore.changeDeviceModelStatus(sendData)
    }).then(() => {
      ElMessage.success(text + "成功")
    }).catch(function () {
      row.status = row.status === "0" ? "1" : "0"
    })
  }

  // 删除按钮操作
  function handleDelete(row: any) {
    deviceModelStore.ids = [ row.roleId ]
    ElMessageBox.confirm('是否确认删除型号编号为"' + deviceModelStore.ids + '"的数据项？', '系统提示', { type: 'warning' }).then(async () => {
      await deviceModelStore.delDeviceModel()
      deviceModelStore.getDeviceModelData()
      ElMessage.success("删除成功")
    }).catch(() => {})
  }

  // 修改按钮操作
  async function handleUpdate(row: any) {
    deviceModelStore.resetFormData()
    await deviceModelStore.getDeviceModelInfo(row.roleId)
    deviceModelStore.formTitle = "修改型号"
    deviceModelStore.showFormDialog = true
    emits('setMenuChecked')
  }  
</script>