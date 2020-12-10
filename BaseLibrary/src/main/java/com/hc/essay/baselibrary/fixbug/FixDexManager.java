package com.hc.essay.baselibrary.fixbug;

import android.content.Context;

import java.lang.reflect.Field;

import dalvik.system.BaseDexClassLoader;
import dalvik.system.DexClassLoader;

/**
 * 通过dex合并，进行热修复，热更新
 */
public class FixDexManager {

    private final Context mContext;

    public FixDexManager(Context context) {
        this.mContext = context;
    }

    // 修复bug,通过dex包
    public void fixBug(String dexFilePath) throws NoSuchFieldException, IllegalAccessException {
        // 1. 获取运行中的DexElements
        Class<BaseDexClassLoader> baseDexClassLoaderClass = BaseDexClassLoader.class;
        Object appDexElements = getDexElementsByClassloader(baseDexClassLoaderClass);

        // 2. 获取修复用的DexElements

          // 2.1 将下载下来的fix.dex挪到应用可以访问的地址

//        new DexClassLoader(dexFilePath, )


        // 3. 合并两个Elements

        // 4. 将合并后的Elements重新注入进系统中
    }


    private Object getDexElementsByClassloader(Class<?> clz) throws NoSuchFieldException, IllegalAccessException {

        // 先获取 pathList
        Field pathList = clz.getDeclaredField("pathList");

        pathList.setAccessible(true);
        Object pathListObj = pathList.get(clz);

        // 根据pathList获取dexElements
        Field dexElements = pathListObj.getClass().getDeclaredField("dexElements");

        dexElements.setAccessible(true);

        return dexElements.get(pathListObj);
    }
}
