package com.user.libbase.utils;

import com.google.gson.Gson;

import java.lang.ref.WeakReference;

public class GsonUtil {
    private static WeakReference<Gson> sGsonInstance;

    private GsonUtil() {
    }

    public static Gson get() {
        if (sGsonInstance == null || sGsonInstance.get() == null) {
            Gson gson = new Gson();
            sGsonInstance = new WeakReference<>(gson);
        }
        return sGsonInstance.get();
    }
}
