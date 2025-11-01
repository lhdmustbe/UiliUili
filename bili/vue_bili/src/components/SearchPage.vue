<template>
  <div class="search-page">
    <div class="container-wrapper">
      <div class="flex-box">
        <ul class="flex-item">
          <li v-for="item in leftNav" :key="item">
            <a href="">{{ item }}</a>
          </li>
        </ul>

        <el-input v-model="inputValue" placeholder="请输入内容" class="search" suffix-icon="Search"
                  @keyup.enter="handleSearch"></el-input>

        <ul class="flex-item" style="margin-right: 45px">
          <li>
            <div
                @mouseenter="isLoggedIn ? showUserInfo = true : null"
                @mouseleave="isLoggedIn ? showUserInfo = false : null">

              <a style="all: revert; cursor: pointer" @click="showLoginDialog">

                <div class="personal_center"
                     :class="{ 'logged-in': isLoggedIn }">
                  {{ isLoggedIn ? userInfo.username.charAt(0) : '登录' }}
                </div>

              </a>
              <!-- 用户信息弹窗 -->
              <div v-if="isLoggedIn && showUserInfo" class="user-info-popup">
                <div class="user-info-header">
                  <span>用户名： {{ userInfo.username }}</span><br>
                  <span>邮箱： {{ userInfo.email }}</span><br>
                  <span>手机号： {{ userInfo.phone }}</span>
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

        <!-- 登录框-->
        <el-dialog
            style="border-radius: 20px"
            v-if="loginVisible"
            v-model="loginVisible"
            width="600px"
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
              <el-button type="primary" @click="register" style="width: 194px;height: 40px">注册</el-button>
            <el-button type="primary" @click="handleLogin" style="width: 194px;height: 40px">登录</el-button>
          </span>
          </el-form>
        </el-dialog>
        <!-- 注册框-->
        <el-dialog
            style="border-radius: 20px;padding: 20px;"
            class=""
            v-model="registerVisible"
            title="用户注册"
            width="500px"
            :close-on-click-modal="false"
        >
          <el-form
              :model="registerForm"
              :rules="registerRules"
              ref="registerFormRef"
              label-width="80px"
          >
            <el-form-item label="用户名" prop="username">
              <el-input
                  v-model="registerForm.username"
                  placeholder="请输入用户名"
                  clearable
              />
            </el-form-item>

            <el-form-item label="密码" prop="password">
              <el-input
                  v-model="registerForm.password"
                  placeholder="请输入密码"
                  type="password"
                  show-password
              />
            </el-form-item>

            <el-form-item label="确认密码" prop="confirmPassword">
              <el-input
                  v-model="registerForm.confirmPassword"
                  placeholder="请再次输入密码"
                  type="password"
                  show-password
              />
            </el-form-item>

            <el-form-item label="邮箱" prop="email">
              <el-input
                  v-model="registerForm.email"
                  placeholder="请输入邮箱"
                  clearable
              />
            </el-form-item>

            <el-form-item label="手机号" prop="phone">
              <el-input
                  v-model="registerForm.phone"
                  placeholder="请输入手机号"
                  clearable
              />
            </el-form-item>
          </el-form>

          <template #footer>
        <span class="dialog-footer">
          <el-button @click="registerVisible = false">取消</el-button>
          <el-button type="primary" @click="handleRegister">注册</el-button>
        </span>
          </template>
        </el-dialog>
      </div>
    </div>

    <!-- 搜索结果内容 -->
    <div class="search-content">
      <div v-if="searchResults.length > 0" class="search-results">
        <h2>搜索结果: "{{ $route.query.keyword }}"</h2>
        <div class="video-grid">
          <router-link
              v-for="video in searchResults"
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
      <div v-else class="no-results">
        <h2>没有找到与 "{{ $route.query.keyword }}" 相关的视频</h2>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "SearchPage",
  data() {
    return {
      // HeaderPage 数据
      isLoggedIn: false,
      userInfo: null,
      showUserInfo: false,
      registerVisible: false,
      registerForm: {
        username: '',
        password: '',
        confirmPassword: '',
        email: '',
        phone: ''
      },
      inputValue: '',
      leftNav: ['首页', '番剧', '漫画', '直播'],
      rightNav: ['收藏', '历史', '消息', '投稿'],
      loginVisible: false,
      loginForm: {
        username: '',
        password: '',
        email: '',
        phone: ''
      },

      // SearchPage 数据
      searchResults: []
    }
  },
  computed: {
    registerRules() {
      const validatePassword = (rule, value, callback) => {
        if (value !== this.registerForm.password) {
          callback(new Error('两次输入的密码不一致'));
        } else {
          callback();
        }
      };

      return {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
          {min: 3, max: 16, message: '长度在3到16个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 6, max: 20, message: '长度在6到20个字符', trigger: 'blur'}
        ],
        confirmPassword: [
          {required: true, message: '请再次输入密码', trigger: 'blur'},
          {validator: validatePassword, trigger: 'blur'}
        ],
        email: [
          {required: true, message: '请输入邮箱', trigger: 'blur'},
          {type: 'email', message: '请输入正确的邮箱格式', trigger: ['blur', 'change']}
        ],
        phone: [
          {required: true, message: '请输入手机号', trigger: 'blur'},
          {pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号码', trigger: 'blur'}
        ]
      }
    }
  },
  watch: {
    // 监听路由变化，当查询参数改变时重新搜索
    '$route.query.keyword': {
      handler(newKeyword) {
        if (newKeyword) {
          this.performSearch(newKeyword);
        }
      },
      immediate: true // 组件创建时立即执行
    }
  },
  methods: {
    handleSearch() {
      // 这里处理搜索逻辑
      console.log('搜索内容:', this.inputValue);
      // 跳转到搜索页面
      if (this.inputValue.trim()) {
        const routeData = this.$router.resolve({
          name: 'SearchPage',
          query: {keyword: this.inputValue}
        });
        window.open(routeData.href, '_blank');
      }
    },
    register() {
      this.registerVisible = true;
      this.$nextTick(() => {
        this.$refs.registerFormRef?.resetFields();
      });
    },
    handleRegisterClose() {
      this.registerVisible = false;
    },
    async handleRegister() {
      try {
        // 验证表单
        await this.$refs.registerFormRef.validate();

        // 发送注册请求
        const response = await this.$axios.post('http://localhost:8081/users/register', {
          username: this.registerForm.username,
          passwordHash: this.registerForm.password,
          email: this.registerForm.email,
          phone: this.registerForm.phone
        });

        if (response.data.code === 200) {
          this.$message.success('注册成功');
          this.registerVisible = false;

          // 自动填充登录表单
          this.loginForm.username = this.registerForm.username;
          this.loginForm.password = this.registerForm.password;
        } else {
          this.$message.error(response.data.msg);
        }
      } catch (error) {
        this.$message.error('注册失败: ' + (error.response?.data?.msg || error.message));
      }
    },
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
          passwordHash: this.loginForm.password,
        });

        if (response.data.code === 200) {
          console.log('响应数据:', response.data);
          localStorage.setItem('token', response.data.data);//存入token数据持久化
          this.$message.success('登录成功');
          this.isLoggedIn = true;
          this.loginVisible = false; // 强制关闭弹窗
          this.userInfo =
              {
                username: this.loginForm.username,
                email: response.data.data.email,
                phone: response.data.data.phone
              }; // 保存用户信息

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
    },
    async performSearch(keyword) {
      try {
        const response = await this.$axios.get('http://localhost:8081/video/page', {
          params: {
            pageNum: 1,
            pageSize: 20,
            title: keyword
          }
        });

        if (response.data.code === 200) {
          const videos = response.data.data.records || response.data.data;
          this.searchResults = videos.map(item => {
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
        } else {
          console.error('搜索失败:', response.data.msg);
          this.searchResults = [];
        }
      } catch (error) {
        console.error('搜索请求失败:', error);
        this.searchResults = [];
      }
    },
    // 格式化持续时间
    formatDuration(seconds) {
      if (!seconds) return '';
      const hours = Math.floor(seconds / 3600);
      const minutes = Math.floor((seconds % 3600) / 60);
      const secs = Math.floor(seconds % 60);
      if (hours > 0) {
        return `${hours}:${minutes.toString().padStart(2, '0')}:${secs.toString().padStart(2, '0')}`;
      }
      return `${minutes}:${secs.toString().padStart(2, '0')}`;
    },
    // 格式化数字显示（大于1000显示k）
    formatCount(count) {
      if (count >= 10000) {
        return (count / 10000).toFixed(1) + '万';
      }
      if (count >= 1000) {
        return (count / 1000).toFixed(1) + 'k';
      }
      return count;
    },
    // 格式化日期显示
    formatDate(dateString) {
      if (!dateString) return '';
      const date = new Date(dateString);
      const now = new Date();
      const diffTime = Math.abs(now - date);
      const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));

      if (diffDays === 1) {
        return '昨天';
      } else if (diffDays <= 7) {
        return `${diffDays}天前`;
      } else {
        return `${date.getFullYear()}-${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')}`;
      }
    }
  }
}
</script>

