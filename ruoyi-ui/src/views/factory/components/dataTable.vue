<template>
  <el-table v-loading="factoryStore.loading" :data="factoryStore.factoryData" @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="55" align="center" />
    <el-table-column label="厂商编号" prop="factoryId"/>
    <el-table-column label="厂商名称" prop="factoryName" :show-overflow-tooltip="true"/>
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
    v-show="factoryStore.total > 0"
    :total="factoryStore.total"
    v-model:page="factoryStore.queryParams.pageNum"
    v-model:limit="factoryStore.queryParams.pageSize"
    @pagination="factoryStore.getFactoryData"
  />
</template>

<script setup lang="ts">
  import { ElMessageBox, ElMessage } from 'element-plus'
  import useFactoryStore from '../store'
  import { dayjs } from 'element-plus'

  const factoryStore = useFactoryStore()
  const router = useRouter()
  const emits = defineEmits(['setMenuChecked', 'setDeptChecked'])

  // 选择条数
  function handleSelectionChange(selection: any) {
    factoryStore.ids = selection.map((item: any) => item.roleId)
    factoryStore.single = selection.length != 1
    factoryStore.multiple = !selection.length
  }

  // 厂商状态修改
  function handleStatusChange(row: any) {
    let text = row.status === "0" ? "启用" : "停用";
    ElMessageBox.confirm('确认要"' + text + '""' + row.roleName + '"厂商吗?', '系统提示', { type: 'warning' }).then(async () => {
      let sendData = {
        roleId: row.roleId,
        status: row.status
      }
      await factoryStore.changeFactoryStatus(sendData)
    }).then(() => {
      ElMessage.success(text + "成功")
    }).catch(function () {
      row.status = row.status === "0" ? "1" : "0"
    })
  }

  // 删除按钮操作
  function handleDelete(row: any) {
    factoryStore.ids = [ row.roleId ]
    ElMessageBox.confirm('是否确认删除厂商编号为"' + factoryStore.ids + '"的数据项？', '系统提示', { type: 'warning' }).then(async () => {
      await factoryStore.delFactory()
      factoryStore.getFactoryData()
      ElMessage.success("删除成功")
    }).catch(() => {})
  }

  // 修改按钮操作
  async function handleUpdate(row: any) {
    factoryStore.resetFormData()
    await factoryStore.getFactoryInfo(row.roleId)
    factoryStore.formTitle = "修改厂商"
    factoryStore.showFormDialog = true
    emits('setMenuChecked')
  }  
</script>