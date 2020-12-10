package com.hc.essay.joke;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hc.essay.baselibrary.fixbug.FixDexManager;
import com.hc.essay.baselibrary.ioc.CheckNet;
import com.hc.essay.baselibrary.ioc.OnClick;
import com.hc.essay.baselibrary.ioc.ViewById;
import com.hc.essay.baselibrary.ioc.ViewUtils;

import java.io.File;
import java.lang.reflect.Field;

import dalvik.system.BaseDexClassLoader;


public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.tv_test)
    TextView tv_test;

    @ViewById(R.id.hello)
    TextView hello;

    @ViewById(R.id.dex)
    TextView dex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        tv_test.setText("are you ok man ?");
        hello.setText("Yo");

        dex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = 2 / 0;
                Toast.makeText(MainActivity.this, "dex", Toast.LENGTH_SHORT).show();

            }
        });

        try {
            fixDexBug();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    // 通过dex修复bug
    private void fixDexBug() throws NoSuchFieldException, IllegalAccessException {

        File fixFile = new File(Environment.getExternalStorageDirectory(), "fix.dex");
        if (fixFile.exists()) {
            FixDexManager fixDexManager = new FixDexManager(this);
            fixDexManager.fixBug(fixFile.getAbsolutePath());
        }
    }


    @OnClick({R.id.tv_test, R.id.hello})
    void click(View view) {
        switch (view.getId()) {
            case R.id.tv_test:
                Toast.makeText(this, "are you ok", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, TestActivity.class));
                break;
            case R.id.hello:
                Toast.makeText(this, "Yo man", Toast.LENGTH_SHORT).show();
        }
    }

}