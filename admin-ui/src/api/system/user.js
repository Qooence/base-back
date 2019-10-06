import axios from '@/libs/api.request'

export const login = ({ username, password, uuid, captcha }) => {
  const data = {
    username,
    password,
    uuid,
    captcha
  }
  return axios.request({
    url: 'sys/login',
    data,
    method: 'post'
  })
}

export const getUserInfo = (token) => {
  return axios.request({
    url: '/sys/user/info',
    params: {
      token
    },
    method: 'get'
  })
}

export const logout = (token) => {
  return axios.request({
    url: '/sys/logout',
    method: 'post'
  })
}

export const getUnreadCount = () => {
  return axios.request({
    url: 'message/count',
    method: 'get'
  })
}

export const getMessage = () => {
  return axios.request({
    url: 'message/init',
    method: 'get'
  })
}

export const getContentByMsgId = msg_id => {
  return axios.request({
    url: 'message/content',
    method: 'get',
    params: {
      msg_id
    }
  })
}

export const hasRead = msg_id => {
  return axios.request({
    url: 'message/has_read',
    method: 'post',
    data: {
      msg_id
    }
  })
}

export const removeReaded = msg_id => {
  return axios.request({
    url: 'message/remove_readed',
    method: 'post',
    data: {
      msg_id
    }
  })
}

export const restoreTrash = msg_id => {
  return axios.request({
    url: 'message/restore',
    method: 'post',
    data: {
      msg_id
    }
  })
}

export const getCaptcha = uuid => {
  return axios.request({
    url: 'captcha.jpg',
    method: 'get',
    responseType: 'arraybuffer',
    params: {
      uuid
    }
  })
}

export const userList = data => {
  return axios.request({
    url: '/sys/user/list',
    method: 'post',
    data: data
  })
}

export const validateUsername = username => {
  return axios.request({
    url: '/sys/user/validate',
    method: 'get',
    params: {
      username
    }
  })
}

export const save = user => {
  return axios.request({
    url: '/sys/user/save',
    method: 'post',
    data: user
  })
}

export const update = user => {
  return axios.request({
    url: '/sys/user/update',
    method: 'post',
    data: user
  })
}

export const getUserById = userId => {
  return axios.request({
    url: '/sys/user/info/' + userId,
    method: 'get'
  })
}

export const deletes = userIds => {
  return axios.request({
    url: '/sys/user/delete',
    method: 'post',
    data: userIds
  })
}
