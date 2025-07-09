import {createRouter, createWebHistory} from "vue-router"
import VideoPage from './videoPage'
import indexPage from "@/components/indexPage";  // 直接导入或保持动态导入

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: "/",
            component: indexPage,
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