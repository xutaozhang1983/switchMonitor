<template>
  <el-table v-loading="genStore.loading" :data="genStore.genData" @selection-change="handleSelectionChange">
    <el-table-column type="selection" align="center" width="55"></el-table-column>
    <el-table-column label="序号" type="index" width="50" align="center">
      <template #default="scope">
        <span>{{ (genStore.queryParams.pageNum - 1) * genStore.queryParams.pageSize + scope.$index + 1 }}</span>
      </template>
    </el-table-column>
    <el-table-column label="表名称" align="center" prop="tableName" :show-overflow-tooltip="true" />
    <el-table-column label="表描述" align="center" prop="tableComment" :show-overflow-tooltip="true" />
    <el-table-column label="实体" align="center" prop="className" :show-overflow-tooltip="true" />
    <el-table-column label="创建时间" align="center" prop="createTime" width="160" />
    <el-table-column label="更新时间" align="center" prop="updateTime" width="160" />
    <el-table-column label="操作" align="center" width="330" class-name="small-padding fixed-width">
      <template #default="scope">
        <el-tooltip content="预览" placement="top">
          <el-button type="text" icon="View" @click="handlePreview(scope.row)"
            v-hasPermi="['tool:gen:preview']"></el-button>
        </el-tooltip>
        <el-tooltip content="编辑" placement="top">
          <el-button type="text" icon="Edit" @click="handleEditTable(scope.row)"
            v-hasPermi="['tool:gen:edit']"></el-button>
        </el-tooltip>
        <el-tooltip content="删除" placement="top">
          <el-button type="text" icon="Delete" @click="handleDelete(scope.row)"
            v-hasPermi="['tool:gen:remove']"></el-button>
        </el-tooltip>
        <el-tooltip content="同步" placement="top">
          <el-button type="text" icon="Refresh" @click="handleSynchDb(scope.row)"
            v-hasPermi="['tool:gen:edit']"></el-button>
        </el-tooltip>
        <el-tooltip content="生成代码" placement="top">
          <el-button type="text" icon="Download" @click="handleGenTable(scope.row)"
            v-hasPermi="['tool:gen:code']"></el-button>
        </el-tooltip>
      </template>
    </el-table-column>
  </el-table>
  <!--数据分页-->
  <pagination
    v-show="genStore.total > 0"
    :total="genStore.total"
    v-model:page="genStore.queryParams.pageNum"
    v-model:limit="genStore.queryParams.pageSize"
    @pagination="genStore.getGenData" />
</template>

<script setup lang="ts">
  import { ElMessageBox, ElMessage } from 'element-plus'
  import useGenStore from '../store'

  const { proxy } = getCurrentInstance() as any
  const router = useRouter()
  const genStore = useGenStore()

  const tableNames = ref([])

  // 选择条数
  function handleSelectionChange(selection: any) {
    genStore.ids = selection.map((item: any) => item.tableId)
    genStore.single = selection.length != 1
    genStore.multiple = !selection.length
  }

  /** 预览按钮 */
  function handlePreview(row: any) {
    genStore.previewTable(row.tableId)
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
  function handleDelete(row: any) {
    genStore.ids = [ row.genId ]
    ElMessageBox.confirm('是否确认删除表编号为"' + genStore.ids + '"的数据项？', '系统提示', { type: 'warning' }).then(async () => {
      await genStore.delGen()
      genStore.getGenData()
      ElMessage.success("删除成功")
    }).catch(() => {})
  }

  /** 同步数据库操作 */
  function handleSynchDb(row: any) {
    const tableName = row.tableName
    ElMessageBox.confirm('确认要强制同步"' + tableName + '"表结构吗？', '系统提示', { type: 'warning' }).then(async () => {
      await genStore.synchDb(tableName)
      ElMessage.success("同步成功")
    }).catch(() => {})
  }

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
</script>