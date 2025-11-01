<template>
  <div class="video-page">
    <div class="video-content">
      <!-- 视频播放器区域 -->
      <div class="video-section">
        <div class="video-wrapper">
          <video
              ref="videoPlayer"
              controls
              autoplay
              :src="videoUrl"
              class="video-player"
              @play="onPlay"
              @pause="onPause"
              @timeupdate="onTimeUpdate"
          ></video>
        </div>

        <!-- 视频信息区域 -->
        <div class="video-info">
          <h1 class="video-title">{{ videoTitle }}</h1>

          <div class="video-meta">
            <div class="meta-left">
              <span class="view-count">{{ viewCount }} 次观看</span>
              <span class="publish-date">{{ publishTime }}</span>
            </div>
          </div>

          <!-- 视频操作区域 -->
          <div class="video-actions">
            <div class="action-item" @click="likeVideo">
              <i :class="['icon', 'like-icon', likeStatus ? 'liked' : '']"></i>
              <span class="action-text">{{ likeCount }}</span>
            </div>
            <div class="action-item" @click="coinVideo">
              <i :class="['icon', 'coin-icon', coinStatus ? 'coined' : '']"></i>
              <span class="action-text">{{ coinCount }}</span>
            </div>
            <div class="action-item" @click="favoriteVideo">
              <i class="icon favorite-icon"></i>
              <span class="action-text">{{ favoriteCount }}</span>
            </div>
            <div class="action-item">
              <i class="icon share-icon"></i>
              <span class="action-text">分享</span>
            </div>
          </div>

          <!-- 视频描述 -->
          <div class="video-description">
            <p>{{ videoDescription }}</p>
          </div>
        </div>
      </div>

      <!-- 作者信息区域 -->
      <div class="author-section">
        <div class="author-info">
          <div class="avatar-container">
            <img src="../assets/toux.png" alt="" class="author-avatar">
          </div>
          <div class="author-details">
            <h3 class="author-name">{{ authorName }}</h3>
            <p class="author-desc">关注我获取更多视频</p>
          </div>
          <button class="follow-btn" :class="{ followed: isFollowed }" @click="toggleFollow">
            {{ isFollowed ? '已关注' : '+ 关注' }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'VideoPage',
  props: {
    id: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      videoId: '',
      videoUrl: '',
      videoTitle: '',
      tags: '',
      description: '',
      publishtime: '',
      viewCount: '',
      // linkcount: '',
      favoritecount: '',
      videoDescription: '',
      publishTime: '',
      isPlaying: false,

      // 新增点赞投币相关数据
      likeCount: 0,
      coinCount: 0,
      favoriteCount: 0,
      likeStatus: false,
      coinStatus: false,
      favoriteStatus: false,

      // 作者信息
      authorAvatar: '',
      authorName: '视频作者',
      authorDesc: '这是作者的简介',
      isFollowed: false
    }
  },
  created() {
    this.fetchVideoData()
  },
  methods: {
    async fetchVideoData() {
      try {
        const response = await this.$axios.get(`http://localhost:8081/video/${this.id}`);
        if (response.data.code === 200) {
          this.videoId = response.data.data.videoId;
          this.videoUrl = response.data.data.videoUrl;
          this.videoTitle = response.data.data.title;
          this.videoDescription = response.data.data.description;
          this.publishTime = response.data.data.publishTime;
          this.viewCount = response.data.data.viewCount;
          this.favoriteCount = response.data.data.favoriteCount;
          // // 初始化点赞投币数据
          this.likeCount = response.data.data.likeCount || 0;
          this.coinCount = response.data.data.coinCount || 0;
          console.log(response.data.data)
        } else {
          throw new Error(response.data.msg || '获取视频失败');
        }
      } catch (error) {
        console.error('获取视频失败:', error);
        this.$message.error('视频加载失败: ' + error.message);
      }
    },
    onPlay() {
      this.isPlaying = true
      console.log('视频开始播放')
    },
    onPause() {
      this.isPlaying = false
      console.log('视频暂停')
    },
    onTimeUpdate() {
      // 可以在这里添加时间更新逻辑
    },

    // 点赞功能
    async likeVideo() {
      if (this.likeStatus) {
        // 取消点赞
        this.likeCount--;
        this.likeStatus = false;
      } else {
        // 点赞
        this.likeCount++;
        this.likeStatus = true;
      }
      try {
        const response = await this.$axios.post(`http://localhost:8081/video/${this.id}/like`, {
          likeStatus: this.likeStatus
        });
        console.log('点赞成功:', response.data);
      } catch (error) {
        console.error('点赞失败:', error);
        if (this.likeStatus) {
          this.likeCount--;
          this.likeStatus = false;
        } else {
          this.likeCount++;
          this.likeStatus = true;
        }
        this.$message.error('操作失败，请重试');
      }
      console.log('点赞状态:', this.likeStatus);
    },

    // 投币功能
    async coinVideo() {
      if (this.coinStatus) {
        // 取消投币
        this.coinCount--;
        this.coinStatus = false;
      } else {
        // 投币
        this.coinCount++;
        this.coinStatus = true;
      }

      try {
        const response = await this.$axios.post(`http://localhost:8081/video/${this.id}/toubi`, {
          coinStatus: this.coinStatus
        });
        console.log('投币成功:', response.data);
      } catch (error) {
        console.error('投币失败:', error);
        if (this.coinStatus) {
          this.coinCount--;
          this.coinStatus = false;
        } else {
          this.coinCount++;
          this.coinStatus = true;
        }
        this.$message.error('操作失败，请重试');
      }
      console.log('投币状态:', this.coinStatus);
    },


    async favoriteVideo() {
      if (this.favoriteStatus) {
        // 取消收藏
        this.favoriteCount--;
        this.favoriteStatus = false;
      } else {
        // 投币
        this.favoriteCount++;
        this.favoriteStatus = true;
      }

      try {
        const response = await this.$axios.post(`http://localhost:8081/video/${this.id}/favorite`, {
          favoriteStatus: this.favoriteStatus
        });
        console.log('投币成功:', response.data);
      } catch (error) {
        console.error('投币失败:', error);
        if (this.favoriteStatus) {
          this.favoriteCount--;
          this.favoriteStatus = false;
        } else {
          this.favoriteCount++;
          this.favoriteStatus = true;
        }
        this.$message.error('操作失败，请重试');
      }
      console.log('投币状态:', this.favoriteStatus);
    },


    // 格式化数字显示（大于1000显示k）
    formatCount(count) {
      if (count >= 1000) {
        return (count / 1000).toFixed(1) + 'k';
      }
      return count;
    },

    // 格式化日期显示
    formatDate(dateString) {
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
    },

    // 关注/取消关注功能
    toggleFollow() {
      this.isFollowed = !this.isFollowed;
      // 这里可以添加向后端发送请求的逻辑
      console.log('关注状态:', this.isFollowed);
    }
  }
}
</script>

