package com.hc.essay.joke;

import android.app.Application;

import com.hc.essay.baselibrary.CrashHandler;

public class JokeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 捕获异常
        CrashHandler.getInstance().init(this);
    }
}
