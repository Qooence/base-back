import {
  getRoleSelect,
  getList,
  save,
  update,
  getRoleById,
  deletes
} from '@/api/system/role'

const state = {
  //
}

const mutations = {
  //
}

const actions = {
  getRoleSelect () {
    return new Promise((resolve, reject) => {
      getRoleSelect().then(res => {
        resolve(res)
      }).catch(err => {
        reject(err)
      })
    })
  },
  getRoleList ({ commit }, data) {
    return new Promise((resolve, reject) => {
      getList(data).then(res => {
        resolve(res)
      }).catch(error => {
        reject(error)
      })
    })
  },
  saveRole ({ commit }, role) {
    return new Promise((resolve, reject) => {
      try {
        save(role).then(res => {
          resolve(res)
        }).catch(error => {
          reject(error)
        })
      } catch (error) {
        reject(error)
      }
    })
  },
  updateRole ({ commit }, role) {
    return new Promise((resolve, reject) => {
      try {
        update(role).then(res => {
          resolve(res)
        }).catch(error => {
          reject(error)
        })
      } catch (error) {
        reject(error)
      }
    })
  },
  getRoleById ({ commit }, { roleId }) {
    return new Promise((resolve, reject) => {
      try {
        getRoleById(roleId).then(res => {
          resolve(res)
        }).catch(error => {
          reject(error)
        })
      } catch (error) {
        reject(error)
      }
    })
  },
  deleteRoles ({ commit }, { roleIds }) {
    return new Promise((resolve, reject) => {
      try {
        deletes(roleIds).then(res => {
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
  state,
  mutations,
  actions
}
