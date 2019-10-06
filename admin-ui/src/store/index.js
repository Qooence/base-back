import Vue from 'vue'
import Vuex from 'vuex'

import user from './module/system/user'
import app from './module/system/app'
import router from './module/system/router'
import role from './module/system/role'
import dict from './module/system/dict'
import log from './module/system/log'
import device from './module/facility/device'
import remote from './module/facility/remote'
import sensor from './module/facility/sensor'
import zone from './module/facility/zone'
import alarm from './module/facility/alarm'
// import saveInLocal from './plugin/saveInLocal'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    //
  },
  mutations: {
    //
  },
  actions: {
    //
  },
  modules: {
    user,
    app,
    router,
    role,
    dict,
    log,
    device,
    remote,
    sensor,
    zone,
    alarm
  }// ,
  // plugins: [ saveInLocal ]
})
