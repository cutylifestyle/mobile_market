package com.sixin.police.market.bean.appdetailinfo.appcommentinfo;

/**
 * 用于通知应用全部评论模块刷新的消息；是EventBus传递的数据
 * */
public class AppCommitCommentEvent {

    /**
     * 是否刷新全部评论模块
     * true :是
     * false: 不刷新
     * */
    private boolean isRefresh;

    public boolean isRefresh() {
        return isRefresh;
    }

    public void setRefresh(boolean refresh) {
        isRefresh = refresh;
    }
}
