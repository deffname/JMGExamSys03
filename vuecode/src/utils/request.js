import axios from 'axios'
import { MessageBox, Message } from 'element-ui'
import store from '@/store'
import { getToken } from '@/utils/auth'

// 创建一个axios的实例
const service = axios.create({
  // 设置所有请求的基本url
  baseURL: "http://localhost:8087", // url = base url + request url
  // baseURL: "http://127.0.0.1:4523/m1/4266312-0-default",
  // 设置请求的超时时间是5s
  timeout: 5000 // request timeout
})

// 添加了一个请求拦截器，这个拦截器会在发送请求之前被调用
service.interceptors.request.use(
  config => {
    // do something before request is sent
    console.log("请求拦截器被触发")
    if (store.getters.token) {
      // 检查是否有一个token存在
      // 如果存在token，就把token加在请求头里面，下面是一个自定义的headers键
      config.headers['token'] = getToken()
    }
    // 返回修改（可能）过后的请求
    console.log('发送出去的请求为', config);
    return config
  },
  error => {
    // do something with request error
    console.log("出错位置: request第30行");
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(
  /**
   * If you want to get http information such as headers or status
   * Please return  response => response
  */

  /**
   * Determine the request status by custom code
   * Here is just an example
   * You can also judge the status by HTTP Status Code
   */
  response => {
    // 从响应对象中提取数据部分
    const res = response.data
    console.log("请求成功，这是本次请求返回的结果:", res);

    // 如果响应的状态码不为500，认为是一个错误响应
    if (res.code !== 200) {
      // 是错误响应就显示出对应的提示
      Message({
        message: res.msg || 'Error',
        type: 'error',
        duration: 5 * 1000
      })

      // 一些特殊的错误码
      // 50008: Illegal token; 50012: Other clients logged in; 50014: Token expired;
      if (res.code === 50008 || res.code === 50012 || res.code === 50014) {
        // to re-login
        MessageBox.confirm('You have been logged out, you can cancel to stay on this page, or log in again', 'Confirm logout', {
          confirmButtonText: 'Re-Login',
          cancelButtonText: 'Cancel',
          type: 'warning'
        }).then(() => {
          store.dispatch('user/resetToken').then(() => {
            location.reload()
          })
        })
      }
      return Promise.reject(new Error(res.msg || 'Error'))
    } else {
      // 是正确响应，直接返回响应的结果
      return res
    }
  },
  error => {
    // 如果请求失败，这个拦截器函数将会被调用
    console.log("这是请求失败了");
    console.log('err' + error) // for debug
    Message({
      message: error.msg,
      type: 'error',
      duration: 5 * 1000
    })
    // 返回一个原始的错误对象
    return Promise.reject(error)
  }
)

export default service
