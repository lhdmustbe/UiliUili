import {createApp} from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from './App.vue'
import router from "./components/router"
import axios from 'axios'

const app = createApp(App)

app.config.globalProperties.$httpUrl = 'http://localhost:8081'
app.config.globalProperties.$axios = axios
axios.interceptors.request.use(config => {
    const token = localStorage.getItem('token')
    if (token) {
        config.headers.Authorization = `Bearer ${token}`
    }
    return config
})

app.use(router)
app.use(ElementPlus)

import * as ElementPlusIconsVue from '@element-plus/icons-vue'

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

app.mount('#app')
