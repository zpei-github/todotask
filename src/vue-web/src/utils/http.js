import axios from 'axios';

// 创建axios实例
const http = axios.create({
    baseURL: '/api', // 你的API基地址
    timeout: 10000, // 请求超时时间
});

// 请求拦截器
http.interceptors.request.use(
    config => {
        // 尝试从localStorage获取token
        const token = localStorage.getItem('authToken');
        if (token) {
            // 如果token存在，将其添加到所有请求的Authorization头部
            config.headers.Authorization = `Bearer ${token}`;
            config.headers['Content-Type'] = 'application/json; charset=utf-8';
        }
        return config;
    },
    error => {
        // 对请求错误做些什么
        return Promise.reject(error);
    }
);

export default http;