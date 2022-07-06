package com.user.libbase.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

    private static Properties sProperties;

    public static void init(Context context) {
        sProperties = new Properties();
        try {
            InputStream is = context.getAssets().open("appConfig.properties");
            sProperties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperties(String key) {
        return sProperties.getProperty(key);
    }
}
