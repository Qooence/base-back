import { getList } from '@/api/system/log'

const actions = {
  getLogList ({ commit }, data) {
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
