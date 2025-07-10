<template>
  <div class="main-page">
    <div class="gundong_box">
      <div class="gundong" @mouseenter="stopAutoPlay" @mouseleave="startAutoPlay">
        <router-link
            :to="{ name: 'VideoPage', params: { id: currentVideo.id }}"
            target="_blank"
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
        {id: 2, img: require('@/assets/gundong2.png'), title: '凸变英雄，b站出品'},
        {id: 3, img: require('@/assets/gundong3.png'), title: '凸变英雄，b站出品'}
      ],
      videoList: [],
      recommendVideos: []
    }
  },
  computed: {
    currentVideo() {
      return this.mainVideo[this.currentIndex];
    }
  },

  mounted() {
    this.startAutoPlay();
    this.fetchVideoList(); // 确保在mounted中调用
  },
  beforeUnmount() {
    this.stopAutoPlay();
  },
  methods: {
    async fetchVideoList() {
      try {
        const response = await this.$axios.get('http://localhost:8081/video/batch', {
          params: {


            videoIds: '1,2,3,4,5' // 确保这些ID在数据库存在且status=1

            // 修改成随机数按照已经有点视频id号进行不重复随机数


          }
        });
        if (response.data.code === 200) {
          this.videoList = response.data.data.map(item => ({
            id: item.videoId,
            img: item.coverUrl,
            title: item.title,
            videoUrl: item.videoUrl,
            duration: item.duration // 添加duration字段

          }));
          console.log('视频列表:', this.videoList);
        } else {
          console.error('API错误:', response.data.msg);
        }
      } catch (error) {
        console.error('请求失败:', error);
        // 4. 添加错误提示
        this.$message.error('获取视频列表失败: ' + error.message);
      }
    },


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
    },
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