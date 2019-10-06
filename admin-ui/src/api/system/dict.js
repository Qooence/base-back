import axios from '@/libs/api.request'

export const treeList = () => {
  return axios.request({
    url: '/sys/dict/tree',
    method: 'get'
  })
}

export const save = dict => {
  return axios.request({
    url: '/sys/dict/save',
    method: 'post',
    data: dict
  })
}

export const update = dict => {
  return axios.request({
    url: '/sys/dict/update',
    method: 'post',
    data: dict
  })
}

export const deletes = dictIds => {
  return axios.request({
    url: '/sys/dict/delete',
    method: 'post',
    data: dictIds
  })
}

export const getDictById = dictId => {
  return axios.request({
    url: '/sys/dict/info/' + dictId,
    method: 'get'
  })
}

export const exist = data => {
  return axios.request({
    url: '/sys/dict/exist',
    method: 'post',
    data: data
  })
}
