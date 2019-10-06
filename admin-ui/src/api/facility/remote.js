import axios from '@/libs/api.request'

export const getList = data => {
  return axios.request({
    url: '/facility/remote/list',
    method: 'post',
    data: data
  })
}
