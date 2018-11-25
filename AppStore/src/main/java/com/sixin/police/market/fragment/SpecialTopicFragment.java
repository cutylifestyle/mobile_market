package com.sixin.police.market.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sixin.police.market.R;
import com.sixin.police.market.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by malx on 2018/11/14
 * @desc 专题应用
 */
public class SpecialTopicFragment extends BaseFragment {

    @BindView(R.id.special_topic_tv)
    TextView textView;

    public static SpecialTopicFragment newInstance(String param) {
        SpecialTopicFragment fragment = new SpecialTopicFragment();
        Bundle args = new Bundle();
        args.putString("agrs", param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container) {
        super.initView(inflater, container);
        setContentView(R.layout.fragment_special_topic, inflater, container);
        textView.setText(getArguments().getString("agrs"));
    }
}
