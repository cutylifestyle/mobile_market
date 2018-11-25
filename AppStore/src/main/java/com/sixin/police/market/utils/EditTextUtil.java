package com.sixinfor.common.utils;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.widget.EditText;

/**
 * Created by malia on 2017/5/31 11:20.
 *
 * @project SixinIMAndroidProject
 * @package：com.sixinfor.common.utils
 * @describe：EditText帮助类
 * @change
 * @chang time
 */

public class EditTextUtil {

    /**
     * 单独设置hint字体的大小
     * @param mEditText
     * @param hintString
     * @param hintTextSize
     */
    public static void setEditTextHintTextSize(EditText mEditText, String hintString, int hintTextSize) {
        SpannableString userSpannableString = new SpannableString(hintString);//定义hint的值
        AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(hintTextSize, true);//设置字体大小 true表示单位是sp
        userSpannableString.setSpan(sizeSpan, 0, userSpannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mEditText.setHint(userSpannableString);
    }
}
