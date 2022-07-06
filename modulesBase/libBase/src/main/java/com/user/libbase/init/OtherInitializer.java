package com.user.libbase.init;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import com.user.libbase.utils.LogUtil;
import com.user.libbase.utils.PropertiesUtil;

import java.util.List;

public class OtherInitializer implements Initializer<Object> {
    @NonNull
    @Override
    public Object create(@NonNull Context context) {
        return null;
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return null;
    }
}
