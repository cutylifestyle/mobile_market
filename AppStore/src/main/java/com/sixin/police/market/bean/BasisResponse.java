package com.sixin.police.market.bean;

import java.io.Serializable;

/**
 * Created by malx on 2018/11/19
 */
public class BasisResponse<T> extends BaseBean {

    public int code;
    public String content;
    public T data;
    public boolean success;
    public String title;
    public String type;

}
