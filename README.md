# 配置
## jdk1.8 java8
## spring boot 2.x
## vue2 

token:https://www.cnblogs.com/Jason-Xiang/p/9808596.html
<br>
使用vue-image-crop-upload图像上传问题：https://blog.csdn.net/bu_xiang_tutou/article/details/127907941

# 后端开发流程
从controller下面接收数据  
controller调用UserService包下面的服务  
UserService调用Mapper中的对象对数据库进行操作  
UserService返回给controller一个ResponseResult对象，这个对象再由controller返回给前端