package com.sixin.police.market.bean.appdetailinfo.appcommentinfo;


import java.util.List;

/**
 * 应用详情全部评论页面头部的数据实体类
 * */
public class AppCommentHeaderEntity extends AppCommentBase{

    private String title;

    private float appScore;

    private String totalCommentCount;

    private List<StarLevelEntity> starLevels;

    public float getAppScore() {
        return appScore;
    }

    public void setAppScore(float appScore) {
        this.appScore = appScore;
    }

    public String getTotalCommentCount() {
        return totalCommentCount;
    }

    public void setTotalCommentCount(String totalCommentCount) {
        this.totalCommentCount = totalCommentCount;
    }

    public List<StarLevelEntity> getStarLevels() {
        return starLevels;
    }

    public void setStarLevels(List<StarLevelEntity> starLevels) {
        this.starLevels = starLevels;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int getItemType() {
        return MultiTypeConfig.ITEM_TYPE_STAR_LEVEL;
    }
}
