import { inOf } from '@/libs/tools'
const hasPermission = {
  install (Vue, options) {
    Vue.directive('hasPermission', {
      bind (el, binding, vnode) {
        let permissionList = vnode.context.$store.state.router.permissions
        if (permissionList && permissionList.length && !inOf(permissionList, binding.value)) {
          el.parentNode.removeChild(el)
        }
      }
    })
  }
}

export default hasPermission
