package com.sixin.police.market.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lehand.adapter.BaseQuickAdapter;
import com.sixin.police.market.AppStore;
import com.sixin.police.market.R;
import com.sixin.police.market.adapter.AppIntroduceMultiAdapter;
import com.sixin.police.market.base.BaseFragment;
import com.sixin.police.market.bean.appdetailinfo.appintroduceinfo.AppIntroduceBase;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;

public class AppIntroduceFragment extends BaseFragment {

    private static final String TAG = "AppIntroduceFragment";

    private static final String ARG_PARAM_BASE_BEAN = "appIntroduceInfos";

    private List<AppIntroduceBase> mAppIntroduceInfos;

    @Nullable
    @BindView(R.id.rv_app_introduce)
    RecyclerView mRVIntroduce;

    private AppIntroduceMultiAdapter mAppIntroduceMultiAdapter;


    public AppIntroduceFragment() {
        // Required empty public constructor
    }

    public static AppIntroduceFragment newInstance(List<AppIntroduceBase> appIntroduceInfos) {
        //TODO 实体类序列化对Gson解析会不会造成影响
        AppIntroduceFragment fragment = new AppIntroduceFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_BASE_BEAN, (Serializable) appIntroduceInfos);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAppIntroduceInfos = (List<AppIntroduceBase>) getArguments().getSerializable(ARG_PARAM_BASE_BEAN);
        }
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container) {
        super.initView(inflater, container);
        setContentView(R.layout.fragment_app_introduce, inflater, container);
    }

    @Override
    public void lazyLoad() {
        if (mAppIntroduceInfos != null && mAppIntroduceInfos.size() > 0) {
            mRVIntroduce.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
            mAppIntroduceMultiAdapter= new AppIntroduceMultiAdapter(mAppIntroduceInfos);
            mAppIntroduceMultiAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
            mRVIntroduce.setAdapter(mAppIntroduceMultiAdapter);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAppIntroduceMultiAdapter != null) {
            mAppIntroduceMultiAdapter.closeLoadAnimation();
        }
        AppStore.getRefWatcher(getContext()).watch(this);
    }
}
