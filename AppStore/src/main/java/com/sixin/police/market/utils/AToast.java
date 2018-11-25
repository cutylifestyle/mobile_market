package com.sixin.police.market.utils;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lehand.appstore.library.toast.TastyToast;
import com.sixin.police.market.BuildConfig;
import com.sixin.police.market.R;
import com.sixin.police.market.constants.Constants;

/**
 * Created by malx on 2018/11/14
 */
public class AToast {

    private static volatile Toast mToast = null;

    private static Activity mContext = null;
    private static View mToastView = null;
    private static TextView mTextView = null;

    public static synchronized void show(final String message){
        mContext = AppUtils.getTopActivity();
        if (mContext != null) {
            if ("Toast".equals(Constants.TOAST_SELECT_TYPE)) {
                if (null != mToast) {
                    mToast.cancel();
                    mToast = null;
                }
                if (Analyzing.isEmpty(mToastView)) {
                    LayoutInflater inflater = mContext.getLayoutInflater();
                    final View view = inflater.inflate(R.layout.toast_layout, null);
                    mTextView = view.findViewById(R.id.toast_tv);
                    mToastView = view;
                }
                // tView.setTextSize(ScreenUtil.sp2px(15));
                mContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Analyzing.isEmpty(mToast)) {
                            mToast = new Toast(mContext);
                            mToast.setDuration(Toast.LENGTH_SHORT);
                            mToast.setView(mToastView);
                        }
                        /*mToast.setGravity(Gravity.CENTER, 0, 0);*/
                        mTextView.setText(message);
                        mToast.show();
                    }
                });
            } else {
                TastyToast.makeText(mContext, message, 1500, 5);
            }
        }
    }

    /**
     * 取消
     */
    public static void cancle() {
        if (!Analyzing.isEmpty(mToast)) {
            mToast.cancel();
        }
    }

    /**
     * Debug模式Toast
     * @param message
     */
    public static synchronized void showDebug(String message){
        if (BuildConfig.DEBUG) {
            mContext = AppUtils.getTopActivity();
            show(message);
        }
    }
}
