package com.sixin.police.market.utils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.HttpParams;

/**
 * Created by malx on 2018/11/19
 */
public class OKHttpAsync {

    /**
     * post请求简单封装
     * @param url
     * @param params
     * @param absCallback
     * @param <T>
     */
    public static <T> void post(String url, HttpParams params, AbsCallback absCallback) {
        OkGo.<T>post(url)
                .tag(AppUtils.getTopActivity())
                .params(params)
                .execute(absCallback);
    }
}
