import { treeList, save, update, deletes, getDictById, exist } from '@/api/system/dict'

const actions = {
  getDictTreeList ({ commit }) {
    return new Promise((resolve, reject) => {
      try {
        treeList().then(res => {
          resolve(res)
        }).catch(error => {
          reject(error)
        })
      } catch (error) {
        reject(error)
      }
    })
  },
  saveDict ({ commit }, dict) {
    return new Promise((resolve, reject) => {
      try {
        save(dict).then(res => {
          resolve(res)
        }).catch(error => {
          reject(error)
        })
      } catch (error) {
        reject(error)
      }
    })
  },
  updateDict ({ commit }, dict) {
    return new Promise((resolve, reject) => {
      try {
        update(dict).then(res => {
          resolve(res)
        }).catch(error => {
          reject(error)
        })
      } catch (error) {
        reject(error)
      }
    })
  },
  getDictById ({ commit }, { dictId }) {
    return new Promise((resolve, reject) => {
      try {
        getDictById(dictId).then(res => {
          resolve(res)
        }).catch(error => {
          reject(error)
        })
      } catch (error) {
        reject(error)
      }
    })
  },
  deleteDicts ({ commit }, { dictIds }) {
    return new Promise((resolve, reject) => {
      try {
        deletes(dictIds).then(res => {
          resolve(res)
        }).catch(error => {
          reject(error)
        })
      } catch (error) {
        reject(error)
      }
    })
  },
  existDict ({ commit }, data) {
    return new Promise((resolve, reject) => {
      try {
        exist(data).then(res => {
          resolve(res)
        }).catch(error => {
          reject(error)
        })
      } catch (error) {
        reject(error)
      }
    })
  }
}

export default {
  actions
}
