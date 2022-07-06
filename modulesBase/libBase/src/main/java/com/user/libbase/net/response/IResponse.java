package com.user.libbase.net.response;

public interface IResponse<T> {

    T getData();

    String getMsg();

    String getCode();

    boolean isSuccess();
}
