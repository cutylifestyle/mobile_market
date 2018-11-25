package com.sixin.police.market.bean.appdetailinfo.appdetailinfo;

import com.sixin.police.market.bean.BaseBean;

/**
 * app详情实体类，其中包含的数据有app版本号，app评论总数
 * */
public class AppDetailEntity extends BaseBean {

    /**
     * app版本号
     * */
    private String appVersion;

    /**
     * app评论总数
     * */
    private String appCommentCount;

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppCommentCount() {
        return appCommentCount;
    }

    public void setAppCommentCount(String appCommentCount) {
        this.appCommentCount = appCommentCount;
    }
}
