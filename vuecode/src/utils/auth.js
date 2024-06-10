// 这个js文件简单封装了对cookie的操作函数
import Cookies from 'js-cookie'

// 定义一个常量，用于指定在cookie中存储token的键名
const TokenKey = 'vuecode_token'

// 获取存储在cookie中的token值，如果没有对应的 Cookie 则返回 undefined
export function getToken(username) {
  return Cookies.get(TokenKey + "_" + username)
}

// 获取存储在cookie中的token值，如果没有对应的 Cookie 则返回 undefined
export function getdefaultToken() {
  return Cookies.get(TokenKey)
}

// 设置存储在cookie中的token值
export function setToken(username, token) {
  console.log('设置token的键为', username);
  if (username === undefined) {
    Cookies.set(TokenKey, token)
  }

  return Cookies.set(TokenKey + "_" + username, token)
}

// 从cookie中移除token值
export function removeToken(username) {
  Cookies.remove(TokenKey)
  return Cookies.remove(TokenKey + "_" + username)
}
