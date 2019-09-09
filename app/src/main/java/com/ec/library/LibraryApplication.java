package com.ec.library;

import android.app.Application;

import com.ec.library.utils.Alert;

public class LibraryApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Alert.build(this);
    }
}
