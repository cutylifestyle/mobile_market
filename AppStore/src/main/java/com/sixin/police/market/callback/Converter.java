package com.sixin.police.market.callback;

import okhttp3.Response;

/**
 * Created by malia on 2017/7/20 18:03.
 *
 * @project PartyBuilding
 * @package：com.sixinfor.partybuilding.callback
 * @describe：网络数据的转换接口
 * @change
 * @chang time
 */

public interface Converter<T> {
    /**
     * 拿到响应后，将数据转换成需要的格式，子线程中执行，可以是耗时操作
     *
     * @param response 需要转换的对象
     * @return 转换后的结果
     * @throws Exception 转换过程发生的异常
     */
    T convertResponse(Response response) throws Throwable;
}
