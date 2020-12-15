package com.hc.essay.joke;

import android.view.View;
import android.widget.Toast;

import com.burt.framelibrary.BaseSkinActivity;
import com.hc.essay.baselibrary.dialog.BaseDialog;

public class TestActivity extends BaseSkinActivity {

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_test);

    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
//        showDialog();

    }

    private void showDialog() {
        new BaseDialog.Builer(this)
                .setContentView(R.layout.detail_dialog)
                .setOnClickListener(R.id.weibo, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(TestActivity.this, "weibo弹弹弹", Toast.LENGTH_SHORT).show();
                    }
                })
                .setOnClickListener(R.id.weixin, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(TestActivity.this, "weixin弹弹弹", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }
}