import { localStorage } from '@/utils/storage'

const useAppStore = defineStore('app', {
  state: () => ({
    device: 'desktop',
    showLicense: false,
    size: localStorage.get('size') || 'default',
    sidebar: {
      opened: localStorage.get('sidebarStatus')
        ? !!+localStorage.get('sidebarStatus')
        : true,
      withoutAnimation: false,
      hide: false
    }
  }),
  actions: {
    toggleSideBar(withoutAnimation: boolean) {
      if (this.sidebar.hide) {
        return false
      }
      this.sidebar.opened = !this.sidebar.opened
      this.sidebar.withoutAnimation = withoutAnimation
      if (this.sidebar.opened) {
        localStorage.set('sidebarStatus', 1)
      } else {
        localStorage.set('sidebarStatus', 0)
      }
    },
    closeSideBar({ withoutAnimation }: { withoutAnimation: boolean }) {
      localStorage.set('sidebarStatus', 0)
      this.sidebar.opened = false
      this.sidebar.withoutAnimation = withoutAnimation
    },
    toggleDevice(device: string) {
      this.device = device
    },
    setSize(size: string) {
      this.size = size
      localStorage.set('size', size)
    },
    toggleSideBarHide(status: boolean) {
      this.sidebar.hide = status
    }
  }
})

export default useAppStore
