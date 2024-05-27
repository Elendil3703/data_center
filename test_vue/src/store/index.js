import Vue from 'vue'
import Vuex from 'vuex'
 
Vue.use(Vuex)
 
export default new Vuex.Store({
  //数据，相当于data
  state: {
    activeIndex: '2'
  },
  getters: {
    
  },
  //里面定义方法，操作state方发
  mutations: {
    setActiveIndex(state, index) {
        state.activeIndex = index;
      }
  },
  // 操作异步操作mutation
  actions: {
    updateActiveIndex({ commit }, index) {
        commit('setActiveIndex', index);
      }
  },
  modules: {
    
  },
})