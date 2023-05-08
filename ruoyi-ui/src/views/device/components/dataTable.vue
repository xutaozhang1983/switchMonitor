<template>
  <el-table v-loading="deviceStore.loading" :data="deviceStore.deviceData" @selection-change="handleSelectionChange" @row-click="handleClickRow">
    <el-table-column type="selection" width="55" align="center" />
    <el-table-column label="设备名称" prop="deviceName" :show-overflow-tooltip="true"/>
    <el-table-column label="所在组" prop="deviceName" :show-overflow-tooltip="true"/>
    <el-table-column label="设备状态" align="center">
      <template #default="scope">
        <div class="deviceStatus" v-if="scope.row.deviceStatus === '0'">
          <el-icon class="mr5" color="#79c155" size="20px"><CircleCheckFilled /></el-icon>
          <span>正常</span>
        </div>
        <div class="deviceStatus" v-else>
          <el-icon class="mr5" color="#f67f7f"  size="20px"><WarningFilled /></el-icon>
          <span>异常</span>
        </div>
      </template>
    </el-table-column>
    <el-table-column label="接口状态" align="center">
      <template #default="scope">
        <div class="deviceStatus font-bold">
          <span style="color: #59a9fc">{{ scope.row.port }}</span>
          <span style="color: #C0C4CC"> / </span>
          <span style="color: #79c155">{{ scope.row.port - scope.row.num }}</span>
          <span style="color: #C0C4CC"> / </span>
          <span style="color: #f67f7f">{{ scope.row.num }}</span>
        </div>
      </template>
    </el-table-column>
    <el-table-column label="IP地址" prop="ip" :show-overflow-tooltip="true"/>
    <el-table-column label="设备型号" prop="deviceModel" :show-overflow-tooltip="true"/>
    <el-table-column label="厂商" prop="factoryName" :show-overflow-tooltip="true"/>
    <el-table-column label="接口数量" prop="port" :show-overflow-tooltip="true"/>
    <el-table-column label="备注" prop="remark" />
    <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
      <template #default="scope">
        <el-tooltip content="修改" placement="top">
          <el-button type="text" icon="Edit" @click.stop="handleUpdate(scope.row)" v-hasPermi="['system:device:edit']"></el-button>
        </el-tooltip>
        <el-tooltip content="删除" placement="top">
          <el-button type="text" icon="Delete" @click.stop="handleDelete(scope.row)" v-hasPermi="['system:device:remove']"></el-button>
        </el-tooltip>
      </template>
    </el-table-column>
  </el-table>
  <!--数据分页-->
  <pagination
    v-show="deviceStore.total > 0"
    :total="deviceStore.total"
    v-model:page="deviceStore.queryParams.pageNum"
    v-model:limit="deviceStore.queryParams.pageSize"
    @pagination="deviceStore.getDeviceData"
  />
</template>

<script setup lang="ts">
  import { ElMessageBox, ElMessage, ElTooltip } from 'element-plus'
  import useDeviceStore from '../store'

  const deviceStore = useDeviceStore()
  const router = useRouter()
  const emits = defineEmits(['setMenuChecked', 'setDeptChecked'])

  // 跳转到监控
  function handleClickRow(row: any) {
    router.push({
      path: '/monitor',
      params: {
        deviceId: row.deviceId
      }
    })
  }

  // 选择条数
  function handleSelectionChange(selection: any) {
    deviceStore.ids = selection.map((item: any) => item.roleId)
    deviceStore.single = selection.length != 1
    deviceStore.multiple = !selection.length
  }

  // 设备状态修改
  function handleStatusChange(row: any) {
    let text = row.status === "0" ? "启用" : "停用";
    ElMessageBox.confirm('确认要"' + text + '""' + row.roleName + '"设备吗?', '系统提示', { type: 'warning' }).then(async () => {
      let sendData = {
        roleId: row.roleId,
        status: row.status
      }
      await deviceStore.changeDeviceStatus(sendData)
    }).then(() => {
      ElMessage.success(text + "成功")
    }).catch(function () {
      row.status = row.status === "0" ? "1" : "0"
    })
  }

  // 删除按钮操作
  function handleDelete(row: any) {
    deviceStore.ids = [ row.roleId ]
    ElMessageBox.confirm('是否确认删除设备编号为"' + deviceStore.ids + '"的数据项？', '系统提示', { type: 'warning' }).then(async () => {
      await deviceStore.delDevice()
      deviceStore.getDeviceData()
      ElMessage.success("删除成功")
    }).catch(() => {})
  }

  // 修改按钮操作
  async function handleUpdate(row: any) {
    deviceStore.resetFormData()
    await deviceStore.getDeviceInfo(row.roleId)
    deviceStore.formTitle = "修改设备"
    deviceStore.showFormDialog = true
    emits('setMenuChecked')
  }
</script>

<style lang="scss" scoped>
  .deviceStatus {
    display: flex;
    justify-content: center;
    align-items: center;
  }
</style>