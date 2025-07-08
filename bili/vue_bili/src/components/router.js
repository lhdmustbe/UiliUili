import {createRouter, createWebHashHistory} from "vue-router"
import VideoPage from './videoPage'
import indexPage from "@/components/indexPage";  // 直接导入或保持动态导入

const router = createRouter({
    history: createWebHashHistory(),
    routes: [
        {
            path: "/",
            component: indexPage,  // 或者保持 () => import('@/components/MainPage.vue')
        },
        {
            path: "/video/:id",
            name: "VideoPage",
            component: VideoPage,
            props: true
        },
    ]
})

export default router