import { getList } from '@/api/facility/sensor'

const actions = {
  getSensorList ({ commit }, data) {
    return new Promise((resolve, reject) => {
      getList(data).then(res => {
        resolve(res)
      }).catch(error => {
        reject(error)
      })
    })
  }
}

export default {
  actions
}
