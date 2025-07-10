<template>
  <div class="video-page">
    <div class="video-header">


      <h1>视频播放页 - ID: {{ videoId }}</h1>


    </div>

    <div class="video-content">
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

      <div class="video-info">
        <h1>{{ videoTitle }}</h1>
        <h2>{{ videoDescription }}</h2>
        <p>{{ videoDescription }}</p>
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
      viewcount: '',
      linkcount: '',
      favoritecount: '',

      videoDescription: '',
      isPlaying: false
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
          this.publishtime = response.data.data.publishtime;
          this.viewcount = response.data.data.viewcount;
          this.linkcount = response.data.data.linkcount;
          this.favoritecount = response.data.data.favoritecount;
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
    }
  }
}
</script>

<style scoped>
.video-page {
  min-width: 1200px;
  padding: 20px;
  color: #fff;
}

.video-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.video-wrapper {
  position: relative;
  width: 100%;
  padding-bottom: 56.25%; /* 16:9 比例 */
  background-color: #000;
  overflow: hidden;
  border-radius: 8px;
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
  max-width: 800px;
  margin: 20px auto;
  padding: 20px;
  background-color: #161b22;
  border-radius: 8px;
}

.video-info h2 {
  font-size: 24px;
  margin-bottom: 10px;
}

.video-info p {
  font-size: 16px;
  color: #aaa;
}
</style>