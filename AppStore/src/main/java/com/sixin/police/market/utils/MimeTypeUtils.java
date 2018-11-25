package com.sixin.police.market.utils;

import android.webkit.MimeTypeMap;

/**
 * Created by malia on 2017/6/12 10:50.
 *
 * @project SixinIMAndroidProject
 * @package：com.sixinfor.common.utils
 * @describe：调用手机上能打开对应类型文件的程序
 * @change
 * @chang time
 */

public class MimeTypeUtils {

    public MimeTypeUtils() {}

    /**
     * 调用手机上能打开对应类型文件的程序
     *
     * @param fileName
     * @return
     */
    public static String getMimeType(final String fileName) {
        String result = "application/octet-stream";
        int extPos = fileName.lastIndexOf(".");
        if (extPos != -1) {
            String ext = fileName.substring(extPos + 1);
            result = MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext);
        }
        return result;
    }
}
