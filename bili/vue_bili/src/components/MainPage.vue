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
          <div class="video-thumb-container">
            <img :src="video.img" :alt="video.title" class="video-thumb">
            <div class="video-duration" v-if="video.duration">{{ formatDuration(video.duration) }}</div>
          </div>
          <div class="video-info">
            <h3 class="video-title">{{ video.title }}</h3>
            <p class="video-stats" v-if="video.viewCount || video.publishTime">
              <span v-if="video.viewCount">{{ formatCount(video.viewCount) }}次观看</span>
              <span v-if="video.publishTime" class="publish-date">{{ formatDate(video.publishTime) }}</span>
            </p>
          </div>
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
        <div class="video-thumb-container">
          <img :src="video.img" :alt="video.title" class="video-thumb">
          <div class="video-duration" v-if="video.duration">{{ formatDuration(video.duration) }}</div>
        </div>
        <div class="video-info">
          <h3 class="video-title">{{ video.title }}</h3>
          <p class="video-stats" v-if="video.viewCount || video.publishTime">
            <span v-if="video.viewCount">{{ formatCount(video.viewCount) }}次观看</span>
            <span v-if="video.publishTime" class="publish-date">{{ formatDate(video.publishTime) }}</span>
          </p>
        </div>
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
        {id: 2, img: require('@/assets/gundong2.png'), title: '刺杀小说家'},
        {id: 3, img: require('@/assets/gundong3.png'), title: '实况足球'}
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
    this.fetchVideoList();
    this.fetchrecommendVideos();
  },
  beforeUnmount() {
    this.stopAutoPlay();
  },
  methods: {
    async fetchrecommendVideos() {
      try {
        const response = await this.$axios.get('http://localhost:8081/video/page', {
          params: {
            pageNum: 1,
            pageSize: 20
          }
        });

        if (response.data.code === 200) {
          const videos = response.data.data.records || response.data.data;
          this.recommendVideos = videos.map(item => ({// 推荐写法
            id: item.videoId || item.id,
            img: item.coverUrl || item.img,
            duration: item.duration,
            viewCount: item.viewCount,
            title: item.title,
            videoUrl: item.videoUrl,
            publishTime: item.publishTime
          }));
          console.log('推荐视频列表:', this.recommendVideos);
        } else {
          console.error('API错误:', response.data.msg);
          this.$message.error('获取视频列表失败: ' + response.data.msg);
        }
      } catch (error) {
        console.error('请求失败:', error);
        this.$message.error('获取视频列表失败: ' + error.message);
      }
    },
    async fetchVideoList() {
      try {
        const response = await this.$axios.get('http://localhost:8081/video/page', {
          params: {
            pageNum: 1,
            pageSize: 6
          }
        });

        if (response.data.code === 200) {
          const videos = response.data.data.records || response.data.data;
          this.videoList = videos.map(function (item) {// 传统写法
            return {
              id: item.videoId || item.id,
              img: item.coverUrl || item.img,
              title: item.title,
              videoUrl: item.videoUrl,
              duration: item.duration,
              viewCount: item.viewCount,
              publishTime: item.publishTime
            }
          });
          console.log('视频列表:', this.videoList);
        } else {
          console.error('API错误:', response.data.msg);
          this.$message.error('获取视频列表失败: ' + response.data.msg);
        }
      } catch (error) {
        console.error('请求失败:', error);
        this.$message.error('获取视频列表失败: ' + error.message);
      }
    },

    startAutoPlay() {
      this.stopAutoPlay();
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

    // 格式化播放时长
    formatDuration(seconds) {
      if (!seconds) return '';
      const mins = Math.floor(seconds / 60);
      const secs = Math.floor(seconds % 60);
      return `${mins.toString().padStart(2, '0')}:${secs.toString().padStart(2, '0')}`;
    },

    // 格式化播放量
    formatCount(count) {
      if (count >= 10000) {
        return (count / 10000).toFixed(1) + '万';
      } else if (count >= 1000) {
        return (count / 1000).toFixed(1) + '千';
      }
      return count;
    },

    // 格式化日期
    formatDate(dateString) {
      if (!dateString) return '';
      const date = new Date(dateString);
      const now = new Date();
      const diffTime = Math.abs(now - date);
      const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));

      if (diffDays === 1) {
        return '昨天';
      } else if (diffDays < 30) {
        return diffDays + '天前';
      } else {
        return date.getFullYear() + '-' +
            (date.getMonth() + 1).toString().padStart(2, '0') + '-' +
            date.getDate().toString().padStart(2, '0');
      }
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
  width: 298px;
  height: 242.63px;
  border-radius: 8px;
  overflow: hidden;
  background-color: #0d1117;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.3);
}

