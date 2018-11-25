package com.sixin.police.market.bean.appdetailinfo.appcommentinfo;

/**
 * 用户评论的实体类
 * */
public class AppCommentEntity extends AppCommentBase{

    private String userName;

    private String policeId;

    private String commentDate;

    private String commentContent;

    private float commentScore;

    private String commentVersion;

    private String likeCount;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPoliceId() {
        return policeId;
    }

    public void setPoliceId(String policeId) {
        this.policeId = policeId;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public float getCommentScore() {
        return commentScore;
    }

    public void setCommentScore(float commentScore) {
        this.commentScore = commentScore;
    }

    public String getCommentVersion() {
        return commentVersion;
    }

    public void setCommentVersion(String commentVersion) {
        this.commentVersion = commentVersion;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    @Override
    public int getItemType() {
        return MultiTypeConfig.ITEM_TYPE_COMMENT;
    }
}
