import Mock from "mockjs";

// 设置延迟时间
// Mock.setup({
//   timeout: 4000
// })


Mock.mock('http://localhost:8087/user/login', {
  "code": 500,
  "message": "success",
  "data": {
    "token": "ssssssss"
  }
})


Mock.mock('http://localhost:8087/user/info', 'get', {
  "code": 500,
  "message": "success",
  "data": {
    "name": "admin",
    "avatar": "src/assets/404_images/404.png"
  }
})