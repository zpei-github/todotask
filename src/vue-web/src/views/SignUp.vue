<template>
  <div class="register-container">
    <el-form class="register-form" ref="registerForm" :model="registerForm" label-position="top">
      <el-form-item label="用户名" prop="username"
                    :rules="[{ required: true, message: '请输入用户名', trigger: 'blur' }]">
        <el-input v-model="registerForm.username" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password"
                    :rules="[{ required: true, message: '请输入密码', trigger: 'blur' }]">
        <el-input type="password" v-model="registerForm.password" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="confirmPassword"
                    :rules="[{ required: true, message: '请再次输入密码', trigger: 'blur' }]">
        <el-input type="password" v-model="registerForm.confirmPassword" autocomplete="off"></el-input>
      </el-form-item>
      <el-button type="primary" @click="handleRegister">注册</el-button>
    </el-form>
  </div>
</template>


<script>
import axios from "axios";

export default {
  data() {
    return {
      registerForm: {
        username: '',
        password: '',
        confirmPassword: ''
      }
    };
  },
  methods: {
    handleRegister() {
      // 首先检查两次输入的密码是否相同
      if (this.registerForm.password !== this.registerForm.confirmPassword) {
        this.$message.error('两次输入的密码不一致，请重新输入');
        return;
      }

      // 构造注册请求的数据
      const requestData = {
        username: this.registerForm.username,
        password: this.registerForm.password,
      };

      // 使用axios发送注册请求
      axios.post('/api/register', requestData)
          .then(response => {
            // 处理注册成功的逻辑
            // 例如，可以跳转到登录页面或直接登录
            this.$message.success('注册成功，请登录');
            this.$router.push('/');
          })
          .catch(error => {
            // 处理注册失败的逻辑
            // 错误处理可以根据实际的API响应进行调整
            console.error('注册失败:', error);
            this.$message.error(error.response.data);
          });
    }
  }
};
</script>

<style scoped>
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: linear-gradient(135deg, #957DAD 0%, #BFA2DB 100%);
}
.register-form {
  width: 300px; /* 或根据需要调整 */
  padding: 20px;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,0.1);
  background-color: #fff;
  border-radius: 5px;}
</style>