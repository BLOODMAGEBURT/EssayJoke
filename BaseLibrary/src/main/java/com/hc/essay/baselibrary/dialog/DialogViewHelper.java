package com.hc.essay.baselibrary.dialog;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * view的辅助类,去掉public只能包内使用
 */
class DialogViewHelper {
    private View contentView;
    // view缓存数组
    private final SparseArray<View> viewCaches = new SparseArray<>();

    public DialogViewHelper(Context context, int layoutId) {
        contentView = LayoutInflater.from(context).inflate(layoutId, null);
    }


    public DialogViewHelper(View view) {
        this.contentView = view;
    }


    public View getContentView() {
        return contentView;
    }

    // 给View设置文本
    public void setText(int viewId, CharSequence value) {
        TextView view = getView(viewId);
        view.setText(value);
    }

    // 根据id获取view
    private <T extends View> T getView(int viewId) {
        // 使用缓存数组
        View view = viewCaches.get(viewId);
        if (view != null) {
            return (T) view;
        }
        view = contentView.findViewById(viewId);
        if (view == null) {
            throw new IllegalArgumentException("viewId未找到");
        }
        viewCaches.put(viewId, view);
        return (T) view;
    }


    public void setListener(int viewId, View.OnClickListener listener) {
        getView(viewId).setOnClickListener(listener);
    }
}
