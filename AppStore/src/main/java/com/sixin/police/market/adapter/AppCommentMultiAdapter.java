package com.sixin.police.market.adapter;

import android.text.TextUtils;
import android.util.Log;
import android.widget.ListView;

import com.lehand.adapter.BaseMultiItemQuickAdapter;
import com.lehand.adapter.BaseViewHolder;
import com.sixin.police.market.R;
import com.sixin.police.market.bean.appdetailinfo.appcommentinfo.AppCommentBase;
import com.sixin.police.market.bean.appdetailinfo.appcommentinfo.AppCommentEntity;
import com.sixin.police.market.bean.appdetailinfo.appcommentinfo.AppCommentHeaderEntity;
import com.sixin.police.market.bean.appdetailinfo.appcommentinfo.AppCommentTitleEntity;
import com.sixin.police.market.bean.appdetailinfo.appcommentinfo.MultiTypeConfig;
import com.sixin.police.market.bean.appdetailinfo.appcommentinfo.StarLevelEntity;
import com.sixin.police.market.widget.expandabletextview.ExpandableTextView;

import java.util.List;
/**
 * 应用评论部分的recyclerView的适配器
 * */
public class AppCommentMultiAdapter extends BaseMultiItemQuickAdapter<AppCommentBase,BaseViewHolder> {

    private static final String TAG = "AppCommentMultiAdapter";

    public AppCommentMultiAdapter(List<AppCommentBase> data) {
        super(data);
        addItemType(MultiTypeConfig.ITEM_TYPE_STAR_LEVEL, R.layout.item_comment_statistical_panel);
        addItemType(MultiTypeConfig.ITEM_TYPE_TITLE,R.layout.item_app_comment_title);
        addItemType(MultiTypeConfig.ITEM_TYPE_COMMENT,R.layout.item_app_comment_content);
    }

    @Override
    protected void convert(BaseViewHolder helper, AppCommentBase item) {
        int itemViewType = helper.getItemViewType();
        if (itemViewType == MultiTypeConfig.ITEM_TYPE_STAR_LEVEL && item instanceof AppCommentHeaderEntity) {
            renderStatisticalPanelArea(helper, (AppCommentHeaderEntity) item);
        } else if (itemViewType == MultiTypeConfig.ITEM_TYPE_TITLE && item instanceof AppCommentTitleEntity) {
            renderTitleArea(helper, (AppCommentTitleEntity) item);
        } else if (itemViewType == MultiTypeConfig.ITEM_TYPE_COMMENT && item instanceof AppCommentEntity) {
            renderUserCommentArea(helper, (AppCommentEntity) item);
        }
    }

    private void renderUserCommentArea(BaseViewHolder helper, AppCommentEntity item) {
        String userName = checkContent(item.getUserName());
        String policeId = checkContent(item.getPoliceId());
        helper.setText(R.id.tv_user_name, userName + mContext.getString(R.string.left_parenthesis) + policeId + mContext.getString(R.string.right_parenthesis));

        String commentContent = checkContent(item.getCommentContent());
        ExpandableTextView expandableTextView = helper.getView(R.id.tv_comment_content);
        expandableTextView.setText(commentContent);
    }

    private void renderTitleArea(BaseViewHolder helper, AppCommentTitleEntity item) {
        String title = checkContent(item.getTitle());
        helper.setText(R.id.tv_app_comment_title, title);
    }

    private void renderStatisticalPanelArea(BaseViewHolder helper, AppCommentHeaderEntity item) {
        String title = checkContent(item.getTitle());
        helper.setText(R.id.tv_app_score_label, title);

        List<StarLevelEntity> starLevels = item.getStarLevels();
        if (starLevels != null && starLevels.size() > 0) {
            ListView starlV = helper.getView(R.id.lv_score_level);
            StarLevelAdapter starLevelAdapter = new StarLevelAdapter(starLevels);
            starlV.setAdapter(starLevelAdapter);
        }
    }

    private String checkContent(String checkedContent) {
        String result = checkedContent;
        if (TextUtils.isEmpty(result)) {
            result = mContext.getResources().getString(R.string.no_content);
        }
        return result;
    }


}
