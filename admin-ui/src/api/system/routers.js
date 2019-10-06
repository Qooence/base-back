import axios from '@/libs/api.request'

export const getRouter = () => {
  return axios.request({
    url: '/sys/route/nav',
    method: 'get'
  })
}

export const getTree = () => {
  return axios.request({
    url: '/sys/route/tree',
    method: 'get'
  })
}

export const getSelectTree = () => {
  return axios.request({
    url: '/sys/route/selectTree',
    method: 'get'
  })
}

export const save = route => {
  return axios.request({
    url: '/sys/route/save',
    method: 'post',
    data: route
  })
}

export const update = route => {
  return axios.request({
    url: '/sys/route/update',
    method: 'post',
    data: route
  })
}

export const deletes = roleIds => {
  return axios.request({
    url: '/sys/route/delete',
    method: 'post',
    data: roleIds
  })
}

export const getRouteById = routeId => {
  return axios.request({
    url: '/sys/route/info/' + routeId,
    method: 'get'
  })
}
