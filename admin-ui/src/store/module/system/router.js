import { commonRoute, notFoundRoute, dynamicRoute } from '@/router/routers'
import {
  getRouter,
  getTree,
  getSelectTree,
  save,
  update,
  deletes,
  getRouteById
} from '@/api/system/routers'
import Main from '@/components/main'
import parentView from '@/components/parent-view'
// 开发环境不使用懒加载, 因为懒加载页面太多的话会造成webpack热更新太慢, 所以只有生产环境使用懒加载
const _import = require('@/router/import-' + process.env.NODE_ENV)

/**
 * URL地址
 * @param {*} s
 */
// const isURL = s => {
//   return /^http[s]?:\/\/.*/.test(s)
// }
/**
 * 添加动态(菜单)路由
 * @param {*} routeList 菜单)路由列表
 * @param {*} parentRoute 上级(菜单)路由
 */
const DynamicAddRoutes = (routeList = [], parentRoute) => {
  routeList.forEach(route => {
    let menu = {
      path: route.path,
      name: route.name,
      children: [],
      component: null,
      meta: {
        icon: route.icon || null,
        title: route.title,
        hideInMenu: route.hideInMenu === 1,
        iframeUrl: ''
      }
    }
    if (route.children.length <= 0) {
      delete menu.children
    }
    if (parentRoute) {
      try {
        if (route.componentPath === 'parentView') {
          menu['component'] = parentView
        // } else if (isURL(route.componentPath)) {// iframe 来展示外部页面貌似行不通
        //   menu['path'] = `i-${route.id}`
        //   menu['name'] = `i-${route.id}`
        //   menu['meta']['iframeUrl'] = route.componentPath
        } else {
          menu['component'] = _import(`${route.componentPath}`) || null
        }
      } catch (e) {}
      parentRoute.children.push(menu)
    } else {
      menu['component'] = Main
      dynamicRoute.push(menu)
    }
    if (route.children && route.children.length > 0) {
      DynamicAddRoutes(route.children, menu)
    }
  })
}

const state = {
  routers: commonRoute.concat(notFoundRoute),
  hasGetRules: false,
  permissions: []
}

const mutations = {
  CONCAT_ROUTES (state, routerList) {
    state.routers = commonRoute.concat(routerList.concat(notFoundRoute))
    state.hasGetRules = true
  },
  CLEAR_ROUTE (state) {
    dynamicRoute.length = 0
    state.routers.length = 0
    state.hasGetRules = false
  },
  SET_PERMISSIONS (state, permissions) {
    state.permissions = permissions
  }
}
const actions = {
  refreshRoutes ({ commit }) {
    return new Promise((resolve, reject) => {
      try {
        getRouter().then(res => {
          resolve(res)
        }).catch(error => {
          reject(error)
        })
      } catch (error) {
        reject(error)
      }
    })
  },
  getRouteTree ({ commit }) {
    return new Promise((resolve, reject) => {
      try {
        getTree().then(res => {
          resolve(res)
        }).catch(error => {
          reject(error)
        })
      } catch (error) {
        reject(error)
      }
    })
  },
  getRouteSelectTree ({ commit }) {
    return new Promise((resolve, reject) => {
      try {
        getSelectTree().then(res => {
          resolve(res)
        }).catch(error => {
          reject(error)
        })
      } catch (error) {
        reject(error)
      }
    })
  },
  saveRoute ({ commit }, route) {
    return new Promise((resolve, reject) => {
      try {
        save(route).then(res => {
          resolve(res)
        }).catch(error => {
          reject(error)
        })
      } catch (error) {
        reject(error)
      }
    })
  },
  updateRoute ({ commit }, route) {
    return new Promise((resolve, reject) => {
      try {
        update(route).then(res => {
          resolve(res)
        }).catch(error => {
          reject(error)
        })
      } catch (error) {
        reject(error)
      }
    })
  },
  getRouteById ({ commit }, { routeId }) {
    return new Promise((resolve, reject) => {
      try {
        getRouteById(routeId).then(res => {
          resolve(res)
        }).catch(error => {
          reject(error)
        })
      } catch (error) {
        reject(error)
      }
    })
  },
  deleteRoutes ({ commit }, { routeIds }) {
    return new Promise((resolve, reject) => {
      try {
        deletes(routeIds).then(res => {
          resolve(res)
        }).catch(error => {
          reject(error)
        })
      } catch (error) {
        reject(error)
      }
    })
  },
  concatRoutes ({ commit }, rules) {
    return new Promise((resolve, reject) => {
      try {
        DynamicAddRoutes(rules)
        commit('CONCAT_ROUTES', dynamicRoute)
        resolve(state.routers)
      } catch (err) {
        reject(err)
      }
    })
  },
  setPermissions ({ commit }, permissions) {
    commit('SET_PERMISSIONS', permissions)
  }
}

export default {
  state,
  mutations,
  actions
}