<style scoped>
.video-page {
  min-width: 1200px;
  padding: 20px;
  background-color: #0d1117; /* 保持黑色背景 */
  min-height: 100vh;
}

.video-content {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.video-section {
  background-color: #0d1117; /* 改为黑色背景 */
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.3);
  overflow: hidden;
}

.video-wrapper {
  position: relative;
  width: 100%;
  padding-bottom: 56.25%; /* 16:9 比例 */
  background-color: #000;
  overflow: hidden;
}

.video-player {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.video-info {
  background-color: #0d1117; /* 改为黑色背景 */
  padding: 20px;
  color: #c9d1d9; /* 添加浅色文字 */
}

.video-title {
  font-size: 20px;
  font-weight: bold;
  color: #c9d1d9; /* 改为浅色文字 */
  margin-bottom: 15px;
}

.video-meta {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
  padding-bottom: 20px;
  border-bottom: 1px solid #30363d; /* 改为深色边框 */
}

.meta-left {
  display: flex;
  gap: 15px;
}

.view-count, .publish-date {
  color: #8b949e; /* 改为灰色文字 */
  font-size: 14px;
}

/* 视频操作区域 */
.video-actions {
  display: flex;
  gap: 30px;
  margin-bottom: 20px;
  padding: 15px 0;
  border-bottom: 1px solid #30363d; /* 改为深色边框 */
}

.action-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  cursor: pointer;
  transition: all 0.3s;
  padding: 8px 12px;
  border-radius: 6px;
}

.action-item:hover {
  background-color: #30363d; /* 改为深色悬停背景 */
}

.icon {
  width: 24px;
  height: 24px;
  margin-bottom: 5px;
  background-size: contain;
  background-repeat: no-repeat;
  background-position: center;
}

/* 点赞图标样式 */
.like-icon:not(.liked) {
  background-image: url('../assets/dz.png');
}

.like-icon.liked {
  background-image: url('../assets/dz.png');
}

/* 投币图标样式 */
.coin-icon:not(.coined) {
  background-image: url('../assets/yb.png');
}

.coin-icon.coined {
  background-image: url('../assets/yb.png');
}

.favorite-icon {
  background-image: url('../assets/sc.png');
}

.share-icon {
  background-image: url('../assets/zf.png');
}

.action-text {
  font-size: 14px;
  color: #8b949e; /* 改为灰色文字 */
}

.video-description {
  padding: 15px;
  background-color: #161b22; /* 改为深色背景 */
  border-radius: 4px;
  color: #c9d1d9; /* 改为浅色文字 */
  line-height: 1.6;
}

/* 作者信息区域 */
.author-section {
  background-color: #0d1117; /* 改为黑色背景 */
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.3);
  padding: 20px;
}

.author-info {
  display: flex;
  align-items: center;
  gap: 15px;
}

.avatar-container {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  overflow: hidden;
}

.author-avatar {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.author-details {
  flex: 1;
}

.author-name {
  font-size: 16px;
  font-weight: bold;
  color: #c9d1d9; /* 改为浅色文字 */
  margin-bottom: 5px;
}

.author-desc {
  font-size: 13px;
  color: #8b949e; /* 改为灰色文字 */
}

.follow-btn {
  background-color: #00a1d6;
  color: white;
  border: none;
  padding: 8px 16px;
  border-radius: 6px;
  font-size: 14px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.follow-btn.followed {
  background-color: #666;
  cursor: default;
}

.follow-btn:hover {
  background-color: #0087b3;
}

.follow-btn.followed:hover {
  background-color: #666;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .video-page {
    min-width: 100%;
    padding: 10px;
  }

  .video-content {
    padding: 10px;
  }

  .video-actions {
    gap: 15px;
  }

  .author-info {
    flex-direction: column;
    align-items: flex-start;
    gap: 10px;
  }
}
</style>

