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
 * @desc 系统应用
 */
public class SystemToolFragment extends BaseFragment {

    @BindView(R.id.system_tool_tv)
    TextView textView;

    public static SystemToolFragment newInstance(String param) {
        SystemToolFragment fragment = new SystemToolFragment();
        Bundle args = new Bundle();
        args.putString("agrs", param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container) {
        super.initView(inflater, container);
        setContentView(R.layout.fragment_system_tool, inflater, container);
        textView.setText(getArguments().getString("agrs"));
    }
}
