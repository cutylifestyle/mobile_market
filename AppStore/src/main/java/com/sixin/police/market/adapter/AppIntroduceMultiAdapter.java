package com.sixin.police.market.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lehand.adapter.BaseMultiItemQuickAdapter;
import com.lehand.adapter.BaseQuickAdapter;
import com.lehand.adapter.BaseViewHolder;
import com.sixin.police.market.R;
import com.sixin.police.market.activity.AppPreImagesActivity;
import com.sixin.police.market.bean.appdetailinfo.appintroduceinfo.AppDeveloperInfoEntity;
import com.sixin.police.market.bean.appdetailinfo.appintroduceinfo.AppInfoEntity;
import com.sixin.police.market.bean.appdetailinfo.appintroduceinfo.AppIntroduceBase;
import com.sixin.police.market.bean.appdetailinfo.appintroduceinfo.AppSummaryEntity;
import com.sixin.police.market.bean.appdetailinfo.appintroduceinfo.AppUpdateLogEntity;
import com.sixin.police.market.bean.appdetailinfo.appintroduceinfo.MultiTypeConfig;
import com.sixin.police.market.bean.appdetailinfo.appintroduceinfo.PreImageEntity;
import com.sixin.police.market.utils.ScreenUtil;
import com.sixin.police.market.widget.expandabletextview.ExpandableTextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 应用介绍部分的recyclerView适配器
 * */
public class AppIntroduceMultiAdapter extends BaseMultiItemQuickAdapter<AppIntroduceBase,BaseViewHolder> {
    //TODO 这里面有很多的重复代码
    private static final String MOBLIE_PHONE_PATTERN = "((13[0-9])|(15[0-9])|(18[0-9])|(14[7])|(17[0|6|7|8]))\\d{8}";

    //应用截图模块的成员变量
    private boolean hasResources;
    private List<String> imgs;

    public AppIntroduceMultiAdapter(List<AppIntroduceBase> data) {
        super(data);
        addItemType(MultiTypeConfig.ITEM_TYPE_IMAGES, R.layout.item_sceenshot_app_introduce);
        addItemType(MultiTypeConfig.ITEM_TYPE_TEXT,R.layout.item_info_app_detail);
        addItemType(MultiTypeConfig.ITEM_TYPE_EXPAND_TEXT, R.layout.item_expand_tv_app_detail);
    }

    @Override
    protected void convert(BaseViewHolder helper, AppIntroduceBase item) {
        int itemViewType = helper.getItemViewType();
        if (itemViewType == MultiTypeConfig.ITEM_TYPE_IMAGES && item instanceof PreImageEntity) {
            setScreenshotArea(helper, (PreImageEntity) item);
        } else if (itemViewType == MultiTypeConfig.ITEM_TYPE_TEXT && item instanceof AppInfoEntity) {
            setAppInfoArea(helper, (AppInfoEntity) item);
        }else if (itemViewType == MultiTypeConfig.ITEM_TYPE_TEXT && item instanceof AppDeveloperInfoEntity) {
            setAppDeveloperInfoArea(helper, (AppDeveloperInfoEntity) item);
        } else if (itemViewType == MultiTypeConfig.ITEM_TYPE_EXPAND_TEXT && item instanceof AppSummaryEntity) {
            setAppSummaryArea(helper, (AppSummaryEntity) item);
        }else if (itemViewType == MultiTypeConfig.ITEM_TYPE_EXPAND_TEXT && item instanceof AppUpdateLogEntity) {
            setAppUpdateLogArea(helper, (AppUpdateLogEntity) item);
        }
    }

    private void setAppUpdateLogArea(BaseViewHolder helper, AppUpdateLogEntity item) {
        String title = item.getTitle();
        if (TextUtils.isEmpty(title)) {
            title = mContext.getString(R.string.no_content);
        }
        helper.setText(R.id.tv_expand_app_info, title);

        String content = item.getContent();
        if (TextUtils.isEmpty(content)) {
            content = mContext.getString(R.string.no_content);
        }
        ExpandableTextView expandableTextView = helper.getView(R.id.tv_expand_specific_info);
        expandableTextView.setText(content);
    }

