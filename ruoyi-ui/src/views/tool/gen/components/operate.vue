<template>
  <el-row :gutter="10" class="mb8">
    <el-col :span="1.5">
      <el-button type="primary" plain icon="Download" @click="handleGenTable"
        v-hasPermi="['tool:gen:code']">生成</el-button>
    </el-col>
    <el-col :span="1.5">
      <el-button type="info" plain icon="Upload" @click="openImportTable"
        v-hasPermi="['tool:gen:import']">导入</el-button>
    </el-col>
    <el-col :span="1.5">
      <el-button type="success" plain icon="Edit" :disabled="genStore.single" @click="handleEditTable"
        v-hasPermi="['tool:gen:edit']">修改</el-button>
    </el-col>
    <el-col :span="1.5">
      <el-button type="danger" plain icon="Delete" :disabled="genStore.multiple" @click="handleDelete"
        v-hasPermi="['tool:gen:remove']">删除</el-button>
    </el-col>
    <!--右侧工具栏-->
    <right-toolbar v-model:showSearch="genStore.showSearch" @queryTable="genStore.getGenData"></right-toolbar>
  </el-row>
</template>

<script setup lang="ts">
  import { ElMessageBox, ElMessage } from 'element-plus'
  import useGenStore from '../store'

  const { proxy } = getCurrentInstance() as any
  const router = useRouter()
  const genStore = useGenStore()

  const tableNames = ref([])

  /** 生成代码操作 */
  async function handleGenTable(row: any) {
    const tbNames = row.tableName || tableNames.value
    if (tbNames == '') {
      ElMessage.error('请选择要生成的数据')
      return
    }
    if (row.genType === '1') {
      await genStore.genCode(row.tableName)
      ElMessage.success('成功生成到自定义路径：' + row.genPath)
    } else {
      proxy.$download.zip('/tool/gen/batchGenCode?tables=' + tbNames, 'ruoyi')
    }
  }

  /** 打开导入表弹窗 */
  function openImportTable() {
    genStore.showImportDialog = true
  }

  /** 修改按钮操作 */
  function handleEditTable(row: any) {
    const tableId = row.tableId || genStore.ids[0]
    router.push({
      path: '/tool/genEdit/index/' + tableId,
      query: { pageNum: genStore.queryParams.pageNum }
    })
  }

  // 删除按钮操作
  function handleDelete() {
    ElMessageBox.confirm('是否确认删除表编号为"' + genStore.ids + '"的数据项？', '系统提示', { type: 'warning' }).then(async () => {
      await genStore.delGen()
      genStore.getGenData()
      ElMessage.success("删除成功")
    }).catch(() => {})
  }

</script>