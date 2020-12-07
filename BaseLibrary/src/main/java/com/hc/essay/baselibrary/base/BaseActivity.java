package com.hc.essay.baselibrary.base;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hc.essay.baselibrary.ioc.ViewUtils;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 1.设置布局
        setContentView();

        // 一些特定到算法
        ViewUtils.inject(this);

        // 2. 设置头部
        initTitle();

        //3. 初始化view
        initView();

        //4. 初始化数据
        initData();
    }

    protected abstract void setContentView();

    protected abstract void initTitle();

    protected abstract void initView();

    protected abstract void initData();

    protected void startActivity(Class<?> clz) {
        Intent intent = new Intent(this, clz);
        startActivity(intent);
    }

}
