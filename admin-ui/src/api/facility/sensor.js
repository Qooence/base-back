import axios from '@/libs/api.request'

export const getList = data => {
  return axios.request({
    url: '/facility/sensor/list',
    method: 'post',
    data: data
  })
}
