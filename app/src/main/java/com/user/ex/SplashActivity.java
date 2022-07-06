package com.user.ex;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.user.common.constant.ARoutePath;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(this::skip, 1500);
    }


    private void skip() {
        ARouter.getInstance().build(ARoutePath.MAIN_ACTIVITY).navigation();
        finish();
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
    }
}