package com.sixinfor.common.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

/**
 * Created by malia on 2017/5/22 15:59.
 *
 * @project SixinIMAndroidProject
 * @package：com.sixinfor.common.utils
 * @describe：打开或关闭软键盘
 * @change
 * @chang time
 */

public class KeyBoardUtils {
    /**
     * 打卡软键盘
     *
     * @param mEditText
     *            输入框
     * @param mContext
     *            上下文
     */
    public static void openKeybord(EditText mEditText, Context mContext)
    {
        mEditText.requestFocus();
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     *
     * @param mEditText
     *            输入框
     * @param mContext
     *            上下文
     */
    public static void closeKeybord(EditText mEditText, Context mContext)
    {
        mEditText.clearFocus();
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    /**
     * 打开软键盘
     *
     * @param mEditText
     *                  输入框
     * @param mContext
     *                  上下文
     */
    public static void openKeybord(AutoCompleteTextView mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     *
     * @param mEditText
     *                  输入框
     * @param mContext
     *                  上下文
     */
    public static void closeKeybord(AutoCompleteTextView mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    /**
     * 是否显示软键盘
     * @return
     */
    public static boolean isShowKeyboard(Context mContext) {
        boolean isOpen = false;
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        isOpen = imm.isActive();
        return isOpen;
    }

    /**
     * 是否强制打开键盘
     * @param isOpen
     * @param view 软键盘根布局
     */
    public static void openKeyboard(boolean isOpen, View view){
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (isOpen) {
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        } else {
            // 强制隐藏键盘
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
