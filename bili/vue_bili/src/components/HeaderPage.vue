<template>
  <div class="container-wrapper">
    <div class="flex-box">
      <ul class="flex-item">
        <li v-for="item in leftNav" :key="item">
          <a href="">{{ item }}</a>
        </li>
      </ul>

      <el-input v-model="inputValue" placeholder="请输入内容" class="search" suffix-icon="Search"></el-input>

      <ul class="flex-item" style="margin-right: 45px">
        <li>
          <div
              @mouseenter="isLoggedIn ? showUserInfo = true : null"
              @mouseleave="isLoggedIn ? showUserInfo = false : null">

            <a style="all: revert; cursor: pointer" @click="showLoginDialog">

              <div class="personal_center"
                   :class="{ 'logged-in': isLoggedIn }">
                {{ isLoggedIn ? userInfo.username : '登录' }}
              </div>

            </a>

            <!-- 用户信息弹窗 -->
            <div v-if="isLoggedIn && showUserInfo" class="user-info-popup">
              <div class="user-info-header">
                <span>{{ userInfo.username }}</span>
              </div>
              <div class="user-info-content">
                <el-button @click="logout" type="text">退出登录</el-button>
              </div>
            </div>
          </div>
        </li>

        <li v-for="item in rightNav" :key="item">
          <a href="">
            {{ item }}
          </a>
        </li>
      </ul>


      <!--      登录框-->
      <el-dialog
          style="border-radius: 20px"
          v-if="loginVisible"
          v-model="loginVisible"
          width="30%"
          :style="{ display: isLoggedIn ? 'none' : 'block' }"
          :before-close="handleClose">
        <el-form :model="loginForm">

          <div class="Username">

            <label style="line-height: 45px;padding-left: 15px;color: #0d1117">账户</label>
            <input class="input_Username" v-model="loginForm.username" placeholder="请输入账号">

          </div>


          <div class="Password">

            <label style="line-height: 45px;padding-left: 15px;color: #0d1117">密码</label>
            <input class="input_Password" v-model="loginForm.password" placeholder="请输入密码">

          </div>
          <span class="dialog-footer"
                style="display: flex;justify-content: space-between ;width: 400px;margin: auto;margin-top: 20px">
          <el-button @click="loginVisible = false" style="width: 194px;height: 40px">取消</el-button>
          <el-button type="primary" @click="handleLogin" style="width: 194px;height: 40px">登录</el-button>
        </span>
        </el-form>
      </el-dialog>


    </div>
  </div>
</template>

<script>
// import axios from 'axios'

export default {
  name: "HeaderPage",
  data() {
    return {
      isLoggedIn: false, // 新增登录状态
      userInfo: null,
      showUserInfo: false,

      inputValue: '',
      leftNav: ['首页', '番剧', '漫画', '直播'],
      rightNav: ['收藏', '历史', '消息', '投稿'],
      loginVisible: false,
      loginForm: {
        username: '',
        password: ''
      }
    }
  },
  methods: {
    showLoginDialog() {
      // 已登录状态直接返回
      if (this.isLoggedIn) return;
      this.loginVisible = true;
    },
    handleClose() {
      this.loginVisible = false
    },
    async handleLogin() {
      try {
        const response = await this.$axios.post('http://localhost:8081/users/login', {
          username: this.loginForm.username,
          passwordHash: this.loginForm.password
        });

        if (response.data.code === 200) {
          localStorage.setItem('token', response.data.data);
          this.$message.success('登录成功');
          this.isLoggedIn = true;
          this.loginVisible = false; // 强制关闭弹窗
          this.userInfo = {username: this.loginForm.username}; // 保存用户信息
        } else {
          this.$message.error(response.data.msg);
        }
      } catch (error) {
        this.$message.error('登录失败: ' + error.response?.data?.msg || error.message);
      }
    },
    logout() {
      localStorage.removeItem('token');
      this.isLoggedIn = false;
      this.userInfo = null;
      this.$message.success('已退出登录');
    }
  }
}
</script>

<style scoped>
.container-wrapper {
  min-width: 1200px;
  width: 100%;
  position: relative;
}

.flex-box {
  display: flex;
  justify-content: space-between;
  align-items: center;
  min-width: 1200px;
  padding: 0 20px;
  box-sizing: content-box;
}

.flex-item {
  position: relative;
  overflow: visible;
  list-style: none;
  display: flex;
}

.flex-item li {
  width: 60px;
  float: left;
}

.flex-item a {
  text-decoration: none;
  color: aliceblue;
  margin-left: 10px;
  margin-right: 10px;
}

.search {
  margin: 0 auto;
  width: 500px;
  height: 40px;
}

.personal_center {
  display: inline-block;
  will-change: transform;
  transition: transform 0.2s ease-out;
  padding: 3px;
  line-height: 37px;
  text-align: center;
  margin-top: -8px;
  width: 37px;
  height: 37px;
  border-radius: 50%;
  background-color: #1093ff;
  color: white;
}

:deep(.el-input .el-input__wrapper) {
  border-radius: 20px;
  background-color: #161b22;
  border: 1px solid #30363d;
}

:deep(.el-input input) {
  margin-left: 10px;
  color: #c9d1d9;
  caret-color: #c9d1d9;
}

.Username {
  margin: auto;
  width: 400px;
  height: 45px;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
  border: 1px solid #ccc
}

.Password {
  margin: auto;
  width: 400px;
  height: 45px;
  border-bottom-left-radius: 10px;
  border-bottom-right-radius: 10px;
  border-bottom: 1px solid #ccc;
  border-left: 1px solid #ccc;
  border-right: 1px solid #ccc
}

.input_Username {
  width: 310px;
  margin-left: 10px;
  font-style: normal;
  font-weight: 400;
  font-size: 14px;
  line-height: 20px;
  -webkit-appearance: none;
  -moz-appearance: none;
  appearance: none;
  outline: none;
  border: none;
}

.input_Password {
  width: 310px;
  margin-left: 10px;
  font-style: normal;
  font-weight: 400;
  font-size: 14px;
  line-height: 20px;
  -webkit-appearance: none;
  -moz-appearance: none;
  appearance: none;
  outline: none;
  border: none;
}

.user-info-popup {
  position: absolute;
  top: 120%;
  right: 0%;
  width: 300px;
  background-color: white;
  border-radius: 10px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  z-index: 1000;
  padding: 10px;
}

.user-info-header {
  padding: 8px 0;
  border-bottom: 1px solid #ebeef5;
  margin-bottom: 8px;
}

.user-info-content {
  padding: 8px 0;
}

.personal_center:hover {
  transform: scale(1.1);
}

.personal_center.logged-in {
  background-color: #4CAF50;
  color: white;
}

.personal_center.logged-in:hover {
  transform: scale(1.2);
}

/*.personal_center.logged-in:hover .user-info-popup{*/
/*  */
/*}*/
</style>
