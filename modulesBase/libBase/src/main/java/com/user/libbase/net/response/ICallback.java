package com.user.libbase.net.response;

import com.user.libbase.net.exception.ApiException;

public interface ICallback<T> {

    void onSuccess(T data);

    void onFailure(ApiException exception);

}
