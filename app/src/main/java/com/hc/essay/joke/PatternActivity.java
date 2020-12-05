package com.hc.essay.joke;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;

/**
 * 设计模式的测试研究类
 */
public class PatternActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern);

        // 研究AsyncTask类的源码
        infoAsyncTask();

        // 研究自定义View的源码
        infoView();
    }

    private void infoAsyncTask() {
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute("12");
    }

    private void infoView() {

    }

    /**
     * 步骤1：创建AsyncTask子类
     * 注：
     * a. 继承AsyncTask类
     * b. 为3个泛型参数指定类型；若不使用，可用java.lang.Void类型代替
     * 此处指定为：输入参数 = String类型、执行进度 = Integer类型、执行结果 = String类型
     * c. 根据需求，在AsyncTask子类内实现核心方法
     */
    public static class MyAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }
    }

}