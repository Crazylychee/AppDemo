package com.crazylychee.result;

import io.swagger.annotations.ApiModel;

/**
* @author Crazylychee
* @date 2024-07-18
*/
@ApiModel("Http状态码")
public enum HttpStatus implements HttpCode{

    /**
     * 操作成功
     */
    SUCCESS(200, "操作成功"),

    /**
     * 对象创建成功
     */
    CREATED(201, "对象创建成功"),

    /**
     * 请求已经被接受
     */
    ACCEPTED(202, "请求已经被接受"),

    /**
     * 操作已经执行成功，但是没有返回数据
     */
    NO_CONTENT(204, "操作已经执行成功，但是没有返回数据"),

    /**
     * 请求成功，但是业务失败
     */
    FAIL(205, "请求成功，但是业务失败"),

    /**
     * 资源已被移除
     */
    MOVED_PERM(301, "资源已被移除"),

    /**
     * 重定向
     */
    SEE_OTHER(303, "重定向"),

    /**
     * 资源没有被修改
     */
    NOT_MODIFIED(304, "资源没有被修改"),

    /**
     * 参数列表错误（缺少，格式不匹配）
     */
    BAD_REQUESTS(400, "参数列表错误（缺少，格式不匹配）"),

    /**
     * 未授权
     */
    UNAUTHORIZED(401, "未授权"),

    /**
     * 访问受限，授权过期
     */
    FORBIDDEN(403, "访问受限，授权过期"),

    /**
     * 资源，服务未找到
     */
    NOT_FOUND(404, "资源，服务未找到"),

    /**
     * 不允许的http方法
     */
    METHOD_NOT_ALLOWED(405, "不允许的http方法"),

    /**
     * 资源冲突，或者资源被锁
     */
    CONFLICT(409, "资源冲突，或者资源被锁"),

    /**
     * 不支持的数据，媒体类型
     */
    UNSUPPORTED_MEDIA_TYPE(415, "不支持的数据，媒体类型"),

    /**
     * 系统内部错误
     */
    ERROR(500, "系统内部错误"),

    /**
     * 接口未实现
     */
    NOT_IMPLEMENTED(501, "接口未实现");




    private final int code;

    private final String message;

    HttpStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }


    public static HttpCode findByCode(int code) {
        for (HttpCode value : values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        return null;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public int getCode() {
        return this.code;
    }
}

