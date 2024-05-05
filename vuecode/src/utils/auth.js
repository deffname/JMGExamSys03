// 这个js文件简单封装了对cookie的操作函数
import Cookies from 'js-cookie'

// 定义一个常量，用于指定在cookie中存储token的键名
const TokenKey = 'vuecode_token'

// 获取存储在cookie中的token值，如果没有对应的 Cookie 则返回 undefined
export function getToken() {
  return Cookies.get(TokenKey)
}

// 设置存储在cookie中的token值
export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

// 从cookie中移除token值
export function removeToken() {
  return Cookies.remove(TokenKey)
}
