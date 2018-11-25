package com.sixinfor.common.utils;

import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by malia on 2017/4/27 14:14.
 *
 * @project SixinIMAndroidProject
 * @package：com.sixinfor.common.utils
 * @describe：由于editor.commit方法是同步的，并且我们很多时候的commit操作都是UI线程中，毕竟是IO操作，所以尽可能采用异步操作；
 *              所以使用apply进行替代，apply异步的进行写入
 * @change
 * @chang time
 */

public class SharedPreferencesCompat {

    private static final Method sApplyMethod = findApplyMethod();

    /**
     * 反射查找apply的方法
     *
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static Method findApplyMethod() {
        try {
            Class clz = SharedPreferences.Editor.class;
            return clz.getMethod("apply");
        } catch (NoSuchMethodException e) {

        }
        return null;
    }

    /**
     * 如果找到则使用apply执行，否则使用commit
     *
     * @param editor
     */
    public static void apply(SharedPreferences.Editor editor) {
        try {
            if (sApplyMethod != null) {
                sApplyMethod.invoke(editor);
                return;
            }
        } catch (IllegalArgumentException e) {

        } catch (IllegalAccessException e) {

        } catch (InvocationTargetException e) {

        }
        editor.commit();
    }
}