    private void setAppSummaryArea(BaseViewHolder helper, AppSummaryEntity item) {
        String title = item.getTitle();
        if (TextUtils.isEmpty(title)) {
            title = mContext.getString(R.string.no_content);
        }
        helper.setText(R.id.tv_expand_app_info, title);

        String content = item.getContent();
        if (TextUtils.isEmpty(content)) {
            content = mContext.getString(R.string.no_content);
        }
        ExpandableTextView expandableTextView = helper.getView(R.id.tv_expand_specific_info);
        expandableTextView.setText(content);
    }

    private void setAppDeveloperInfoArea(BaseViewHolder helper, AppDeveloperInfoEntity item) {
        String title = item.getTitle();
        if (TextUtils.isEmpty(title)) {
            title = mContext.getString(R.string.no_content);
        }
        helper.setText(R.id.tv_app_info, title);

        TextView tvSpecificInfo = helper.getView(R.id.tv_specific_info);
        //设置手机号码可被点击
//        List<String> phoneNumbers = new ArrayList<>();
        String content = item.getContent();
//        if (TextUtils.isEmpty(content)) {
//            content = mContext.getString(R.string.no_content);
//        }
//        Pattern p = Pattern.compile(MOBLIE_PHONE_PATTERN);
//        Matcher m = p.matcher(content);
//        while (m.find()) {
//            phoneNumbers.add(m.group());
//            Log.d(TAG, "----:" + m.group());
//        }
//        if (phoneNumbers.size() > 0) {
//            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(content);
//            for (String phoneNumber :
//                    phoneNumbers) {
//                int i = content.indexOf(phoneNumber);
//                spannableStringBuilder.setSpan(new TextClick(),i,i+phoneNumber.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            }
//            tvSpecificInfo.setMovementMethod(LinkMovementMethod.getInstance());
//            tvSpecificInfo.setText(spannableStringBuilder);
//        }else{
            tvSpecificInfo.setText(content);
//        }
    }

    private void setAppInfoArea(BaseViewHolder helper, AppInfoEntity item) {
        String title = item.getTitle();
        if (TextUtils.isEmpty(title)) {
            title = mContext.getString(R.string.no_content);
        }
        helper.setText(R.id.tv_app_info, title);

        String content = item.getContent();
        if (TextUtils.isEmpty(content)) {
            content = mContext.getString(R.string.no_content);
        }
        helper.setText(R.id.tv_specific_info, content);
    }

    private void setScreenshotArea(BaseViewHolder helper, PreImageEntity item) {
        String title = item.getTitle();
        if (TextUtils.isEmpty(title)) {
            title = mContext.getString(R.string.no_content);
        }
        helper.setText(R.id.tv_app_screenshot, title);

        RecyclerView rlvScreenShot = helper.getView(R.id.rlv_app_screenshot);
        rlvScreenShot.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
        imgs = item.getImgs();
        if (imgs != null && imgs.size() > 0) {
            hasResources = true;
        }else{
            hasResources = false;
            if (imgs == null) {
                imgs = new ArrayList<>();
            }
            for (int i = 0; i < 3; i++) {
                imgs.add("");
            }
        }
        //TODO 各个版本的机子的适应情况
        //TODO 提供错误图片
        ScreenShotAdapter screenShotAdapter = new ScreenShotAdapter(R.layout.item_screenshot_img, imgs, hasResources);
        rlvScreenShot.setAdapter(screenShotAdapter);
        screenShotAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (hasResources) {
                    Intent intent = new Intent(mContext,AppPreImagesActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(AppPreImagesActivity.KEY_PRE_IMGS, (Serializable) imgs);
                    bundle.putInt(AppPreImagesActivity.KEY_POSITION,position);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }else{
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.sorry_no_preimages), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    class TextClick extends ClickableSpan{

        @Override
        public void onClick(View widget) {
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(Color.BLUE);
        }
    }


}
