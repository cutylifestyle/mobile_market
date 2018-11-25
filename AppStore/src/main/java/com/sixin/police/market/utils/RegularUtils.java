package com.sixinfor.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by malia on 2017/6/9 9:36.
 *
 * @project SixinIMAndroidProject
 * @package：com.sixinfor.common.utils
 * @describe：正则工具类
 * @change
 * @chang time
 */

public class RegularUtils {

    /**
     * 判断手机号码
     *
     * @param phoneNumber
     * @return
     */
    public static boolean isMobile(String phoneNumber) {
        String MOBLIE_PHONE_PATTERN = "^((13[0-9])|(15[0-9])|(18[0-9])|(14[7])|(17[0|6|7|8]))\\d{8}$";
        Pattern p = Pattern.compile(MOBLIE_PHONE_PATTERN);
        Matcher m = p.matcher(phoneNumber);
        return m.matches();
    }

    /**
     * 判断是否为电子邮件网址
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 正则匹配小括号内容
     *
     * "(?<=\\()[^\\)]+"
     * @param msg
     * @return
     */
    public static List<String> getParenthesesContents(String msg) {
        List<String> mList = new ArrayList<String>();
        Pattern pattern = Pattern.compile("(?<=\\()[^\\)]+");
        Matcher matcher = pattern.matcher(msg);
        while (matcher.find()) {
            mList.add(matcher.group());
        }
        return mList;
    }

    /**
     * 正则匹配url里面的内容
     *
     * "(?<=url\\()[^\\)]+"
     * @param msg
     * @return
     */
    public static List<String> getParenthesesUrlContents(String msg) {
        List<String> mList = new ArrayList<String>();
        Pattern pattern = Pattern.compile("(?<=url\\()[^\\)]+");
        Matcher matcher = pattern.matcher(msg);
        while (matcher.find()) {
            mList.add(matcher.group());
        }
        return mList;
    }

}
