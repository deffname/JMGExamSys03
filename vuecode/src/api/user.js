import request from '@/utils/request'

export function login(data) {
  console.log("登录login被触发，将要发送请求,data = ", data);
  return request({
    url: '/user/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/vuecode/user/info',
    method: 'get',
    // 适用于get请求，因为get请求的请求体通常不被使用
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/vuecode/user/logout',
    method: 'post'
  })
}
