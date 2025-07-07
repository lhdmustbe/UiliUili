<template>
  <div class="main-page">
    <div class="gundong_box">
      <div class="gundong">
        <a href="/video/1" style="width: 616px;height: 430.25px; display: block; position: relative;">
          <span
              style="position: absolute;bottom: 10%;margin-left: 15px;text-decoration: none;color: white ;font-weight: bold;font-size: 20px;width: 516px">凸变英雄，b站出品</span>
          <img src="../assets/gundong1.png">
        </a>
      </div>

      <div class="gundong_right">
        <a href="/video/2" class="video-card">
          <img src="../assets/gundong1.png">
          <span>&^*(^*(^312&^&*(^&*^(&*)</span>
        </a>
        <a href="/video/3" class="video-card">
          <img src="../assets/gundong1.png">
          <span>&^*(^*(^&^&*4124124124(^&*^(&*)</span>
        </a>
        <a href="/video/4" class="video-card">
          <img src="../assets/gundong1.png">
          <span>&^*(^*(^&^&123123123*(^&*^(&*)</span>
        </a>
        <a href="/video/5" class="video-card">
          <img src="../assets/gundong1.png">
          <span>&^*(^*(^&^&412421*(^&*^(&*)</span>
        </a>
        <a href="/video/6" class="video-card">
          <img src="../assets/gundong1.png">
          <span>&^*(^*(^&^&*(4214^&*^(&*)</span>
        </a>
        <a href="/video/7" class="video-card">
          <img src="../assets/gundong1.png">
          <span>&^*(^*(^&^&*(124^&*^(&*)</span>
        </a>
      </div>
    </div>

    <div class="main_video">
      <a href="/video/8" class="video-card">
        <img src="../assets/gundong1.png">
        <span>&^*(^*(^&^&*(^&*^(&*)</span>
      </a>
      <a href="/video/9" class="video-card">
        <img src="../assets/gundong1.png">
        <span>&^*(^*(^&^&*(^&*^(&*)</span>
      </a>
      <a href="/video/10" class="video-card">
        <img src="../assets/gundong1.png">
        <span>&^*(^*(^&^&*(^&*^(&*)</span>
      </a>
      <a href="/video/11" class="video-card">
        <img src="../assets/gundong1.png">
        <span>&^*(^*(^&^&*(^&*^(&*)</span>
      </a>
      <a href="/video/12" class="video-card">
        <img src="../assets/gundong1.png">
        <span>&^*(^*(^&^&*(^&*^(&*)</span>
      </a>
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
        url: '/video/1',
        img: '../assets/gundong1.png',
        title: '凸变英雄，b站出品',
        views: '',
        up: ''
      },
      recommendVideos: [],
      videoList: []
    }
  },
}
</script>

<style scoped>
a {
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