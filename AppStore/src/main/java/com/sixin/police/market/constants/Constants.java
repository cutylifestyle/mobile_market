package com.sixin.police.market.constants;

import android.os.Environment;

import com.sixin.police.market.utils.AppUtils;
import com.sixin.police.market.utils.LogUtils;

import java.io.File;

/**
 * Created by malx on 2018/11/14
 */
public class Constants {
    /**
     * 本地缓存目录
     */
    public static final String CACHE_DIR;
    /**
     * 图片缓存目录
     */
    public static final String CACHE_DIR_IMAGE;
    /**
     * 保存测试大文本
     */
    public static final String CACHE_TEXT;
    /**
     * 保存apk文件
     */
    public static final String CACHE_FILE;
    /**
     * 视频缓存缩略图
     */
    public static final String CACHE_VIDEO_THUMBNAIL;
    /**
     * apk名称
     */
    public static final String APK_NAME = "AppStore.apk";

    /**
     * Fragment 传递消息Key
     */
    public static final String ArgumentsKey = "args";
    /**
     * Fragment 传递数据(String)Key
     */
    public static final String ArgumentsOtherKey = "other";
    /**
     * Fragment 传递数据object
     */
    public static final String ArgumentsObjectKey = "object";

    /**
     * toolbar高度
     */
    public static final String SHARED_PREFERENCES_TOOLBAR_HEIGHT = "ToolbarHeight";

    static {
        if (AppUtils.hasSDCard()) {
            CACHE_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/appstore";
            LogUtils.e(CACHE_DIR + "---1");
        } else {
            CACHE_DIR = Environment.getRootDirectory().getAbsolutePath() + "/appstore";
            LogUtils.e(CACHE_DIR + "---0");
        }
        CACHE_DIR_IMAGE = CACHE_DIR + File.separator + "image" + File.separator;
        CACHE_TEXT = CACHE_DIR + File.separator + "text" + File.separator;
        CACHE_FILE = CACHE_DIR + File.separator + "file" + File.separator;
        CACHE_VIDEO_THUMBNAIL = CACHE_DIR + File.separator + "thumbnail" + File.separator;
    }

    public static final String TOAST_SELECT_TYPE = "Toast";


    public enum ToastType {
        /**
         * 系统默认
         */
        Toast,
        /**
         * 带动画效果的toast
         */
        TastyToast
    }
}
