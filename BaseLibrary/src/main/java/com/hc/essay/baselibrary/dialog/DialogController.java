package com.hc.essay.baselibrary.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseArray;
import android.view.View;
import android.view.Window;
import android.widget.TextView;


class DialogController {


    private BaseDialog baseDialog;
    private Window window;

    public DialogController(BaseDialog baseDialog, Window window) {

        this.baseDialog = baseDialog;
        this.window = window;
    }

    public BaseDialog getBaseDialog() {
        return baseDialog;
    }

    public Window getWindow() {
        return window;
    }


    public static class DialogParams {

        public Context context;
        public int themeResId;
        public boolean mCancelable;
        public DialogInterface.OnCancelListener mOnCancelListener;
        public DialogInterface.OnDismissListener mOnDismissListener;
        public DialogInterface.OnKeyListener mOnKeyListener;
        // 布局view
        public View view;
        // 布局layout
        public int layoutId;
        // 存放多个View的值
        public SparseArray<CharSequence> textArray = new SparseArray<>();
        // 存放多个View的点击事件
        public SparseArray<View.OnClickListener> listenerArray = new SparseArray<>();

        public DialogParams(Context context, int themeResId) {

            this.context = context;
            this.themeResId = themeResId;
        }

        /**
         * 绑定参数，将param中的参数应用的到Controller中
         *
         * @param mAlert
         */
        public void apply(DialogController mAlert) {
            // 1.设置布局
            DialogViewHelper dialogViewHelper = null;
            if (layoutId != 0) {
                dialogViewHelper = new DialogViewHelper(context, layoutId);
            }

            if (view != null) {
                dialogViewHelper = new DialogViewHelper(view);
            }
            if (dialogViewHelper == null) {
                throw new IllegalArgumentException("请调用setContentView(view or layoutId)");
            }


            mAlert.getBaseDialog().setContentView(dialogViewHelper.getContentView());

            // 2.设置文本
            for (int i = 0; i < textArray.size(); i++) {
                dialogViewHelper.setText(textArray.keyAt(i),textArray.valueAt(i));
            }


            // 3.设置点击事件
            for (int i = 0; i < listenerArray.size(); i++) {
                dialogViewHelper.setListener(listenerArray.keyAt(i),listenerArray.valueAt(i));
            }

            // 4.配置自定义的效果  全屏 底部弹出  默认动画
        }
    }
}

