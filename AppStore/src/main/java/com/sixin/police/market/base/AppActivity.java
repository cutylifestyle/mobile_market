package com.sixin.police.market.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sixin.police.market.R;
import com.sixin.police.market.utils.Analyzing;


/**
 * Created by malia on 2017/4/24.
 */

public abstract class AppActivity extends BaseActivity {

    /**
     * 获取第一个fragment
     */
    protected abstract BaseFragment getFirstFragment();

    //获取Intent
    protected void handleIntent(Intent intent){}

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ui);
        if (!Analyzing.isEmpty(getIntent())) {
            handleIntent(getIntent());
        }

        //添加栈底的第一个fragment|| getSupportFragmentManager().getBackStackEntryCount() == 0
        //避免重复添加fragment
        if (Analyzing.isEmpty(getSupportFragmentManager().getFragments())) {
            BaseFragment firstFragment = getFirstFragment();
            if (!Analyzing.isEmpty(firstFragment)) {
                addFragment(firstFragment);
            } else {
                throw new NullPointerException("getFirstFragment() cannot be null");
            }
        }
    }
}
