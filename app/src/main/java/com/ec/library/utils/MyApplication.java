package com.ec.library.utils;

import android.app.Application;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Alert.build(this);
    }
}
