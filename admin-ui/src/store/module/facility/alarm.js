import { getList } from '@/api/facility/alarm'

const actions = {
  getAlarmList ({ commit }, data) {
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
