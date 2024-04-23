package com.example.jmgexamsys03.domain.enums;

/**
 * 存放所有响应状态码的枚举类。
 * 使用方法见ResponseResult
 */
public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200, "操作成功"),
    // 登录
    NEED_LOGIN(401, "需要登录后操作"),
    NO_OPERATOR_AUTH(403, "无权限操作"),
    SYSTEM_ERROR(500, "出现错误"),
    USERNAME_EXIST(501, "用户名已存在"),
    PHONENUMBER_EXIST(502, "手机号已存在"), EMAIL_EXIST(503, "邮箱已存在"),
    REQUIRE_USERNAME(504, "必需填写用户名"),
    CONTENT_NOT_NULL(506, "评论内容不能为空"),
    FILE_TYPE_ERROR(507, "文件类型错误"),
    USERNAME_NOT_NULL(508, "用户名不能为空"),
    NICKNAME_NOT_NULL(509, "昵称不能为空"),
    PASSWORD_NOT_NULL(510, "密码不能为空"),
    EMAIL_NOT_NULL(511, "邮箱不能为空"),
    NICKNAME_EXIST(512, "昵称已存在"),
    LOGIN_ERROR(505, "用户名或密码错误"),
    ROLE_NOT_EXIST(520,"教师或学生档案不存在"),
    ROLE_REGISTERED(521,"档案已被注册"),
    COURSE_NOT_EXIST(522,"课程不存在"),
    COURSE_FULL(522,"课程人数已达上限！"),
    COURSE_SELECTED(523,"你已经选择了此课程！"),
    COURSE_REPETION(524,"课程名称重复！");


    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage) {
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

