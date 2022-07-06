package com.user.ex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.user.ex.service.ApiService;
import com.user.libbase.net.RequestModule;
import com.user.libbase.net.exception.ApiException;
import com.user.libbase.net.response.ICallback;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @Inject
    ApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("cwc", "ApiService :" + mApiService);

        RequestModule.request(mApiService.test(), this, new ICallback<Object>() {
            @Override
            public void onSuccess(Object data) {
                Log.e("cwc","data :"+data.toString());
            }

            @Override
            public void onFailure(ApiException exception) {
                Log.e("cwc","msg :"+exception.getErrorMsg());
            }
        });
    }
}