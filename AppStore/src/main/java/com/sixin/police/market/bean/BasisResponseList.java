package com.sixin.police.market.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by malx on 2018/11/19
 */
public class BasisResponseList<T> extends BaseBean {

    public int code;
    public String content;
    public List<T> data;
    public boolean success;
    public String title;
    public String type;

}
