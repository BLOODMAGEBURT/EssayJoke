package com.hc.essay.baselibrary.ioc;

import android.app.Activity;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ViewUtils {

    // 注入Activity
    public static void inject(Activity activity) {
        inject(new ViewFinder(activity), activity);
    }

    // 注入View
    public static void inject(View view) {
        inject(new ViewFinder(view), view);
    }

    // 注入Fragment
    public static void inject(View view, Object object) {
        inject(new ViewFinder(view), object);
    }


    /**
     * 兼容以上三种方法
     *
     * @param finder 实例
     * @param object 反射需要执行的类
     */
    public static void inject(ViewFinder finder, Object object) {

        injectField(finder, object);
        injectEvent(finder, object);
    }


    // 注入变量
    private static void injectField(ViewFinder finder, Object object) {
        //1. 通过反射获取field
        Class<?> clz = object.getClass();
        Field[] declaredFields = clz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            ViewById annotation = declaredField.getAnnotation(ViewById.class);
            if (annotation == null) {
                continue;
            }

            // 2.获取有viewById注解的field,并取出里面的值

            int viewId = annotation.value();
            // 3. findViewById找到view
            View viewById = finder.findViewById(viewId);
            if (viewById == null) {
                // 可以抛一个找不到类的异常
                continue;
            }

            // 4. 反射赋值
            declaredField.setAccessible(true);
            try {
                declaredField.set(object, viewById);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    // 注入事件
    public static void injectEvent(ViewFinder finder, Object object) {
        // 1. 获取对象的所有方法
        Class<?> aClass = object.getClass();
        Method[] methods = aClass.getDeclaredMethods();
        for (Method method : methods) {
            // 2. 获取方法中的注解以及注解的值
            OnClick onClick = method.getAnnotation(OnClick.class);
            if (onClick != null) {
                int[] values = onClick.value();
                for (int value : values) {
                    // 3. 找到view
                    View viewById = finder.findViewById(value);
                    // 4. setOnClickListener, 会回调onClick方法
                    viewById.setOnClickListener(new MyOnClickListener(method, object));
                }
            }
        }
    }

    public static class MyOnClickListener implements View.OnClickListener {
        private final Method method;
        private final Object object;

        public MyOnClickListener(Method method, Object object) {
            this.method = method;
            this.object = object;
        }

        @Override
        public void onClick(View v) {
            // 回调onClick方法

            // 5. 回调时反射执行方法
            try {
                method.setAccessible(true);
                method.invoke(object, v);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }


}
