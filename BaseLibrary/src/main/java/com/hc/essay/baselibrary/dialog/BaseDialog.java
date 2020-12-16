package com.hc.essay.baselibrary.dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.DialogCompat;

import com.hc.essay.baselibrary.R;

public class BaseDialog extends Dialog {
    public DialogController mAlert;

    // 不让直接New对象
    private BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mAlert = new DialogController(this, getWindow());
    }


    public static class Builer {
        private final DialogController.DialogParams P;

        public Builer(Context context) {
            // 默认实现
            this(context, R.style.dialog);

        }

        public Builer(Context context, int themeResId) {
            P = new DialogController.DialogParams(context, themeResId);
        }


        public Builer setContentView(View view) {
            P.view = view;
            P.layoutId = 0;
            return this;
        }

        public Builer setContentView(int layoutId) {
            P.view = null;
            P.layoutId = layoutId;
            return this;
        }


        // 设置文本
        public Builer setText(int viewId, CharSequence text) {
            P.textArray.put(viewId, text);
            return this;
        }


        // 设置点击事件
        public Builer setOnClickListener(int viewId, View.OnClickListener onClickListener) {
            P.listenerArray.put(viewId, onClickListener);
            return this;
        }

        // 设置点击阴影能否取消
        public Builer setCancelable(boolean cancelable) {
            P.mCancelable = cancelable;
            return this;
        }


        private BaseDialog create() {
            // Context has already been wrapped with the appropriate theme.
            final BaseDialog dialog = new BaseDialog(P.context, P.themeResId);
            P.apply(dialog.mAlert);
            dialog.setCancelable(P.mCancelable);
            if (P.mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            dialog.setOnCancelListener(P.mOnCancelListener);
            dialog.setOnDismissListener(P.mOnDismissListener);
            if (P.mOnKeyListener != null) {
                dialog.setOnKeyListener(P.mOnKeyListener);
            }
            return dialog;
        }

        public BaseDialog show() {
            final BaseDialog dialog = create();
            dialog.show();
            return dialog;
        }
    }
}

