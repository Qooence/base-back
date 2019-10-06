import axios from '@/libs/api.request'

export const getRoleSelect = () => {
  return axios.request({
    url: '/sys/role/listForSelect',
    method: 'get'
  })
}

export const getList = data => {
  return axios.request({
    url: '/sys/role/list',
    method: 'post',
    data: data
  })
}

export const save = role => {
  return axios.request({
    url: '/sys/role/save',
    method: 'post',
    data: role
  })
}

export const update = role => {
  return axios.request({
    url: '/sys/role/update',
    method: 'post',
    data: role
  })
}

export const getRoleById = roleId => {
  return axios.request({
    url: '/sys/role/info/' + roleId,
    method: 'get'
  })
}

export const deletes = roleIds => {
  return axios.request({
    url: '/sys/role/delete',
    method: 'post',
    data: roleIds
  })
}
