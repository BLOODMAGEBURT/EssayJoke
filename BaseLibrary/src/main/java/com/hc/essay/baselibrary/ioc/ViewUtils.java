package com.hc.essay.baselibrary.ioc;

import android.app.Activity;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class ViewUtils {
    // 注入Activity
    public static void inject(Activity activity) {
        //通过反射获取注解值，并赋值给field
        Class<? extends Activity> clz = activity.getClass();
        Field[] declaredFields = clz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            Annotation[] declaredAnnotations = declaredField.getDeclaredAnnotations();

        }
    }

    // 注入View
    public static void inject(View view) {

    }

    // 注入Fragment
    public static void inject(View view, Object object) {

    }


    /**
     * 兼容以上三种方法
     *
     * @param finder
     * @param object 反射需要执行的类
     */
    public static void inject(ViewFinder finder, Object object) {

    }
}
