import axios from 'axios'
import { Message } from 'element-ui'

// 创建axios实例
const service = axios.create({
  baseURL: '/', // api的base_url
  timeout: 15000 // 请求超时时间
})

// request拦截器
service.interceptors.request.use(
  config => {
    // 可以在这里添加token
    const token = localStorage.getItem('token')
    if (token) {
      config.headers['Authorization'] = 'Bearer ' + token
    }
    
    // 添加用户信息到请求头（用于系统服务）
    const userInfoStr = localStorage.getItem('userInfo')
    if (userInfoStr) {
      try {
        const userInfo = JSON.parse(userInfoStr)
        if (userInfo.accountId) {
          config.headers['accountId'] = userInfo.accountId
        }
        if (userInfo.tenantId) {
          config.headers['tenantId'] = userInfo.tenantId
        }
      } catch (e) {
        console.error('解析用户信息失败', e)
      }
    }
    
    return config
  },
  error => {
    console.log(error)
    Promise.reject(error)
  }
)

// response拦截器
service.interceptors.response.use(
  response => {
    const res = response.data
    
    // 如果返回的状态码不是200，则认为是错误
    if (res.code && res.code !== 200) {
      Message({
        message: res.msg || 'Error',
        type: 'error',
        duration: 5 * 1000
      })
      return Promise.reject(new Error(res.msg || 'Error'))
    } else {
      return res
    }
  },
  error => {
    // 对于404错误（接口未实现），不显示错误提示，只在控制台记录
    if (error.response && error.response.status === 404) {
      console.warn('接口未实现:', error.config.url)
      return Promise.reject(error)
    }
    
    // 其他错误才显示提示
    console.log('err' + error)
    const errorMessage = error.response?.data?.msg || error.message || '请求失败'
    Message({
      message: errorMessage,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service


