package com.user.libbase.init;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import com.user.libbase.utils.LogUtil;
import com.user.libbase.utils.PropertiesUtil;

import java.util.ArrayList;
import java.util.List;

public class LabBaseInitializer implements Initializer<Object> {
    @NonNull
    @Override
    public Object create(@NonNull Context context) {
        PropertiesUtil.init(context);
        String tag = PropertiesUtil.getProperties("tag");
        String isShowLog = PropertiesUtil.getProperties("isShowLog");
        LogUtil.init(TextUtils.isEmpty(tag) ? "TAG" : tag, TextUtils.equals(isShowLog, "false"));
        return null;
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        List<Class<? extends Initializer<?>>> list = new ArrayList<>();
        list.add(OtherInitializer.class);
        return list;
    }
}
