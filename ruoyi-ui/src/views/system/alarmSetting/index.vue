<template>
  <div class="app-container">
    <el-tabs v-model="activeTab" type="card">
        <el-tab-pane label="邮件服务器" name="smtp">
          <Smtp />
        </el-tab-pane>
        <!--<el-tab-pane label="短信服务器" name="sms">
          <Sms />
        </el-tab-pane>-->
    </el-tabs>
  </div>
</template>

<script setup name="AlarmSetting" lang="ts">
  import Smtp from "./components/smtp.vue"
  import Sms from "./components/sms.vue"

  const activeTab = ref('smtp')

</script>
