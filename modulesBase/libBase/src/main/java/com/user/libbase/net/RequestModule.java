package com.user.libbase.net;

import androidx.lifecycle.LifecycleOwner;

import com.user.libbase.net.exception.ApiException;
import com.user.libbase.net.response.ICallback;
import com.user.libbase.net.response.IResponse;
import com.user.libbase.net.response.ResponseTransformer;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class RequestModule {

    public static <T> void request(Observable<? extends IResponse<T>> observable,
                                   LifecycleOwner owner,
                                   ICallback<T> callback) {
      Disposable disposable =  observable.compose(ResponseTransformer.obtain(owner))
                .subscribe(d->{
                    callback.onSuccess((T) d);
                },throwable -> {
                    callback.onFailure(ApiException.handlerException(throwable));
                });
    }
}
