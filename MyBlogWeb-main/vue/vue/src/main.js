import Vue from 'vue'
import App from './App.vue'
import router from './router'
import './assets/global.css'

import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
Vue.use(mavonEditor);

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI, {size:'mini'});

import request from "@/utils/request";
Vue.prototype.request = request

Vue.config.productionTip = false

new Vue({
  router,
  beforeCreate(){
    Vue.prototype.$bus = this
    Vue.prototype.gotoUrl="http://localhost:8090"
  },
  render: h => h(App)
}).$mount('#app')
