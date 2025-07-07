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
          <a style="all: revert; cursor: pointer" @click="showLoginDialog">
            <div class="personal_center">
              登录
            </div>
          </a>
        </li>
        <li v-for="item in rightNav" :key="item">
          <a href="">
            {{ item }}
          </a>
        </li>
      </ul>

      <!--      登录框-->
      <el-dialog
          v-model="loginVisible"
          width="30%"
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
export default {
  name: "HeaderPage",
  data() {
    return {
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
    handleSearch() {

    },
    showLoginDialog() {
      this.loginVisible = true
    },
    handleClose() {
      this.loginVisible = false
    },
    handleLogin() {
      // 这里添加登录逻辑
      console.log('登录信息:', this.loginForm)
      this.loginVisible = false
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
</style>
