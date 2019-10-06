import Vue from 'vue'
import Router from 'vue-router'
import { commonRoute, notFoundRoute } from './routers'
import store from '@/store'
import iView from 'iview'
import { getToken, setTitle, setToken } from '@/libs/util'
import config from '@/config'
const { homeName } = config
Vue.use(Router)

const router = new Router({
  // hash
  // routes: commonRoute
  // history
  routes: commonRoute,
  // base: process.env.NODE_ENV === 'production' ? '/smarthome/page/' : '/',
  mode: 'hash'
})
const LOGIN_PAGE_NAME = 'login'

/**
 * 保存到localstrotge 可能会用到
 */
// router.onReady(() => {
//   console.log(store.state.router.routers)
//   router.options.routes = store.state.router.routers
// })

router.beforeEach((to, from, next) => {
  iView.LoadingBar.start()
  const token = getToken()
  if (!token && to.name !== LOGIN_PAGE_NAME) {
    // 未登录且要跳转的页面不是登录页
    next({
      name: LOGIN_PAGE_NAME // 跳转到登录页
    })
  } else if (!token && to.name === LOGIN_PAGE_NAME) {
    // 未登陆且要跳转的页面是登录页
    next() // 跳转
  } else if (token && to.name === LOGIN_PAGE_NAME) {
    // 已登录且要跳转的页面是登录页
    next({
      name: homeName // 跳转到homeName页
    })
  } else {
    if (!store.state.router.hasGetRules) {
      store.dispatch('refreshRoutes').then(res => {
        // 保存权限
        store.dispatch('setPermissions', res.data.permissions)
        // 1.获取路由列表
        store.dispatch('concatRoutes', res.data.routeList).then((routers) => {
          // 2.合并路由列表
          router.addRoutes(routers)
          router.addRoutes(notFoundRoute)
          // 左侧菜单加载router.options.routes 从这里赋值
          router.options.routes = routers
          store.dispatch('getUserInfo').then(() => {
            next({ ...to,
              replace: true
            })
          }).catch(() => {
            setToken('')
            next({
              name: 'login'
            })
          })
        })
      }).catch(() => {
        setToken('')
        next({
          name: 'login'
        })
      })
    } else {
      next()
    }
  }
})

router.afterEach(to => {
  setTitle(to, router.app)
  iView.LoadingBar.finish()
  window.scrollTo(0, 0)
})

export default router