.video-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.4);
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
  background-color: #0d1117;
  min-height: 100vh;
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

.gundong_right .video-card {
  border: 1px solid rgba(255, 255, 255, 0.1);
  width: 100%;
  height: auto;
  background-color: #0d1117;
  border-radius: 8px;
  overflow: hidden;
  transition: all 0.3s ease;
}

.gundong_right .video-thumb-container {
  position: relative;
  width: 100%;
  height: 150px;
}

.gundong_right .video-thumb {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.3s;
  border-radius: 8px;
}

.gundong_right .video-card:hover .video-thumb {
  transform: scale(1.05);
}

.gundong_right .video-duration {
  position: absolute;
  bottom: 8px;
  right: 8px;
  background-color: rgba(0, 0, 0, 0.7);
  color: white;
  font-size: 12px;
  padding: 2px 4px;
  border-radius: 2px;
  z-index: 2;
}

.gundong_right .video-info {
  padding: 10px;
}

.gundong_right .video-title {
  font-size: 14px;
  font-weight: 500;
  margin: 0 0 8px 0;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  line-height: 1.4;
  color: white;
}

.gundong_right .video-stats {
  font-size: 12px;
  color: #8b949e;
  margin: 0;
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.gundong_right .publish-date {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
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

.main_video .video-card {
  border: 1px solid rgba(255, 255, 255, 0.1);
  width: 100%;
  height: auto;
  background-color: #0d1117;
  border-radius: 8px;
  overflow: hidden;
  transition: all 0.3s ease;
}

.main_video .video-thumb-container {
  position: relative;
  width: 100%;
  height: 167.63px;
}

.main_video .video-thumb {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.3s;
  border-radius: 8px;
}

.main_video .video-card:hover .video-thumb {
  transform: scale(1.05);
}

.main_video .video-duration {
  position: absolute;
  bottom: 8px;
  right: 8px;
  background-color: rgba(0, 0, 0, 0.7);
  color: white;
  font-size: 12px;
  padding: 2px 4px;
  border-radius: 2px;
  z-index: 2;
}

.main_video .video-info {
  padding: 10px;
}

.main_video .video-title {
  font-size: 14px;
  font-weight: 500;
  margin: 0 0 8px 0;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  line-height: 1.4;
  color: white;
}

.main_video .video-stats {
  font-size: 12px;
  color: #8b949e;
  margin: 0;
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.main_video .publish-date {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.fade-enter-active, .fade-leave-active {
  transition: opacity 0.8s ease;
}

.fade-enter-from, .fade-leave-to {
  opacity: 0;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .main-page {
    width: 100%;
    padding: 10px;
  }

  .gundong_box {
    width: 100%;
    flex-direction: column;
  }

  .main_video {
    width: 100%;
    grid-template-columns: repeat(2, 1fr);
  }

  .gundong {
    width: 100%;
    height: 300px;
  }

  .gundong_right {
    grid-template-columns: repeat(2, 1fr);
  }

  .gundong_right .video-card {
    width: 100%;
    height: auto;
    background-color: #0d1117;
    border-radius: 8px;
    overflow: hidden;
    transition: all 0.3s ease;
  }

  .gundong_right .video-thumb-container {
    position: relative;
    width: 100%;
    height: 167.63px;
  }

  .gundong_right .video-thumb {
    width: 100%;
    height: 100%;
    object-fit: cover;
    transition: transform 0.3s;
  }

  .gundong_right .video-card:hover .video-thumb {
    transform: scale(1.05);
  }

  .gundong_right .video-duration {
    position: absolute;
    bottom: 8px;
    right: 8px;
    background-color: rgba(0, 0, 0, 0.7);
    color: white;
    font-size: 12px;
    padding: 2px 4px;
    border-radius: 2px;
  }

  .gundong_right .video-info {
    padding: 10px;
  }

  .gundong_right .video-title {
    font-size: 14px;
    font-weight: 500;
    margin: 0 0 8px 0;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;
    line-height: 1.4;
  }

  .gundong_right .video-stats {
    font-size: 12px;
    color: #8b949e;
    margin: 0;
    display: flex;
    flex-direction: column;
    gap: 4px;
  }

  .gundong_right .publish-date {
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }
}
</style>