package com.zyj.studyapp.net;

public enum  HttpCode {
    SUCCEED (0,"返回成功"),
    ANALYSIS(-101,"数据解析错误"),
    ERROR_404 (404,"无网络"),
    FAIL(1,"")//自定义字段
    ;

    private int code;
    private String message;
    HttpCode(int code,String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "HttpCode{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
