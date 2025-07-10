<template>
  <div class="main-page">
    <div class="gundong_box">
      <div class="gundong" @mouseenter="stopAutoPlay" @mouseleave="startAutoPlay">
        <router-link
            :to="{ name: 'VideoPage', params: { id: currentVideo.id }}"
            style="width: 616px;height: 430.25px; display: block; position: relative;"
        >
          <transition name="fade" mode="out-in">
            <img
                :key="currentIndex"
                :src="currentVideo.img"
                style="width: 100%; height: 100%; object-fit: cover;"
                alt="轮播图"
            >
          </transition>
          <span
              style="position: absolute;bottom: 10%;margin-left: 15px;text-decoration: none;color: white ;font-weight: bold;font-size: 20px;width: 516px"
          >
            {{ currentVideo.title }}
          </span>
        </router-link>

        <!-- 导航按钮 -->
        <button class="carousel-btn prev" @click="prevSlide">‹</button>
        <button class="carousel-btn next" @click="nextSlide">›</button>

        <!-- 指示器 -->
        <div class="carousel-indicators">
          <span
              v-for="(item, index) in mainVideo"
              :key="index"
              :class="{ active: currentIndex === index }"
              @click="goToSlide(index)"
          ></span>
        </div>
      </div>

      <div class="gundong_right">
        <router-link
            v-for="video in videoList"
            :key="video.id"
            :to="{ name: 'VideoPage', params: { id: video.id }}"
            target="_blank"
            class="video-card router-link">
          <img :src="video.img" :alt="video.title">
          <span>{{ video.title }}</span>
        </router-link>
      </div>
    </div>

    <div class="main_video">
      <router-link
          v-for="video in recommendVideos"
          :key="video.id"
          :to="{ name: 'VideoPage', params: { id: video.id }}"
          target="_blank"
          class="video-card router-link">
        <img :src="video.img" :alt="video.title">
        <span>{{ video.title }}</span>
      </router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: "MainPage",
  data() {
    return {
      currentIndex: 0,
      timer: null,
      mainVideo: [
        {id: 1, img: require('@/assets/gundong1.png'), title: '凸变英雄，b站出品'},
        {id: 2, img: require('@/assets/gundong2.png'), title: '图片2标题'},
        {id: 3, img: require('@/assets/gundong3.png'), title: '图片3标题'}
      ],
      videoList: [
        {id: 2, img: require('../assets/gundong3.png'), title: '视频2'},
        {id: 3, img: require('../assets/gundong2.png'), title: '视频3'},
        {id: 4, img: require('../assets/gundong3.png'), title: '视频4'},
        {id: 5, img: require('../assets/gundong1.png'), title: '视频5'},
        {id: 6, img: require('../assets/gundong3.png'), title: '视频6'},
        {id: 7, img: require('../assets/gundong2.png'), title: '视频7'}
      ],
      recommendVideos: [
        {id: 8, img: require('../assets/gundong1.png'), title: '推荐视频1'},
        {id: 9, img: require('../assets/gundong3.png'), title: '推荐视频2'},
        {id: 10, img: require('../assets/gundong2.png'), title: '推荐视频3'},
        {id: 11, img: require('../assets/gundong1.png'), title: '推荐视频4'},
        {id: 12, img: require('../assets/gundong3.png'), title: '推荐视频5'}
      ]
    }
  },
  computed: {
    currentVideo() {
      return this.mainVideo[this.currentIndex];
    }
  },
  mounted() {
    this.startAutoPlay();
  },
  beforeUnmount() {
    this.stopAutoPlay();
  },
  methods: {
    startAutoPlay() {  // 统一命名为 startAutoPlay
      this.stopAutoPlay(); // 先停止现有定时器
      this.timer = setInterval(() => {
        this.nextSlide();
      }, 3000);
    },
    stopAutoPlay() {
      if (this.timer) {
        clearInterval(this.timer);
        this.timer = null;
      }
    },
    nextSlide() {
      this.currentIndex = (this.currentIndex + 1) % this.mainVideo.length;
    },
    prevSlide() {
      this.currentIndex = (this.currentIndex - 1 + this.mainVideo.length) % this.mainVideo.length;
    },
    goToSlide(index) {
      this.currentIndex = index;
      this.startAutoPlay();
    }
  }
}
</script>

<style scoped>
/* 轮播图相关样式 */
.fade-enter-active, .fade-leave-active {
  transition: opacity 0.8s ease;
}

.fade-enter-from, .fade-leave-to {
  opacity: 0;
}

.carousel-btn {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  width: 40px;
  height: 40px;
  background: rgba(0, 0, 0, 0.5);
  color: white;
  border: none;
  border-radius: 50%;
  font-size: 20px;
  cursor: pointer;
  z-index: 10;
  transition: all 0.3s;
}

.carousel-btn:hover {
  background: rgba(0, 0, 0, 0.8);
}

.carousel-btn.prev {
  left: 15px;
}

.carousel-btn.next {
  right: 15px;
}

.carousel-indicators {
  position: absolute;
  bottom: 20px;
  left: 0;
  right: 0;
  display: flex;
  justify-content: center;
  gap: 8px;
  z-index: 10;
}

.carousel-indicators span {
  width: 12px;
  height: 12px;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.5);
  cursor: pointer;
  transition: all 0.3s;
}

.carousel-indicators span.active {
  background: white;
  transform: scale(1.2);
}

.carousel-indicators span:hover {
  background: rgba(255, 255, 255, 0.8);
}

/* 原有样式 */
a,
.router-link {
  text-decoration: none;
  color: white;
}

.video-card {
  transition: transform 0.3s;
  display: block;
  text-decoration: none;
  color: white;
  font-size: 10px;
  width: 298px;
  height: 242.63px;
}

.video-card:hover {
  transform: translateY(-5px);
}

.video-card span {
  display: block;
  margin-top: 10px;
}

.video-card img {
  width: 100%;
  display: block;
  transition: transform 0.3s;
  border-radius: 10px;
}

.video-card:hover img {
  transform: scale(1.03);
}

.main-page {
  padding-top: 20px;
  width: 1570px;
  margin: auto;
}

.gundong_box {
  justify-content: space-between;
  display: flex;
  width: 1570px;
  margin: auto;
  gap: 20px;
}

.gundong {
  width: 616px;
  height: 430.25px;
  position: relative;
}

.gundong img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  display: block;
  border-radius: 10px;
}

.gundong_right {
  width: 100%;
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
}

.main_video {
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  width: 1570px;
  margin: auto;
  gap: 20px;
  margin-top: 20px;
}

.main_video img,
.gundong_right img {
  width: 100%;
  height: 167.63px;
  object-fit: cover;
}

.fade-enter-active, .fade-leave-active {
  transition: opacity 0.8s ease;
}

.fade-enter-from, .fade-leave-to {
  opacity: 0;
}
</style>