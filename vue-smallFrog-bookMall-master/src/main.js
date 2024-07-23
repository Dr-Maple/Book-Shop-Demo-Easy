// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import App from './App'
import router from './router'
import axios from 'axios'
import VueCookies from 'vue-cookies'
import 'vue-area-linkage/dist/index.css'
import VueAreaLinkage from 'vue-area-linkage'
import store from './store'

Vue.use(VueCookies)
Vue.use(ElementUI)
Vue.use(VueAreaLinkage)

Vue.config.productionTip = false
Vue.prototype.$bus = new Vue();

// 设置反向代理，前端请求默认发送到 http://localhost:8625/api
// var axios = require('axios')
axios.defaults.baseURL = '/api/'

// 全局注册，之后可在其他组件中通过 this.$axios 发送数据
// Vue.prototype.$axios = axios
Vue.prototype.axios = axios

router.beforeEach((to, from, next) => {
    document.title =  'Maple SpringBoot'
    const role = VueCookies.get('user');
    if (to.meta.permission && !role && (to.path == '/shopping/cart' || to.path == '/shopping/settle' || to.path == '/order' || to.path == '/setting')) {
        next('/403'); 
    } else {
        next();
    }
})

/* eslint-disable no-new */
new Vue({
    el: '#app',
    router,
    store,
    components: {
        App
    },
    template: '<App/>'
})