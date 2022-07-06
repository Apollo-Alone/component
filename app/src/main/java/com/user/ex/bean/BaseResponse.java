package com.user.ex.bean;

import com.user.libbase.net.response.IResponse;

public class BaseResponse<T> implements IResponse<T> {

    int code;
    T data;
    String msg;


    public void setCode(int code) {
        this.code = code;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public String getCode() {
        return String.valueOf(code);
    }

    @Override
    public boolean isSuccess() {
        return code == 0 ? true : false;
    }
}
