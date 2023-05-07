<template>
  <el-dialog :title="userStore.uploadParams.title" v-model="userStore.uploadParams.open" width="400px" append-to-body>
      <el-upload
        ref="uploadRef"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="userStore.uploadParams.headers"
        :action="userStore.uploadParams.url + '?updateSupport=' + userStore.uploadParams.updateSupport"
        :disabled="userStore.uploadParams.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <el-icon class="el-icon--upload"><upload-filled /></el-icon>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <template #tip>
          <div class="el-upload__tip text-center">
            <div class="el-upload__tip">
              <el-checkbox v-model="userStore.uploadParams.updateSupport"/>是否更新已经存在的用户数据
            </div>
            <span>仅允许导入xls、xlsx格式文件。</span>
            <el-link type="primary" :underline="false" style="font-size: 12px; vertical-align: baseline" @click="importTemplate">下载模板</el-link>
          </div>
        </template>
      </el-upload>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitFileForm">确 定</el-button>
          <el-button @click="userStore.uploadParams.open = false">取 消</el-button>
        </div>
      </template>
    </el-dialog>
</template>

<script setup lang="ts">
  import useUserStore from '../store'

  const { proxy } = getCurrentInstance() as any
  const userStore = useUserStore()

  // 文件上传中处理
  const handleFileUploadProgress = (event: Event, file: string, fileList: string[]) => {
    userStore.uploadParams.isUploading = true
  };

  // 文件上传成功处理
  const handleFileSuccess = (response: any, file: string, fileList: string[]) => {
    userStore.uploadParams.open = false;
    userStore.uploadParams.isUploading = false;
    proxy.$refs["uploadRef"].handleRemove(file);
    proxy.$alert(
      "<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" +
        response.msg +
        "</div>",
      "导入结果",
      { dangerouslyUseHTMLString: true }
    );
    userStore.getUserData()
  };

   // 下载模板操作
   function importTemplate() {
    proxy.download(
      "system/user/importTemplate",
      {},
      `user_template_${new Date().getTime()}.xlsx`
    );
  }

  // 提交上传文件
  function submitFileForm() {
    proxy.$refs["uploadRef"].submit();
  }
</script>