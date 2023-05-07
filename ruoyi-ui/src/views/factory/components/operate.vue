<template>
  <el-row :gutter="10" class="mb8">
    <el-col :span="1.5">
      <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['system:role:add']" >新增</el-button>
    </el-col>
    <el-col :span="1.5">
      <el-button type="success" plain icon="Edit" :disabled="factoryStore.single" @click="handleUpdate" v-hasPermi="['system:role:edit']">修改</el-button>
    </el-col>
    <el-col :span="1.5">
      <el-button type="danger" plain icon="Delete" :disabled="factoryStore.multiple" @click="handleDelete" v-hasPermi="['system:role:remove']">删除</el-button>
    </el-col>
    <el-col :span="1.5">
      <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['system:role:export']">导出</el-button>
    </el-col>
    <!--右侧工具条-->
    <right-toolbar v-model:showSearch="factoryStore.showSearch" @queryTable="factoryStore.getFactoryData"></right-toolbar>
  </el-row>
</template>

<script setup lang="ts">
  import { ElMessageBox, ElMessage } from 'element-plus'
  import useFactoryStore from '../store'

  const { proxy } = getCurrentInstance() as any
  const factoryStore = useFactoryStore()
  const emits = defineEmits(['setMenuChecked'])

  // 新增按钮操作
  async function handleAdd() {
    factoryStore.resetFormData()
    factoryStore.showFormDialog = true
    factoryStore.formTitle = "添加厂商"
  }

  // 修改按钮操作
  async function handleUpdate() {
    factoryStore.resetFormData()
    await factoryStore.getFactoryInfo(factoryStore.ids[0])
    factoryStore.formTitle = "修改厂商"
    factoryStore.showFormDialog = true
    emits('setMenuChecked')
  }

  // 删除按钮操作
  function handleDelete() {
    ElMessageBox.confirm('是否确认删除厂商编号为"' + factoryStore.ids + '"的数据项？', '系统提示', { type: 'warning' }).then(async () => {
      await factoryStore.delFactory()
      factoryStore.getFactoryData()
      ElMessage.success("删除成功")
    }).catch(() => {})
  }

  // 导出按钮操作
  function handleExport() {
    proxy.download(
      "system/role/export",
      {
        ...factoryStore.queryParams,
      },
      `role_${new Date().getTime()}.xlsx`
    );
  }

</script>