package com.sixin.police.market.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sixin.police.market.R;
import com.sixin.police.market.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by malx on 2018/11/14
 * @desc 首页
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.tab_home_tv)
    TextView textView;

    public static HomeFragment newInstance(String param) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("agrs", param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container) {
        super.initView(inflater, container);
        setContentView(R.layout.fragment_home, inflater, container);
        textView.setText(getArguments().getString("agrs"));
    }
}
