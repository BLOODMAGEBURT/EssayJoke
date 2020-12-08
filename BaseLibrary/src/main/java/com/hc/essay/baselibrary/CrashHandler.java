package com.hc.essay.baselibrary;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    // 饿汉式单例模式
    private static final CrashHandler CRASH_HANDLER = new CrashHandler();

    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultExceptionHandler;

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return CRASH_HANDLER;
    }

    public void init(Context context) {
        this.mContext = context;
        //获取默认的系统异常捕获器
        this.mDefaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();

        //把当前的crashHandler捕获器设置成默认的crash捕获器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
        // 我们处理异常
//        handleExceptionByMyself(t, e);
        Log.d("aaa", "出异常了");
        //系统默认处理异常
//        mDefaultExceptionHandler.uncaughtException(t, e);
    }


    private void handleExceptionByMyself(Thread t, Throwable e) {
        Log.d("aaa", "出异常了");
    }
}
