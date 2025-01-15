package com.crazylychee.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


@ApiModel(description = "统一结果返回集")
public class JSONResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "返回状态码", example = "200")
    private Integer code = 200;

    @ApiModelProperty(value = "返回消息", example = "Success")
    private String message = "操作成功";

    @ApiModelProperty(value = "是否展示提示信息", example = "true")
    private boolean showMessage = false;

    @ApiModelProperty(value = "返回数据")
    private T data = null;

    public static <T> JSONResult<T> success(){
        return new JSONResult<>();
    }

    public static <T> JSONResult<T> success(boolean showMessage){
        JSONResult<T> instance = new JSONResult<>();
        instance.setShowMessage(showMessage);
        return instance;
    }

    public static <T> JSONResult<T> success(T obj){
        JSONResult<T> instance = new JSONResult<>();
        instance.setData(obj);
        return instance;
    }

    public static <T> JSONResult<T> success(T obj, boolean showMessage){
        JSONResult<T> instance = new JSONResult<>();
        instance.setData(obj);
        instance.setShowMessage(showMessage);
        return instance;
    }


    public static <T> JSONResult<T> success(T obj, Integer code){
        JSONResult<T> instance = new JSONResult<>();
        instance.setCode(code);
        instance.setData(obj);
        return instance;
    }

    public static <T> JSONResult<T> success(Integer code, String message){
        JSONResult<T> instance = new JSONResult<>();
        instance.setCode(code);
        instance.setMessage(message);
        return instance;
    }


    public static <T> JSONResult<T> error(){
        JSONResult<T> instance = new JSONResult<>();
        instance.setCode(HttpStatus.ERROR.getCode());
        instance.setMessage("系统发生异常，请稍后再试！");
        return instance;
    }


    public static <T> JSONResult<T> error(String message){
        JSONResult<T> instance = new JSONResult<>();
        instance.setCode(HttpStatus.ERROR.getCode());
        instance.setMessage(message);
        return instance;
    }

    public static <T> JSONResult<T> error(String message, T obj){
        JSONResult<T> instance = new JSONResult<>();
        instance.setCode(HttpStatus.ERROR.getCode());
        instance.setMessage(message);
        instance.setData(obj);
        return instance;
    }

    public static <T> JSONResult<T> error(Integer code, String message){
        JSONResult<T> instance = new JSONResult<>();
        instance.setCode(code);
        instance.setMessage(message);
        return instance;
    }


    //以下方法用于支持HttpStatus枚举
    public static <T> JSONResult<T> success(HttpStatus httpStatus, String message, T data) {
        JSONResult<T> instance = new JSONResult<>();
        instance.setCode(httpStatus.getCode());
        instance.setMessage(message);
        instance.setData(data);
        return instance;
    }

    public static <T> JSONResult<T> success(HttpStatus httpStatus, T data) {
        JSONResult<T> instance = new JSONResult<>();
        instance.setCode(httpStatus.getCode());
        instance.setMessage(httpStatus.getMessage());
        instance.setData(data);
        return instance;
    }

    public static <T> JSONResult<T> error(HttpStatus httpStatus) {
        JSONResult<T> instance = new JSONResult<>();
        instance.setCode(httpStatus.getCode());
        instance.setMessage(httpStatus.getMessage());
        return instance;
    }

    public static <T> JSONResult<T> error(HttpStatus httpStatus, String message) {
        JSONResult<T> instance = new JSONResult<>();
        instance.setCode(httpStatus.getCode());
        instance.setMessage(message);
        return instance;
    }

    public static <T> JSONResult<T> error(HttpStatus httpStatus, String message, T data) {
        JSONResult<T> instance = new JSONResult<>();
        instance.setCode(httpStatus.getCode());
        instance.setMessage(message);
        instance.setData(data);
        return instance;
    }



    public static boolean hasLength(String str) {
        return org.springframework.util.StringUtils.hasLength(str);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isShowMessage() {
        return showMessage;
    }

    public void setShowMessage(boolean showMessage) {
        this.showMessage = showMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "JSONResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", showMessage=" + showMessage +
                ", data=" + data +
                '}';
    }
}
