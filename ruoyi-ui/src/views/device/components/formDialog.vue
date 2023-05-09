<template>
  <el-dialog :title="deviceStore.formTitle" v-model="deviceStore.showFormDialog" width="800px" append-to-body>
    <el-form ref="deviceRef" :model="deviceStore.formData" :rules="rules" label-width="100px">
      <el-card header="基础信息" shadow="Never">
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="设备类型" prop="deviceType">
              <el-select v-model="deviceStore.formData.deviceType" placeholder="请选择" style="width:100%">
                <el-option v-for="(item, index) in sys_device_type" :key="index" :label="item.label" :value="item.label"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="设备名称" prop="deviceName">
              <el-input v-model="deviceStore.formData.deviceName" placeholder="请输入" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所在组" prop="groupId">
              <el-select v-model="deviceStore.formData.groupId" placeholder="请选择" style="width:100%">
                <el-option v-for="(item, index) in deviceStore.deviceGroupData" :key="index" :label="item.groupName" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="设备型号" prop="deviceModel">
              <el-input v-model="deviceStore.formData.deviceModel" placeholder="请输入" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="IP地址" prop="deviceIp">
              <el-input v-model="deviceStore.formData.deviceIp" placeholder="请输入" />
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="deviceStore.formData.deviceType === '网络设备'">
            <el-form-item label="接口数量" prop="portNum">
              <el-input-number v-model="deviceStore.formData.portNum" placeholder="请输入" :min="0" style="width:100%"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="厂商" prop="manufacturer">
              <el-select v-model="deviceStore.formData.manufacturer" placeholder="请选择" style="width:100%">
                <el-option v-for="(item, index) in sys_manufacturer" :key="index" :label="item.label" :value="item.label"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="是否开启监控">
              <el-radio-group v-model="deviceStore.formData.enable">
                <el-radio v-for="dict in sys_device_enable" :key="dict.value" :label="dict.value">{{ dict.label }}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注">
              <el-input v-model="deviceStore.formData.remark" type="textarea" placeholder="请输入内容"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-card>
      <el-card header="snmp信息：" shadow="Never" class="mt10">
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="团体名" prop="snmpCommunity">
              <el-input v-model="deviceStore.formData.snmpCommunity" placeholder="请输入" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="版本号" prop="snmpVersion">
              <el-select v-model="deviceStore.formData.snmpVersion" placeholder="请选择" style="width:100%">
                <el-option v-for="(item, index) in sys_snmp_version" :key="index" :label="item.label" :value="item.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="端口号" prop="snmpPort">
              <el-input v-model="deviceStore.formData.snmpPort" placeholder="请输入" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="用户名" prop="snmpUsername">
              <el-input v-model="deviceStore.formData.snmpUsername" placeholder="请输入" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="密码" prop="snmpPassword">
              <el-input v-model="deviceStore.formData.snmpPassword" placeholder="请输入" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-card>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
  import { ElMessage } from 'element-plus'
  import useDeviceStore from '../store'

  const { proxy } = getCurrentInstance() as any
  const deviceStore = useDeviceStore()

  const {
    sys_device_type,
    sys_manufacturer,
    sys_device_enable,
    sys_snmp_version }: {
    sys_device_type: DictType[],
    sys_manufacturer: DictType[],
    sys_device_enable: DictType[],
    sys_snmp_version: DictType[] } = proxy.useDict(
    "sys_device_type",
    "sys_manufacturer",
    "sys_device_enable",
    "sys_snmp_version"
  )
  
  const rules = {
    deviceName: [
      { required: true, message: "设备名称不能为空", trigger: "blur" },
    ],
    groupId: [
      { required: true, message: "请选择所在组", trigger: "change" },
    ],
    deviceIp: [
      { required: true, message: "ip地址不能为空", trigger: "blur" },
    ],
    snmpCommunity: [
      { required: true, message: "团体名不能为空", trigger: "blur" },
    ],
    snmpVersion: [
      { required: true, message: "请选择版本号", trigger: "change" },
    ],
    snmpPort: [
      { required: true, message: "端口号不能为空", trigger: "blur" },
    ]
  }
  
  // 提交按钮
  function submitForm() {
    proxy.$refs["deviceRef"].validate(async (valid: boolean) => {
      if (valid) {
        if (deviceStore.formData.id != undefined) {
          await deviceStore.updateDevice()
          ElMessage.success("修改成功")
          deviceStore.showFormDialog = false
          deviceStore.getDeviceData()
        } else {
          await deviceStore.addDevice()
          ElMessage.success("新增成功")
          deviceStore.showFormDialog = false
          deviceStore.getDeviceData()
        }
      }
    });
  }

  // 取消按钮
  function cancel() {
    deviceStore.resetFormData()
    deviceStore.showFormDialog = false
  }
</script>