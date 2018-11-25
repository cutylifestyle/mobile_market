package com.sixin.police.market.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lehand.appstore.library.Utils;
import com.sixin.police.market.R;
import com.sixin.police.market.base.BaseActivity;

public class StartupPageActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_page);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                moveTo(AppDetailActivity.class, false);
            }
        }, 1000);
    }
}
