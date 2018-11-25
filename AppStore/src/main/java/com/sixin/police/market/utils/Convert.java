package com.sixin.police.market.utils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）Github地址：https://github.com/jeasonlzy
 * 版    本：1.0
 * 创建日期：16/9/28
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class Convert {

    private static Gson create() {
        return Convert.GsonHolder.gson;
    }

    private static class GsonHolder {
        private static Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();
    }

    public static <T> T object(String json, Class<T> classOfT) {
        return create().fromJson(json, classOfT);
    }

    public static <T> T fromJson(JsonElement json, Class<T> classOfT) throws JsonIOException, JsonSyntaxException, ClassCastException {
        return create().fromJson(json, classOfT);
    }

    public static <T> T fromJson(String json, Class<T> type) throws JsonIOException, JsonSyntaxException, IllegalStateException {
        return create().fromJson(json, type);
    }

    public static <T> T fromJson(String json, Type type) throws JsonIOException, JsonSyntaxException, ClassCastException {
        return create().fromJson(json, type);
    }

    public static <T> List<T> fromJsonList(String json, Type type) throws JsonIOException, JsonSyntaxException, ClassCastException {
        return create().fromJson(json, type);
    }

    public static <T> T fromJson(JsonReader reader, Type typeOfT) throws JsonIOException, JsonSyntaxException {
        return create().fromJson(reader, typeOfT);
    }

    public static <T> T fromJson(Reader json, Class<T> classOfT) throws JsonSyntaxException, JsonIOException {
        return create().fromJson(json, classOfT);
    }

    public static <T> T fromJson(Reader json, Type typeOfT) throws JsonIOException, JsonSyntaxException {
        return create().fromJson(json, typeOfT);
    }

    public static String toJson(Object src) {
        return create().toJson(src);
    }

    public static String toJson(Object src, Type typeOfSrc) {
        return create().toJson(src, typeOfSrc);
    }

    public static String formatJson(String json) {
        try {
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(json);
            return create().toJson(je);
        } catch (Exception e) {
            return json;
        }
    }

    public static String formatJson(Object src) {
        try {
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(toJson(src));
            return create().toJson(je);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}