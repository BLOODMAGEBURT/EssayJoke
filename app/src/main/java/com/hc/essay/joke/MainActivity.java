package com.hc.essay.joke;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


    //    @Bind(R.id.tv_test)
    TextView tv_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ButterKnife.bind(this);
        tv_test.setText("are you ok ???");

    }

    //    @OnClick(R.id.tv_test)
    void submit() {
        // TODO call server...
        Toast.makeText(this, "are you ok", Toast.LENGTH_SHORT).show();
    }
}