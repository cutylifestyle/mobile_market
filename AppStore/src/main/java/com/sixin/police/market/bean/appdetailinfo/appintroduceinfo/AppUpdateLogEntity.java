package com.sixin.police.market.bean.appdetailinfo.appintroduceinfo;

import com.lehand.adapter.entity.MultiItemEntity;
import com.sixin.police.market.bean.BaseBean;
/**
 * 升级日志的实体类
 * */
public class AppUpdateLogEntity extends AppIntroduceBase {

    private String title;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    @Override
    public int getItemType() {
        return MultiTypeConfig.ITEM_TYPE_EXPAND_TEXT;
    }
}
