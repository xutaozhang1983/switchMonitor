/// <reference types="vite/client" />

declare module '*.vue' {
  import { DefineComponent } from 'vue';
  const component: DefineComponent<{}, {}, any>;
  export default component;
}

// 自定义基础路由对象属性
import { _RouteRecordBase } from 'vue-router'
declare module "vue-router" {
  interface _RouteRecordBase {
    hidden?: boolean,
    permissions?: string[]
  }
}

// 环境变量 TypeScript的智能提示
interface ImportMetaEnv {
  VITE_APP_TITLE: string;
  VITE_APP_PORT: string;
  VITE_APP_BASE_API: string;
}

interface ImportMeta {
  readonly env: ImportMetaEnv;
}
