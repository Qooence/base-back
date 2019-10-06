import axios from '@/libs/api.request'

export const getList = data => {
  return axios.request({
    url: '/facility/device/list',
    method: 'post',
    data: data
  })
}
