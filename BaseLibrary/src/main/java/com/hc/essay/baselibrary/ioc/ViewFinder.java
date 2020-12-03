package com.hc.essay.baselibrary.ioc;

import android.app.Activity;
import android.view.View;

/*
 *findViewById的辅助类
 * */
public class ViewFinder {

    private Activity mActivity;
    private View mView;

    public ViewFinder(Activity activity) {
        this.mActivity = activity;
    }

    public ViewFinder(View view) {
        this.mView = view;
    }

    // findViewById
    public View findViewById(int viewId) {

        return (mActivity != null) ? mActivity.findViewById(viewId) : mView.findViewById(viewId);
    }


}
