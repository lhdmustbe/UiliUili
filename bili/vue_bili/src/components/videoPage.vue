<template>
  <div class="video-page">
    <div class="video-header">
      <h1>视频播放页 - ID: {{ id }}</h1>
    </div>

    <div class="video-content">
      <div class="video-wrapper">
        <video
            ref="videoPlayer"
            controls
            autoplay
            src="../assets/video1.mp4"
            class="video-player"
            @play="onPlay"
            @pause="onPause"
            @timeupdate="onTimeUpdate"

        ></video>
        <!--            :src="videoUrl"-->

      </div>

      <div class="video-info">
        <h2>{{ videoTitle }}</h2>
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
      videoUrl: '',
      videoTitle: '视频标题',
      videoDescription: '视频描述信息',
      isPlaying: false
    }
  },
  created() {
    // 根据id获取视频信息
    this.fetchVideoData()
  },
  methods: {
    fetchVideoData() {
      this.videoUrl = `../assets/video1.mp4`
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