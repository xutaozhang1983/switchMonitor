<template>
  <el-dialog title="操作日志详细" v-model="operLogStore.showDetailDialog" width="700px" append-to-body>
    <el-form :model="operLogStore.detailForm" label-width="100px">
      <el-row>
        <el-col :span="12">
          <el-form-item label="操作模块：">{{ operLogStore.detailForm.title }} / {{ typeFormat(operLogStore.detailForm) }}</el-form-item>
          <el-form-item label="登录信息：">{{ operLogStore.detailForm.operName }} / {{ operLogStore.detailForm.operIp }} / {{ operLogStore.detailForm.operLocation }}</el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="请求地址：">{{ operLogStore.detailForm.operUrl }}</el-form-item>
          <el-form-item label="请求方式：">{{ operLogStore.detailForm.requestMethod }}</el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="操作方法：">{{ operLogStore.detailForm.method }}</el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="请求参数：">{{ operLogStore.detailForm.operParam }}</el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="返回参数：">{{ operLogStore.detailForm.jsonResult }}</el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="操作状态：">
            <div v-if="operLogStore.detailForm.status === 0">正常</div>
            <div v-else-if="operLogStore.detailForm.status === 1">失败</div>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="操作时间：">{{ dayjs(operLogStore.detailForm.operTime).format('YYYY-MM-DD HH:mm:ss') }}</el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="异常信息：" v-if="operLogStore.detailForm.status === 1">{{ operLogStore.detailForm.errorMsg }}</el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="operLogStore.showDetailDialog = false">关 闭</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
  import useOperLogStore from '../store'
  import dayjs from 'dayjs'
  import { dictOperType } from '@/utils/dict'

  const { proxy } = getCurrentInstance() as any
  const operLogStore = useOperLogStore()

  /** 操作日志类型字典翻译 */
  function typeFormat(row: any) {
    return (dictOperType).filter((item: any) => item.value === row.businessType.toString())[0].label
  }
</script>