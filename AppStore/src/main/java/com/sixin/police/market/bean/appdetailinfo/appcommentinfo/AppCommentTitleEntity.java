package com.sixin.police.market.bean.appdetailinfo.appcommentinfo;
/**
 * 应用评论标题的实体类
 * */
public class AppCommentTitleEntity extends AppCommentBase{

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int getItemType() {
        return MultiTypeConfig.ITEM_TYPE_TITLE;
    }
}
