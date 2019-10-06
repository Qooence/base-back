import axios from '@/libs/api.request'

export const getList = data => {
  return axios.request({
    url: '/facility/alarm/list',
    method: 'post',
    data: data
  })
}
