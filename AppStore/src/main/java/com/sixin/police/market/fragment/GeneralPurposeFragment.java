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
 * @desc 通用应用
 */
public class GeneralPurposeFragment extends BaseFragment {

    @BindView(R.id.general_purpose_tv)
    TextView textView;

    public static GeneralPurposeFragment newInstance(String param) {
        GeneralPurposeFragment fragment = new GeneralPurposeFragment();
        Bundle args = new Bundle();
        args.putString("agrs", param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container) {
        super.initView(inflater, container);
        setContentView(R.layout.fragment_general_purpose, inflater, container);
        textView.setText(getArguments().getString("agrs"));
    }
}
