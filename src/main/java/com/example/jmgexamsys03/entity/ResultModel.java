package com.example.jmgexamsys03.entity;

import java.util.List;

public class ResultModel {
    public static ResultModel build(int status, String msg, Object data) {
        return new ResultModel(status, msg, data);
    }
    public static ResultModel build(int status, String msg) {
        return new ResultModel(status, msg, null);
    }

    /**
     * ok:http请求成功，例如导入学生名单成功，返回200. <br/>
     * @author Beats
     * @param
     * @return ResultModel
     */
    public static ResultModel ok() {
        return new ResultModel();
    }

    /**
     * ok:http请求获取某些信息，例如获取考生名单，返回200和考生名单的json数组. <br/>
     * @author Beats
     * @param data
     * @return ResultModel
     */
    public static ResultModel ok(Object data) {
        return new ResultModel(200, "ok", data);
    }

    private ResultModel() {
        this.status = 200;
        this.msg = "ok";
    }
    private ResultModel(int status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 返回状态码
     * 默认：正常：200
     * 异常：500
     */
    private int status;
    /**
     * 返回消息
     * 默认："ok"
     */
    private String msg;
    /**
     * 返回消息值
     * 默认：null
     */
    private Object data;
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }

    /**
     * @Description:(获取String值，如果Data是String格式，请用此方法获取值). <br/>
     * @return
     */

    public String getStringData() {
        return (String) data;
    }

    @SuppressWarnings("unchecked")

    public <T> T getPojoData(Class<T> clazz) {
        return (T) this.data;
    }

    @SuppressWarnings("unchecked")

    public <T> List<T> getListData(Class<T> clazz) {
        return (List<T>) this.data;
    }
}
