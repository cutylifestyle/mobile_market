package com.sixin.police.market.adapter;

import android.support.annotation.Nullable;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lehand.adapter.BaseQuickAdapter;
import com.lehand.adapter.BaseViewHolder;
import com.sixin.police.market.R;

import java.util.List;

public class ScreenShotAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    private boolean mHasResources;

    public ScreenShotAdapter(int layoutResId, @Nullable List<String> data,boolean hasResources) {
        super(layoutResId, data);
        mHasResources = hasResources;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        if (mHasResources){
            SimpleDraweeView sdvScreenShot = helper.getView(R.id.sdv_screen_shot);
            sdvScreenShot.setImageURI(item);
        }
    }
}
