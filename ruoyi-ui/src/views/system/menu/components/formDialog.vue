<template>
  <el-dialog :title="menuStore.formTitle" v-model="menuStore.showFormDialog" width="680px" append-to-body>
    <el-form ref="menuRef" :model="menuStore.formData" :rules="rules" label-width="100px">
      <el-row>
        <el-col :span="24">
          <el-form-item label="上级菜单">
            <el-tree-select
              v-model="menuStore.formData.parentId"
              :data="menuStore.getMenuTree"
              :props="{ value: 'menuId', label: 'menuName', children: 'children' }"
              value-key="menuId"
              placeholder="选择上级菜单"
              check-strictly/>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="菜单类型" prop="menuType">
            <el-radio-group v-model="menuStore.formData.menuType">
              <el-radio label="M">目录</el-radio>
              <el-radio label="C">菜单</el-radio>
              <el-radio label="F">按钮</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="24" v-if="menuStore.formData.menuType != 'F'">
          <el-form-item label="菜单图标" prop="icon">
            <el-popover
              placement="bottom-start"
              :width="540"
              v-model:visible="showChooseIcon"
              trigger="click"
              @show="showSelectIcon">
              <template #reference>
                <el-input v-model="menuStore.formData.icon" placeholder="点击选择图标" @click="showSelectIcon" v-click-outside="hideSelectIcon" readonly>
                  <template #prefix>
                    <svg-icon v-if="menuStore.formData.icon" :icon-class="menuStore.formData.icon" class="el-input__icon" style="height: 32px;width: 16px;"/>
                    <el-icon v-else style="height: 32px;width: 16px;"><search /></el-icon>
                  </template>
                </el-input>
              </template>
              <icon-select ref="iconSelectRef" @selected="selected" />
            </el-popover>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="菜单名称" prop="menuName">
            <el-input v-model="menuStore.formData.menuName" placeholder="请输入菜单名称" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="显示排序" prop="orderNum">
            <el-input-number v-model="menuStore.formData.orderNum" controls-position="right" :min="0" />
          </el-form-item>
        </el-col>
        <el-col :span="12" v-if="menuStore.formData.menuType != 'F'">
          <el-form-item>
            <template #label>
              <span>
                <el-tooltip content="选择是外链则路由地址需要以`http(s)://`开头" placement="top">
                  <el-icon><question-filled /></el-icon>
                </el-tooltip>是否外链
              </span>
            </template>
            <el-radio-group v-model="menuStore.formData.isFrame">
              <el-radio label="0">是</el-radio>
              <el-radio label="1">否</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="12" v-if="menuStore.formData.menuType != 'F'">
          <el-form-item prop="path">
            <template #label>
              <span>
                <el-tooltip content="访问的路由地址，如：`user`，如外网地址需内链访问则以`http(s)://`开头" placement="top">
                  <el-icon><question-filled /></el-icon>
                </el-tooltip>
                路由地址
              </span>
            </template>
            <el-input v-model="menuStore.formData.path" placeholder="请输入路由地址" />
          </el-form-item>
        </el-col>
        <el-col :span="12" v-if="menuStore.formData.menuType == 'C'">
          <el-form-item prop="component">
            <template #label>
              <span>
                <el-tooltip content="访问的组件路径，如：`system/user/index`，默认在`views`目录下" placement="top">
                  <el-icon><question-filled /></el-icon>
                </el-tooltip>
                组件路径
              </span>
            </template>
            <el-input v-model="menuStore.formData.component" placeholder="请输入组件路径" />
          </el-form-item>
        </el-col>
        <el-col :span="12" v-if="menuStore.formData.menuType != 'M'">
          <el-form-item>
              <el-input v-model="menuStore.formData.perms" placeholder="请输入权限标识" maxlength="100" />
              <template #label>
                <span>
                  <el-tooltip content="控制器中定义的权限字符，如：@PreAuthorize(`@ss.hasPermi('system:user:list')`)" placement="top">
                    <el-icon><question-filled /></el-icon>
                  </el-tooltip>
                  权限字符
                </span>
              </template>
          </el-form-item>
        </el-col>
        <el-col :span="12" v-if="menuStore.formData.menuType == 'C'">
          <el-form-item>
              <el-input v-model="menuStore.formData.query" placeholder="请输入路由参数" maxlength="255" />
              <template #label>
                <span>
                  <el-tooltip content='访问路由的默认传递参数，如：`{"id": 1, "name": "ry"}`' placement="top">
                    <el-icon><question-filled /></el-icon>
                  </el-tooltip>
                  路由参数
                </span>
              </template>
          </el-form-item>
        </el-col>
        <el-col :span="12" v-if="menuStore.formData.menuType == 'C'">
          <el-form-item>
              <template #label>
                <span>
                  <el-tooltip content="选择是则会被`keep-alive`缓存，需要匹配组件的`name`和地址保持一致" placement="top">
                    <el-icon><question-filled /></el-icon>
                  </el-tooltip>
                  是否缓存
                </span>
              </template>
              <el-radio-group v-model="menuStore.formData.isCache">
                <el-radio label="0">缓存</el-radio>
                <el-radio label="1">不缓存</el-radio>
              </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="12" v-if="menuStore.formData.menuType != 'F'">
          <el-form-item>
              <template #label>
                <span>
                  <el-tooltip content="选择隐藏则路由将不会出现在侧边栏，但仍然可以访问" placement="top">
                    <el-icon><question-filled /></el-icon>
                  </el-tooltip>
                  显示状态
                </span>
              </template>
              <el-radio-group v-model="menuStore.formData.visible">
                <el-radio
                  v-for="dict in sys_show_hide"
                  :key="dict.value"
                  :label="dict.value"
                >{{ dict.label }}</el-radio>
              </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="12" v-if="menuStore.formData.menuType != 'F'">
          <el-form-item>
              <template #label>
                <span>
                  <el-tooltip content="选择停用则路由将不会出现在侧边栏，也不能被访问" placement="top">
                    <el-icon><question-filled /></el-icon>
                  </el-tooltip>
                  菜单状态
                </span>
              </template>
              <el-radio-group v-model="menuStore.formData.status">
                <el-radio
                  v-for="dict in sys_normal_disable"
                  :key="dict.value"
                  :label="dict.value"
                >{{ dict.label }}</el-radio>
              </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
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
  import { ElMessage, ElTree } from 'element-plus'
  import SvgIcon from '@/components/SvgIcon/index.vue'
  import IconSelect from '@/components/IconSelect/index.vue'
  import { ClickOutside as vClickOutside } from 'element-plus'
  import useMenuStore from '../store'

  const { proxy } = getCurrentInstance() as any
  const menuStore = useMenuStore()

  const menuRef = ref<InstanceType<typeof ElTree>>()
  const { sys_normal_disable, sys_show_hide }: 
    { sys_normal_disable: DictType[], sys_show_hide: DictType[] } = proxy.useDict(
    "sys_normal_disable",
    "sys_show_hide"
  )

  const showChooseIcon = ref(false)
  const iconSelectRef = ref<InstanceType<typeof IconSelect> | null>(null)
  
  const rules = {
    menuName: [
      { required: true, message: '菜单名称不能为空', trigger: 'blur' }
    ],
    orderNum: [
      { required: true, message: '菜单顺序不能为空', trigger: 'blur' }
    ],
    path: [{ required: true, message: '路由地址不能为空', trigger: 'blur' }]
  }

  /** 展示下拉图标 */
  function showSelectIcon() {
    iconSelectRef.value!.reset()
    showChooseIcon.value = true
  }
  /** 选择图标 */
  function selected(name: string) {
    menuStore.formData.icon = name
    showChooseIcon.value = false
  }
  /** 图标外层点击隐藏下拉列表 */
  function hideSelectIcon(event: Event) {
    var className = (event.target as HTMLElement).className
    if (className !== 'el-input__inner') {
      showChooseIcon.value = false
    }
  }
  
  // 提交按钮
  function submitForm() {
    proxy.$refs["menuRef"].validate(async (valid: boolean) => {
      if (valid) {
        if (menuStore.formData.menuId != undefined) {
          await menuStore.updateMenu()
          ElMessage.success("修改成功")
          menuStore.showFormDialog = false
          menuStore.getMenuData()
        } else {
          await menuStore.addMenu()
          ElMessage.success("新增成功")
          menuStore.showFormDialog = false
          menuStore.getMenuData()
        }
      }
    })
  }

  // 取消按钮
  function cancel() {
    menuStore.resetFormData()
    menuStore.showFormDialog = false
  }
</script>