package com.user.libbase.net.response;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import com.user.libbase.net.exception.ApiException;
import com.user.libbase.utils.ReflectUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 数据处理 由Observable(IResponse<T> -----> Observable<T>)类型
 * 实现:
 * 1:对线程的切换 Transformer操作符代码复用
 * 2：RxJava的声明周期进行管理 防止内存泄漏
 * 3：对响应数据统一处理，获取到真正的数据
 *
 * @param <T>
 */
public class ResponseTransformer<T> implements ObservableTransformer<IResponse<T>, T>, LifecycleObserver {

    final CompositeDisposable mDisposable = new CompositeDisposable();

    /*绑定RxJava的生命周期*/
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy() {
        if (!mDisposable.isDisposed()) {
            mDisposable.isDisposed();
        }
    }

    @Override
    public ObservableSource<T> apply(@NonNull Observable<IResponse<T>> upstream) {
        return upstream.doOnSubscribe(disposable -> {
            mDisposable.add(disposable);
        }).onErrorResumeNext(throwable -> {
            return Observable.error(ApiException.handlerException(throwable));
        }).flatMap(tiResponse -> {
            //对响应数据统一处理
            if (tiResponse.isSuccess()) {
                if (tiResponse.getData() != null) {
                    return Observable.just(tiResponse.getData());
                } else {
                    //业务请求可能成功了 但是data是null
                    Class<?> clazz = ReflectUtil.analysisClassInfo(tiResponse);
                    T o = (T) clazz.newInstance();
                    return Observable.just(o);

                }
            }
            return Observable.error(new ApiException(tiResponse.getCode(), tiResponse.getMsg()));
        }).subscribeOn(Schedulers.io())//指定事件产生的线程（请求的线程）
                .observeOn(AndroidSchedulers.mainThread());//响应的线程
    }

    /**
     * 对外提供获取方法
     *
     * @param owner
     * @param <T>
     * @return
     */
    public static <T> ResponseTransformer<T> obtain(LifecycleOwner owner) {
        ResponseTransformer<T> transformer = new ResponseTransformer<>();
        owner.getLifecycle().addObserver(transformer);
        return transformer;
    }
}
