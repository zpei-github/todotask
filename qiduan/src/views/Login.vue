<template>
  <div class="login-container">
    <el-form class="login-form" ref="loginForm" :model="loginForm" label-position="top">
      <el-form-item label="账号">
        <el-input v-model="loginForm.username" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="密码">
        <el-input type="password" v-model="loginForm.password" autocomplete="off"></el-input>
      </el-form-item>
      <div class="form-actions">
        <el-button type="primary" @click="handleLogin">登录</el-button>
        <el-button type="text" @click="handleRegister">注册</el-button>
      </div>
    </el-form>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      loginForm: {
        username: '',
        password: ''
      }
    };
  },
  methods: {
    handleLogin() {
      const { username, password } = this.loginForm;
      // 调用登录API
      axios.post('/api/login', { username, password })
          .then(response => {
            // 假设登录成功后服务器返回的响应中包含了token
            const token = response.data;

            // 存储token到localStorage（或其他安全地方，根据应用需求）
            localStorage.setItem('authToken', token);

            // 跳转到事项页面
            this.$router.push('/mytask');
          })
          .catch(error => {
            // 登录失败处理
            console.error('登录失败:', error);
            // 可以使用Element UI的Message组件显示错误信息
            this.$message.error('登录失败，请检查用户名和密码是否正确。');
          });
    },
    handleRegister() {
      // 跳转到注册页面的逻辑
      this.$router.push('/signup');
    }
  }
};
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: linear-gradient(135deg, #957DAD 0%, #BFA2DB 100%);
}

.login-form {
  width: 300px; /* 或根据需要调整 */
  padding: 20px;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,0.1);
  background-color: #fff;
  border-radius: 5px;
}

.form-actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 20px;
}
</style>
