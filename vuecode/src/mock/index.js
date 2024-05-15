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


Mock.mock('http://localhost:8087/user/info', {
  "code": 500,
  "message": "success",
  "data": {
    "name": "user",
    "avatar": "src/assets/404_images/404.png",
    "userrole": "teacher"
  }
})

Mock.mock('http://localhost:8087/user/register', {
  "code": 500,
  "message": "success",
  "data": {
  }
})

Mock.mock('http://localhost:8087/user/logout', {
  "code": 500,
  "message": "success",
  "data": {
  }
})