<template>
  <div class="main-page">
    <div class="gundong_box">
      <div class="gundong">
        <router-link :to="{ name: 'VideoPage', params: { id: mainVideo.id }}"
                     style="width: 616px;height: 430.25px; display: block; position: relative;">
          <span
              style="position: absolute;bottom: 10%;margin-left: 15px;text-decoration: none;color: white ;font-weight: bold;font-size: 20px;width: 516px">
            {{ mainVideo.title }}
          </span>
          <img :src="mainVideo.img">
        </router-link>
      </div>
      <div class="gundong_right">
        <router-link
            v-for="video in videoList"
            :key="video.id"
            :to="{ name: 'VideoPage', params: { id: video.id }}"
            target="_blank"
            class="video-card router-link">
          <img :src="video.img">
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
        <img :src="video.img">

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
      mainVideo: {
        id: 1,
        img: require('../assets/gundong1.png'),
        title: '凸变英雄，b站出品',
      },
      videoList: [
        {id: 2, img: require('../assets/gundong1.png'), title: '视频2'},
        {id: 3, img: require('../assets/gundong1.png'), title: '视频3'},
        {id: 4, img: require('../assets/gundong1.png'), title: '视频4'},
        {id: 5, img: require('../assets/gundong1.png'), title: '视频5'},
        {id: 6, img: require('../assets/gundong1.png'), title: '视频6'},
        {id: 7, img: require('../assets/gundong1.png'), title: '视频7'}
      ],
      recommendVideos: [
        {id: 8, img: require('../assets/gundong1.png'), title: '推荐视频1'},
        {id: 9, img: require('../assets/gundong1.png'), title: '推荐视频2'},
        {id: 10, img: require('../assets/gundong1.png'), title: '推荐视频3'},
        {id: 11, img: require('../assets/gundong1.png'), title: '推荐视频4'},
        {id: 12, img: require('../assets/gundong1.png'), title: '推荐视频5'}
      ]
    }
  },
}
</script>

<style scoped>
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
}

.video-card:hover img {
  transform: scale(1.03);
}

img {
  border-radius: 10px;
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
}

.main_video img,
.gundong_right img {
  width: 100%;
  height: 167.63px;
  object-fit: cover;
}
</style>
