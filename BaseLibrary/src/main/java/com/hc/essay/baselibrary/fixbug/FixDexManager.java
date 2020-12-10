package com.hc.essay.baselibrary.fixbug;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import dalvik.system.BaseDexClassLoader;
import dalvik.system.DexClassLoader;

/**
 * 通过dex合并，进行热修复，热更新
 */
public class FixDexManager {

    private final Context mContext;
    private File mDexDir;  // dex文件路径

    public FixDexManager(Context context) {

        this.mContext = context;
        this.mDexDir = context.getDir("odex", Context.MODE_PRIVATE);
    }

    // 修复bug,通过dex包
    public void fixBug(String dexFilePath) throws NoSuchFieldException, IllegalAccessException, IOException {
        // 1. 获取运行中的DexElements
        ClassLoader classLoader = mContext.getClassLoader();
        Object appDexElements = getDexElementsByClassloader(classLoader);

        // 2. 获取修复用的DexElements

        File src = new File(dexFilePath);
        File dest = new File(mDexDir, src.getName());

        if (!src.exists()) {
            throw new FileNotFoundException(dexFilePath);
        }
        if (dest.exists()) {
            Log.d("fixbug", "dest已经加载过了");
            return;
        }

        File optimizedDirectory = new File(mDexDir, "odex");

        if (!optimizedDirectory.exists()) {
            optimizedDirectory.mkdirs();
        }


        // 2.1 将下载下来的fix.dex挪到应用可以访问的 dex目录下
        copyFile(src, dest);
        ArrayList<File> dexFiles = new ArrayList<>();
        dexFiles.add(dest);
        for (File dexFile : dexFiles) {
            // 使用classLoader加载

            DexClassLoader dexClassLoader = new DexClassLoader(
                    dexFile.getAbsolutePath(),
                    optimizedDirectory.getAbsolutePath(),
                    null,
                    classLoader);
            Object fixDexElements = getDexElementsByClassloader(dexClassLoader);

            // 3. 合并两个Elements, 重新赋值给系统appDexElements
            appDexElements = combineArray(fixDexElements, appDexElements);
        }


        // 4. 将合并后的Elements重新注入进系统中
        injectDexElements(classLoader, appDexElements);
    }

    /**
     * 重新注入
     *
     * @param classLoader
     * @param appDexElements
     */
    private void injectDexElements(ClassLoader classLoader, Object appDexElements) throws NoSuchFieldException, IllegalAccessException {

        Class<? extends ClassLoader> clz = BaseDexClassLoader.class;
        Field pathList = clz.getDeclaredField("pathList");
        pathList.setAccessible(true);

        Object pathListObj = pathList.get(classLoader);

        // 根据pathList获取dexElements
        Field dexElements = pathListObj.getClass().getDeclaredField("dexElements");

        dexElements.setAccessible(true);
        dexElements.set(pathListObj, appDexElements);
    }


    /**
     * 使用FileChannel复制 Java NIO包括transferFrom方法,根据文档应该比文件流复制的速度更快
     *
     * @param src
     * @param dest
     * @throws IOException
     */
    private void copyFile(File src, File dest) throws IOException {
        FileChannel sourceChannel = null;
        FileChannel destChannel = null;
        try {
            sourceChannel = new FileInputStream(src).getChannel();
            destChannel = new FileOutputStream(dest).getChannel();
            destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
        } finally {
            if (sourceChannel != null) {
                sourceChannel.close();
            }
            if (destChannel != null) {
                destChannel.close();
            }
        }
    }


    /**
     * 合并两个dexElements数组
     *
     * @param arrayLhs
     * @param arrayRhs
     * @return
     */
    private static Object combineArray(Object arrayLhs, Object arrayRhs) {
        Class<?> localClass = arrayLhs.getClass().getComponentType();
        int i = Array.getLength(arrayLhs);
        int j = i + Array.getLength(arrayRhs);
        Object result = Array.newInstance(localClass, j);
        for (int k = 0; k < j; ++k) {
            if (k < i) {
                Array.set(result, k, Array.get(arrayLhs, k));
            } else {
                Array.set(result, k, Array.get(arrayRhs, k - i));
            }
        }
        return result;
    }

    private Object getDexElementsByClassloader(ClassLoader classLoader) throws NoSuchFieldException, IllegalAccessException {

        // 先获取 pathList
        Class<DexClassLoader> clz = DexClassLoader.class;
        Field pathList = clz.getDeclaredField("pathList");

        pathList.setAccessible(true);
        Object pathListObj = pathList.get(classLoader);

        // 根据pathList获取dexElements
        Field dexElements = pathListObj.getClass().getDeclaredField("dexElements");

        dexElements.setAccessible(true);

        return dexElements.get(pathListObj);
    }
}
