package com.sixin.police.market.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.sixin.police.market.R;
import com.sixin.police.market.bean.appdetailinfo.appcommentinfo.StarLevelEntity;
import com.sixin.police.market.widget.AnimateHorizontalProgressBar;
import com.sixin.police.market.widget.RatingBar;

import java.util.List;

/**
 * 星级评分面板的适配器
 * */
public class StarLevelAdapter extends BaseAdapter {

    private List<StarLevelEntity> mData;

    public StarLevelAdapter(List<StarLevelEntity> data) {
        mData = data;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_starlevel_layout, parent, false);
            viewHolder.rbStarLevel = convertView.findViewById(R.id.rb_star_level);
            viewHolder.ahpLevelCount = convertView.findViewById(R.id.ahp_level_user_count);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        StarLevelEntity starLevelEntity = mData.get(position);
        viewHolder.rbStarLevel.setRating(starLevelEntity.getStarLevel());
        viewHolder.ahpLevelCount.setProgress((int) (starLevelEntity.getPercent()*100));
        return convertView;
    }

    class ViewHolder{
        RatingBar rbStarLevel;
        AnimateHorizontalProgressBar ahpLevelCount;
    }
}
