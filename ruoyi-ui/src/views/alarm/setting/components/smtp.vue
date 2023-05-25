<template>
  <el-row>
    <el-col :span="6"></el-col>
    <el-col :span="12">
      <el-form ref="smtpRef" :model="formData" :rules="rules" label-width="80px">
        <el-form-item label="服务器名" prop="host">
          <el-input v-model="formData.host" placeholder="请输入" :disabled="formData.status === '1'" />
        </el-form-item>
        <el-form-item label="端口" prop="port">
          <el-input v-model="formData.port" placeholder="请输入" :disabled="formData.status === '1'" />
        </el-form-item>
        <el-form-item label="发送邮箱" prop="sender">
          <el-input v-model="formData.sender" placeholder="请输入" :disabled="formData.status === '1'" />
        </el-form-item>
        <el-form-item label="用户名" prop="username">
          <el-input v-model="formData.username" placeholder="请输入" :disabled="formData.status === '1'" />
        </el-form-item>
        <el-form-item label="密码" prop="passwd">
          <el-input v-model="formData.passwd" type="password" placeholder="请输入" :disabled="formData.status === '1'" />
        </el-form-item>
        <el-form-item label="安全连接">
          <el-radio-group v-model="secureLink" @change="handleSecureLink" :disabled="formData.status === '1'">
            <el-radio label="0">无</el-radio>
            <el-radio label="1">启用SSL</el-radio>
            <el-radio label="2">启用TLS</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="formData.status">
            <el-radio label="0">启用</el-radio>
            <el-radio label="1">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div class="footer">
        <el-button type="success" style="margin-right: 20px" :disabled="sendMailDisabled || formData.status === '1'" @click="handleShowSendMail">发送测试电子邮件</el-button>
        <el-button type="primary" :loading="btnLoading" :disabled="formData.status === '1'" @click="submitForm">保存</el-button>
      </div>
    </el-col>
    <el-col :span="6"></el-col>
  </el-row>
  <!--测试邮件对话框-->
  <el-dialog title="电子邮件测试" v-model="showSendMailDialog" width="500px" append-to-body :show-close="false">
    <el-form :model="sendMailFormData" ref="sendMailRef" :rules="rules">
      <el-form-item label="接收邮箱" prop="receive">
        <el-input v-model="sendMailFormData.receive" placeholder="请输入" style="width:100%"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button type="primary" :loading="btnLoading" @click="handleSendMail">确 定</el-button>
        <el-button @click="handleCloseSendMail">取 消</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup lang=ts>
  import { ElMessage } from 'element-plus'
  import { getAlarmMedia, updateAlarmMedia, sendTestMail } from '@/api/system/alarmSetting'
  import { AlarmFormData } from '@/types/api/system/alarmSetting'

  const { proxy } = getCurrentInstance() as any

  const btnLoading = ref(false)
  const sendMailDisabled = ref(false)
  const secureLink = ref('0')
  const showSendMailDialog = ref(false)

  const formData = ref<AlarmFormData>({
    mediaType: 'EMAIL',
    enableSsl: '0',
    enableTls: '0',
    status: '1'
  })

  const sendMailFormData = ref({
    mediaType: 'EMAIL',
    receive: ''
  })

  const rules = ref({
    host: [
      { required: true, message: '服务器名不能为空', trigger: 'blur' }
    ],
    port: [
      { required: true, message: '端口不能为空', trigger: 'blur' }
    ],
    sender: [
      { required: true, message: '发送邮箱不能为空', trigger: 'blur' },
      { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
    ],
    username: [
      { required: true, message: '用户名不能为空', trigger: 'blur' }
    ],
    passwd: [
      { required: true, message: '密码不能为空', trigger: 'blur' }
    ],
    receive: [
    { required: true, message: '接收邮箱不能为空', trigger: 'blur' }
    ]
  })

  // 显示发送邮件对话框
  function handleShowSendMail () {
    sendMailFormData.value.receive = ''
    showSendMailDialog.value = true
  }

  // 关闭发送邮件对话框
  function handleCloseSendMail () {
    showSendMailDialog.value = false
  }

  // 发送测试邮件
  function handleSendMail () {
    proxy.$refs["sendMailRef"].validate(async (valid: boolean) => {
      if (valid) {
        btnLoading.value = true
        sendTestMail(sendMailFormData.value).then(() => {
          btnLoading.value = false
          ElMessage.success("发送成功")
          showSendMailDialog.value = false
        })
      }
    })
  }

  // 更换安全链接
  function handleSecureLink (val: string) {
    formData.value.enableSsl = '0'
    formData.value.enableTls = '0'
    if (val === '1') {
      formData.value.enableSsl = '1'
    } else if (val === '2') {
      formData.value.enableTls = '1'
    }
  }

  // 查询介质详情数据
  function getAlarmMediaData () {
    getAlarmMedia('EMAIL').then((response: any) => {
      if (response.data) {
        formData.value = response.data
        if (formData.value.enableSsl === '1') secureLink.value = '1'
        if (formData.value.enableTls === '1') secureLink.value = '2'
      }
    })
  }

  // 提交按钮
  function submitForm() {
    proxy.$refs["smtpRef"].validate(async (valid: boolean) => {
      if (valid) {
        btnLoading.value = true
        sendMailDisabled.value = true
        updateAlarmMedia(formData.value).then(() => {
          sendMailDisabled.value = false
          btnLoading.value = false
          ElMessage.success("保存成功")
        })
      }
    })
  }

  getAlarmMediaData()

</script>

<style lang="scss" scoped>
  .footer {
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    margin-top: 30px;
  }
</style>