<style scoped>
.search-page {
  background-color: #0d1117;
  min-height: 100vh;
}

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
  top: 100%;
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
  text-align: center;
  font-size: 30px;
  background-color: #4CAF50;
  color: white;
}

.personal_center.logged-in:hover {
  transform: scale(1.2);
}

.search-content {
  padding: 20px;
}

.search-results h2 {
  color: #c9d1d9;
  padding: 0 0 20px 0;
  margin: 0;
}

.video-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 20px;
}

.video-card {
  display: block;
  text-decoration: none;
  color: inherit;
  background-color: #0d1117;
  border-radius: 8px;
  overflow: hidden;
  transition: all 0.3s ease;
}

.video-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.3);
}

.video-thumb-container {
  position: relative;
  width: 100%;
  padding-top: 56.25%; /* 16:9 宽高比 */
}

.video-thumb {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.3s;
}

.video-card:hover .video-thumb {
  transform: scale(1.05);
}

.video-duration {
  position: absolute;
  bottom: 8px;
  right: 8px;
  background-color: rgba(0, 0, 0, 0.7);
  color: white;
  font-size: 12px;
  padding: 2px 4px;
  border-radius: 2px;
}

.video-info {
  padding: 10px;
}

.video-title {
  font-size: 14px;
  font-weight: 500;
  margin: 0 0 8px 0;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  line-height: 1.4;
  color: #c9d1d9;
}

.video-stats {
  font-size: 12px;
  color: #8b949e;
  margin: 0;
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.publish-date {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.no-results h2 {
  color: #c9d1d9;
  text-align: center;
  padding: 50px 0;
}
</style>