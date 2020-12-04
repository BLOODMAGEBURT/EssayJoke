package com.hc.essay.joke;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hc.essay.baselibrary.ioc.CheckNet;
import com.hc.essay.baselibrary.ioc.OnClick;
import com.hc.essay.baselibrary.ioc.ViewById;
import com.hc.essay.baselibrary.ioc.ViewUtils;


public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.tv_test)
    TextView tv_test;

    @ViewById(R.id.hello)
    TextView hello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        tv_test.setText("are you ok man ?");
        hello.setText("Yo");


        getInfoFromNet();
    }

    @CheckNet
    private void getInfoFromNet() {
        Toast.makeText(this, "联网获取数据", Toast.LENGTH_LONG).show();
    }

    @OnClick({R.id.tv_test, R.id.hello})
    void click(View view) {
        switch (view.getId()) {
            case R.id.tv_test:
                Toast.makeText(this, "are you ok", Toast.LENGTH_SHORT).show();
                break;
            case R.id.hello:
                Toast.makeText(this, "Yo man", Toast.LENGTH_SHORT).show();
        }
    }

}