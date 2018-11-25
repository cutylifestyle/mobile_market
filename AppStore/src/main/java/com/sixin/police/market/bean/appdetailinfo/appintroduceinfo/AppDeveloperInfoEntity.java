package com.sixin.police.market.bean.appdetailinfo.appintroduceinfo;
/**
 * 开发厂商的实体类
 * */
public class AppDeveloperInfoEntity extends AppIntroduceBase {

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
        return MultiTypeConfig.ITEM_TYPE_TEXT;
    }
}
