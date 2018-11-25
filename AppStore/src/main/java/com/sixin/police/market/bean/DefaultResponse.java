package com.sixin.police.market.bean;

/**
 * Created by malx on 2018/11/19
 */
public class DefaultResponse extends BaseBean {

    public int code;
    public String content;

    public BasisResponse lzyResponse() {
        BasisResponse lzyResponse = new BasisResponse();
        lzyResponse.code = code;
        lzyResponse.content = content;
        return lzyResponse;
    }
}
