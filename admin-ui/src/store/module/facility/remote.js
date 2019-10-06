import { getList } from '@/api/facility/remote'

const actions = {
  getRemoteList ({ commit }, data) {
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
