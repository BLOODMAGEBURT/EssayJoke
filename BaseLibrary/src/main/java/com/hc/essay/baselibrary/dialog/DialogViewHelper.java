package com.hc.essay.baselibrary.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * view的辅助类,去掉public只能包内使用
 */
class DialogViewHelper {
    private View contentView;

    public DialogViewHelper(Context context, int layoutId) {
        contentView = LayoutInflater.from(context).inflate(layoutId, null);
    }


    public DialogViewHelper(View view) {
        this.contentView = view;
    }


    public View getContentView() {
        return contentView;
    }
}
