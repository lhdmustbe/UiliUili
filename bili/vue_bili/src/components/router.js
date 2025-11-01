import {createRouter, createWebHistory} from "vue-router"
import VideoPage from './videoPage'
import indexPage from "@/components/indexPage";
import SearchPage from "@/components/SearchPage";

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
        {
            path: "/search",
            name: "SearchPage",
            component: SearchPage,
            props: true
        },
    ]
})

export default router