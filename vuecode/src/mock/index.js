import Mock from "mockjs";

// 设置延迟时间
// Mock.setup({
//   timeout: 4000
// })


Mock.mock('http://localhost:8087/user/login', {
  "code": 200,
  "msg": "login",
  "data": {
    "accessToken": "ssssssss",
    "username": "mock",
    "identity": "teacher"
  }
})


Mock.mock('http://localhost:8087/user/info', {
  "code": 200,
  "msg": "info",
  "data": {
    "name": "user",
    "avatar": "src/assets/404_images/404.png",
    "identity": "teacher"
  }
})

Mock.mock('http://localhost:8087/user/register', {
  "code": 200,
  "msg": "register",
  "data": {
  }
})

Mock.mock('http://localhost:8087/user/logout', {
  "code": 200,
  "msg": "logout",
  "data": {
  }
})

Mock.mock('http://localhost:8087/user/updateAvatar', {
  "code": 200,
  "msg": "更新图像",
  "data": {
  }
})