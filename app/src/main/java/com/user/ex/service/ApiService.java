package com.user.ex.service;

import com.user.ex.bean.BaseResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {

    @GET("/article/list/0/json")
    Observable<BaseResponse<Object>> test();
}
