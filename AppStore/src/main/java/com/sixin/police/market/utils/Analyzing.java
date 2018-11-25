package com.sixin.police.market.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by malia on 2017/4/20.
 * 分析
 */

public class Analyzing {

    /**
     * 判断对象是否为空
     * @param object
     * @return
     */
    public static boolean isEmpty(Object object) {
        boolean rs = false;
        if (object instanceof String) {
            rs = isEmpty((String) object);
        } else {
            if (null == object) {
                rs = true;
            }
        }
        return rs;
    }

    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        boolean rs = false;
        if(null == str || "".equals(str) || "null".equals(str) || "\"\"".equals(str)){
            rs = true;
        }
        return rs;
    }

    /**
     * 判断list是否为空
     * @param object
     * @return
     */
    public static boolean isEmpty(List<?> object) {
        boolean rs = false;
        if (object != null) {
            rs = object.isEmpty();
        }
        return rs;
    }

    /**
     *  判断字符串中是否含Emoji表情正则表达式
     * @param emoji
     * @return
     */
    public boolean hasEmoji(String emoji){
        boolean isEmoji = false;
        Pattern pattern = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]");
        Matcher matcher = pattern.matcher(emoji);
        if(matcher .find()){
            isEmoji = true;
        }
        return isEmoji;
    }

    /**
     * 判断文件是否为图片文件(GIF,PNG,JPG)
     *
     * @param srcFilePaht
     * @return
     */
    public static boolean isGif(File srcFilePaht) {
        boolean isGif = false;
        FileInputStream inputStream = null;
        byte[] bytes = new byte[10];
        int length = -1;
        try {
            inputStream = new FileInputStream(srcFilePaht);
            length = inputStream.read(bytes);
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (length == 10) {
            byte b0 = bytes[0];
            byte b1 = bytes[1];
            byte b2 = bytes[2];
            byte b3 = bytes[3];
            byte b4 = bytes[4];
            byte b5 = bytes[5];
            byte b6 = bytes[6];
            byte b7 = bytes[7];
            byte b8 = bytes[8];
            byte b9 = bytes[9];

            if (b0 == (byte) 'G' && b1 == (byte) 'I' && b2 == (byte) 'F') {
                // GIF
                isGif = true;
            } else if (b1 == (byte) 'P' && b2 == (byte) 'N' && b3 == (byte) 'G') {
                // PNG
            } else if (b6 == (byte) 'J' && b7 == (byte) 'F' && b8 == (byte) 'I' && b9 == (byte) 'F') {
                // JPG
            } else {
                //Unknown
            }
        }
        return isGif;
    }

    /**
     * 判断图片类型是否是gif
     *
     * @param url
     * @return
     */
    public static boolean isGif(String url) {
        boolean isGif = false;
        String reg = "(?i).+?\\.(gif)";
        if (isEmpty(url)) {
            isGif = false;
        } else {
            isGif = url.matches(reg);
        }
        return isGif;
    }
}
