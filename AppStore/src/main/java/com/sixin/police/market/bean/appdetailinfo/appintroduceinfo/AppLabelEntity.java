package com.sixin.police.market.bean.appdetailinfo.appintroduceinfo;

import com.lehand.adapter.entity.MultiItemEntity;
import com.sixin.police.market.bean.BaseBean;

import java.util.List;
/**
 * 应用标签的实体类
 * */
public class AppLabelEntity extends AppIntroduceBase {

    private String title;
    private List<String> content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    @Override
    public int getItemType() {
        return MultiTypeConfig.ITEM_TYPE_LABEL;
    }
}
