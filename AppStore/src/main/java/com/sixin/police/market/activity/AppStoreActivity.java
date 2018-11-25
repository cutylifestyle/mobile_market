package com.sixin.police.market.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import com.lehand.appstore.library.toast.TastyToast;
import com.sixin.police.market.R;
import com.sixin.police.market.base.BaseActivity;
import com.sixin.police.market.base.BaseFragment;
import com.sixin.police.market.bottomnavigation.BottomNavigationBar;
import com.sixin.police.market.bottomnavigation.BottomNavigationItem;
import com.sixin.police.market.fragment.GeneralPurposeFragment;
import com.sixin.police.market.fragment.HomeFragment;
import com.sixin.police.market.fragment.SpecialTopicFragment;
import com.sixin.police.market.fragment.SystemToolFragment;
import com.sixin.police.market.utils.AToast;
import com.sixin.police.market.utils.Analyzing;
import com.sixin.police.market.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by malx on 2018/11/14
 */
public class AppStoreActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {

    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar navigationBar;

    // 最后一次选中的位置
    private int lastSelectedPosition = 0;
    private List<Fragment> fragments = new ArrayList<Fragment>();
    // 首页
    private HomeFragment homeFragment;
    // 通用应用
    private GeneralPurposeFragment purposeFragment;
    // 专题应用
    private SpecialTopicFragment topicFragment;
    // 系统工具
    private SystemToolFragment toolFragment;

    private final String TAG1 = HomeFragment.class.getSimpleName();
    private final String TAG2 = GeneralPurposeFragment.class.getSimpleName();
    private final String TAG3 = SpecialTopicFragment.class.getSimpleName();
    private final String TAG4 = SystemToolFragment.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_store);

        initView();
        getmToolbarTitle().setText(AppUtils.getString(R.string.tab_home));

        if (savedInstanceState == null) {
            navigationBar.selectTab(0, true);
        } else {
            FragmentManager mFragmentManager = getSupportFragmentManager();
            homeFragment = (HomeFragment) mFragmentManager.findFragmentByTag(TAG1);
            purposeFragment = (GeneralPurposeFragment) mFragmentManager.findFragmentByTag(TAG2);
            topicFragment = (SpecialTopicFragment) mFragmentManager.findFragmentByTag(TAG3);
            toolFragment = (SystemToolFragment) mFragmentManager.findFragmentByTag(TAG4);
        }
    }

    /**
     *
     */
    private void initView() {
        navigationBar.setTabSelectedListener(this);
        //bottom
        navigationBar.clearAll();
        navigationBar.setMode(BottomNavigationBar.MODE_FIXED)
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);

        navigationBar
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, getString(R.string.tab_home)).setInactiveIconResource(R.mipmap.ic_launcher_round))
                .setActiveColor(R.color.colorPrimary).setInActiveColor(R.color.design_default_color_primary)
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, getString(R.string.tab_general_purpose)).setInactiveIconResource(R.mipmap.ic_launcher_round))
                .setActiveColor(R.color.colorPrimary)
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, getString(R.string.tab_special_topic)).setInactiveIconResource(R.mipmap.ic_launcher_round))
                .setActiveColor(R.color.colorPrimary)
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, getString(R.string.tab_system_tool)).setInactiveIconResource(R.mipmap.ic_launcher_round))
                .setActiveColor(R.color.colorPrimary)
                .setFirstSelectedPosition(lastSelectedPosition > 3 ? 3 : lastSelectedPosition)
                .initialise();
    }

    @Override
    protected boolean isShowBacking() {
        // 不显示返回按钮
        return false;
    }

    /**
     * Called when a tab enters the selected state.
     *
     * @param position The position of the tab that was selected
     */
    @Override
    public void onTabSelected(int position) {
        // 开启事务
        FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
        hideAllFragment(transaction);
        cutoverFragment(position, transaction);
        // 事物提交
        transaction.commit();
        lastSelectedPosition = position;
        AToast.show("lastSelectedPosition = " + lastSelectedPosition);
    }

    /**
     * 切换页面
     * @param position
     * @param transaction
     */
    private void cutoverFragment(int position, FragmentTransaction transaction) {
        Fragment presentFragment = null;
        switch (position) {
            case 0:
                if (Analyzing.isEmpty(homeFragment)) {
                    homeFragment = HomeFragment.newInstance(TAG1);
                    transaction.add(R.id.tab_select, homeFragment, TAG1);
                }
                getmToolbarTitle().setText(AppUtils.getString(R.string.tab_home));
                presentFragment = homeFragment;
                break;

            case 1:
                if (Analyzing.isEmpty(purposeFragment)) {
                    purposeFragment = GeneralPurposeFragment.newInstance(TAG2);
                    transaction.add(R.id.tab_select, purposeFragment, TAG2);
                }
                getmToolbarTitle().setText(AppUtils.getString(R.string.tab_general_purpose));
                presentFragment = purposeFragment;
                break;

            case 2:
                if (Analyzing.isEmpty(topicFragment)) {
                    topicFragment = SpecialTopicFragment.newInstance(TAG3);
                    transaction.add(R.id.tab_select, topicFragment, TAG3);
                }
                getmToolbarTitle().setText(AppUtils.getString(R.string.tab_special_topic));
                presentFragment = topicFragment;
                break;

            case 3:
                if (Analyzing.isEmpty(toolFragment)) {
                    toolFragment = SystemToolFragment.newInstance(TAG4);
                    transaction.add(R.id.tab_select, toolFragment, TAG4);
                }
                getmToolbarTitle().setText(AppUtils.getString(R.string.tab_system_tool));
                presentFragment = toolFragment;
                break;

            default:
                break;
        }
        fragments.add(presentFragment);
        transaction.show(presentFragment);
    }

    /**
     * 隐藏所有的Fragment
     * @param transaction
     */
    private void hideAllFragment(FragmentTransaction transaction) {
        for (Fragment fragment : fragments) {
            if (Analyzing.isEmpty(fragment)) {
                continue;
            }
            try {
                transaction.hide(fragment);
                fragments.remove(fragment);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Called when a tab exits the selected state.
     *
     * @param position The position of the tab that was unselected
     */
    @Override
    public void onTabUnselected(int position) {

    }

    /**
     * Called when a tab that is already selected is chosen again by the user. Some applications
     * may use this action to return to the top level of a category.
     *
     * @param position The position of the tab that was reselected.
     */
    @Override
    public void onTabReselected(int position) {

    }

    /**
     * Called when pointer capture is enabled or disabled for the current window.
     *
     * @param hasCapture True if the window has pointer capture.
     */
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TastyToast.cancel();
    }
}
