package com.sixin.police.market.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by malia on 2017/4/27 14:06.
 *
 * @project SixinIMAndroidProject
 * @package：com.sixinfor.common.utils
 * @describe：sharedPreferences
 * @change
 * @chang time
 */

public class SharedPreferencesUtils {
    /**
     * 保存在手机里面的文件名
     */
    private static final String FILE_NAME = "share_date";

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    public static void setParam(Context context, String key, Object object) {

        String type = object.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if ("String".equals(type)) {
            editor.putString(key, (String) object);
        } else if ("Integer".equals(type)) {
            editor.putInt(key, (Integer) object);
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(key, (Boolean) object);
        } else if ("Float".equals(type)) {
            editor.putFloat(key, (Float) object);
        } else if ("Long".equals(type)) {
            editor.putLong(key, (Long) object);
        }
        editor.commit();
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object getParam(Context context, String key, Object defaultObject) {
        String type = defaultObject.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

        if ("String".equals(type)) {
            return sp.getString(key, (String) defaultObject);
        } else if ("Integer".equals(type)) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if ("Boolean".equals(type)) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if ("Float".equals(type)) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if ("Long".equals(type)) {
            return sp.getLong(key, (Long) defaultObject);
        }
        return null;
    }

    /**
     * 移除某个key值已经对应的值
     * @param context
     * @param key
     */
    public static void remove(Context context, String key)
    {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        com.sixinfor.common.utils.SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     * @param context
     */
    public static void clearAll(Context context)
    {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        com.sixinfor.common.utils.SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     * @param context
     * @param key
     * @return
     */
    public static boolean contains(Context context, String key)
    {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 获取实例化对象的方法
     *
     * @param context
     * @param key
     * @param classOfT
     * @param <T>
     * @return
     */
    public static <T> T getObject(Context context, String key, Class<?> classOfT) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        String json = sp.getString(key, "");
        //com.google.gson.JsonSyntaxException: java.lang.IllegalStateException: Expected BEGIN_OBJECT but was BEGIN_ARRAY at line 1 column 2 path $
        Object object = null;
        try {
            object = Convert.fromJson(json, classOfT);
        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            //java.lang.ClassCastException: com.google.gson.internal.LinkedTreeMap cannot be cast to
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return (T) object;
    }

    /**
     * 获取实例化对象的方法
     * @param key
     * @param classOfT
     * @param <T>
     * @return
     */
    public static <T> T getObject(String key, Class<?> classOfT) {
        SharedPreferences sp = AppUtils.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        String json = sp.getString(key, "");
        Object object = null;
        try {
            object = Convert.fromJson(json, classOfT);
        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return (T) object;
    }

    /**
     * 获取List
     * @param context
     * @param key
     * @param <T>
     * @return
     */
    public static <T> List<T> getObjectList(Context context, String key, Class<T> classOfT) {
        List<T> datasT = new ArrayList<T>();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        String json = sp.getString(key, "");
        // 创建一个JsonParser
        JsonParser jsonParser = new JsonParser();
        JsonElement element = jsonParser.parse(json);
        if (element.isJsonArray()) {
            //判断是否为Json数组
            JsonArray array = element.getAsJsonArray();
            for (JsonElement jsonElement : array) {
                datasT.add(Convert.fromJson(jsonElement, classOfT));
            }
        }
        //JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        //datasT = Convert.fromJsonList(json, new TypeToken<List<T>>(){}.getType());
        return datasT;
    }

    /**
     * 存储对象
     * @param context
     * @param key
     * @param object
     */
    public static void setObject(Context context , String key, Object object){
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, Convert.toJson(object));
        edit.commit();
    }

}
