package com.sixin.police.market.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.sixin.police.market.AppStore;
import com.sixin.police.market.R;
import com.sixin.police.market.base.BaseActivity;
import com.sixin.police.market.bean.appdetailinfo.appintroduceinfo.AppDeveloperInfoEntity;
import com.sixin.police.market.bean.appdetailinfo.appintroduceinfo.AppInfoEntity;
import com.sixin.police.market.bean.appdetailinfo.appintroduceinfo.AppIntroduceBase;
import com.sixin.police.market.bean.appdetailinfo.appintroduceinfo.AppIntroduceEntity;
import com.sixin.police.market.bean.appdetailinfo.appintroduceinfo.AppLabelEntity;
import com.sixin.police.market.bean.appdetailinfo.appintroduceinfo.AppSummaryEntity;
import com.sixin.police.market.bean.appdetailinfo.appintroduceinfo.AppUpdateLogEntity;
import com.sixin.police.market.bean.BaseBean;
import com.sixin.police.market.bean.appdetailinfo.appintroduceinfo.PreImageEntity;
import com.sixin.police.market.fragment.AppAllCommentFragment;
import com.sixin.police.market.fragment.AppCommitCommentFragment;
import com.sixin.police.market.fragment.AppIntroduceFragment;
import com.sixin.police.market.utils.Analyzing;
import com.sixin.police.market.utils.NetUtils;
import com.sixin.police.market.utils.ScreenUtil;
import com.sixin.police.market.utils.StatusBarUtil;
import com.sixin.police.market.widget.emptypage.LoadingAndRetryManager;
import com.sixin.police.market.widget.emptypage.OnLoadingAndRetryListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 应用详情的activity
 * */
public class AppDetailActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private static final String TAG ="AppDetailActivity";
    private static final String TAG_CANCEL_REQUEST_INTRODUCE = "tag_cancel_request_introduce";
    private static final String TAG_CANCEL_REQUEST_HEADER_PANEL = "tag_cancel_request_header_panel";
    //TODO 修改了基础布局ToolBar的图片
    //TODO 测试meta9\meta10\小米\华为平板\锤子手机的表现
    //TODO AToast发生了内存泄漏

    //TODO 模拟数据

    @Nullable
    @BindView(R.id.app_detail_content)
    LinearLayout mDetailContent;

    @Nullable
    @BindView(R.id.radio_group_app_detail)
    RadioGroup mRadioGroup;

    @Nullable
    @BindView(R.id.img_display_more_operation)
    ImageView mImgDisplayMore;

    @Nullable
    @BindView(R.id.display_more_layout)
    RelativeLayout mLayoutDisplayMore;

    @Nullable
    @BindView(R.id.line_view)
    View mLineView;

    @Nullable
    @BindView(R.id.btn_open_more)
    Button mBtnOpenMore;

    @Nullable
    @BindView(R.id.btn_uninstall_more)
    Button mBtnUninstallMore;

    private FragmentManager mFragmentManager;
    //应用介绍的碎片
    private AppIntroduceFragment mAppIntroduceFragment;
    //应用全部评论的碎片
    private AppAllCommentFragment mAppAllCommentFragment;
    //提交应用评价的碎片
    private AppCommitCommentFragment mAppCommitCommentFragment;
    private List<Fragment> fragments = new ArrayList<>();

    private boolean isDown = true;
    //折叠图片的向下的动画效果
    private AnimatorSet mDownAnimatorSet;
    //折叠图片的向上的动画效果
    private AnimatorSet mUpAnimatorSet;

    private LoadingAndRetryManager mLoadingAndRetryManager;
    private View mRetryViewLayout;

    /**
     * 网络请求总次数的累加器
     * 用于实现多个网络请求全部完成在渲染页面
     * */
    private int responseTotalCount;
    /**
     * 多个网络请求空数据的累加器
     * 用于实现多个网络请求全部完成在渲染页面
     * */
    private int emptyDataTotalCount;

    /**
     * 应用介绍部分的数据集合
     * */
    private List<AppIntroduceBase> mAppIntroduceInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_app_detail);

        //设置Toolbar
        getmToolbarTitle().setText(R.string.app_detail_title);
        getmToolbarTitle().setTextColor(Color.BLACK);
        getmToolbarTitle().setTextSize(TypedValue.COMPLEX_UNIT_SP,19);
        getToolbar().setBackgroundColor(Color.WHITE);
        setToolBarLeftImageBgRes(R.drawable.ic_back_home);
        getToolBarLeftImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setToolbarRightClickableAreaVisibility(false);
        setToolBarRightImageBgRes(R.drawable.ic_search);
        getToolBarRightImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTo(AppSearchActivity.class,false);
            }
        });

        mFragmentManager = getSupportFragmentManager();
        //设置radioGroup的按钮切换监听
        mRadioGroup.setOnCheckedChangeListener(this);
        //设置折叠动画
        setFoldAnim();

        LoadingAndRetryManager.BASE_LOADING_LAYOUT_ID = R.layout.base_loading;
        LoadingAndRetryManager.BASE_EMPTY_LAYOUT_ID = R.layout.base_empty;
        LoadingAndRetryManager.BASE_RETRY_LAYOUT_ID = R.layout.base_retry;
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(mDetailContent, new OnLoadingAndRetryListener() {
            @Override
            public void setRetryEvent(View retryView) {
                    mRetryViewLayout = retryView;
            }
        });

        if (mRetryViewLayout != null) {
            mRetryViewLayout.findViewById(R.id.id_btn_retry).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadData();
                }
            });
        }

        //网络请求获取数据
        loadData();
    }

    private void loadData() {
        mLoadingAndRetryManager.showLoading();
        //重置多网络请求的计数器
        responseTotalCount = 0;
        emptyDataTotalCount = 0;

        //在请求数据前，检查网络状况是否良好
        if(!NetUtils.isNetworkConnected(this)){
            mLoadingAndRetryManager.showRetry();
            Toast.makeText(this, getResources().getString(R.string.network_not_connected), Toast.LENGTH_SHORT).show();
            return;
        }

        getHeaderPanelData();
        getAppIntroduceData();
    }

    /**
     * 应用详情应用介绍页面的网络请求
     * 该请求并没有在应用介绍的碎片中请求，
     * 目的在于实现与应用详情头部数据的网络请求共同完成后渲染页面
     * */
    private void getAppIntroduceData() {
        OkGo.<String>get("http://v.juhe.cn/toutiao/index")
                .tag(TAG_CANCEL_REQUEST_INTRODUCE)
                .params("key","8ba9b86990513b9629bbf60510b39224")
                .execute(new StringCallback() {

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        //TODO 后期改成LogUtils
                        Log.d(TAG,response.getException().getMessage());
                        sendResponseMessage(true,0);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.d(TAG,response.body());
                        AppIntroduceEntity appIntroduceEntity = new AppIntroduceEntity();

                        PreImageEntity preImageEntity = new PreImageEntity();
                        preImageEntity.setTitle("应用截图");
                        List<String> imgs = new ArrayList<>();
                        imgs.add("http://img.my.csdn.net/uploads/201508/05/1438760758_3497.jpg");
                        imgs.add("http://img.my.csdn.net/uploads/201508/05/1438760758_6667.jpg");
                        imgs.add("http://img.my.csdn.net/uploads/201508/05/1438760757_3588.jpg");
                        imgs.add("http://img.my.csdn.net/uploads/201508/05/1438760756_3304.jpg");
                        preImageEntity.setImgs(imgs);
                        appIntroduceEntity.setPreImageEntity(preImageEntity);

                        AppInfoEntity appInfoEntity = new AppInfoEntity();
                        appInfoEntity.setTitle("应用信息");
                        appInfoEntity.setContent("当前版本:v2.9.1\n应用大小:12MB\n更新时间:2018/10/28\n" +
                                "应用等级:二类应用\n管理部门:安徽省公安厅信息科");
                        appIntroduceEntity.setAppInfoEntity(appInfoEntity);

                        AppSummaryEntity appSummaryEntity = new AppSummaryEntity();
                        appSummaryEntity.setTitle("应用简介");
                        appSummaryEntity.setContent("xxxxxxxx\nxxxxxxxxxxxxxx\nxxxxxxxxxxxxxxx\nxxxxxxx\nxxxxxxxxxxxxx");
                        appIntroduceEntity.setAppSummaryEntity(appSummaryEntity);

                        AppUpdateLogEntity appUpdateLogEntity = new AppUpdateLogEntity();
                        appUpdateLogEntity.setTitle("升级日志");
                        appUpdateLogEntity.setContent("xxxxxxxxxxxx\nxxxxxxxxxxxx");
                        appIntroduceEntity.setAppUpdateLogEntity(appUpdateLogEntity);

                        AppLabelEntity appLabelEntity = new AppLabelEntity();
                        appLabelEntity.setTitle("应用标签");
                        List<String> labes = new ArrayList<>();
                        labes.add("综合要闻");
                        labes.add("综合要闻");
                        labes.add("综合要闻");
                        labes.add("综合要闻");
                        labes.add("综合要闻");
                        appLabelEntity.setContent(labes);
                        appIntroduceEntity.setAppLabelEntity(appLabelEntity);

                        AppDeveloperInfoEntity appDeveloperInfoEntity = new AppDeveloperInfoEntity();
                        appDeveloperInfoEntity.setTitle("开发厂商");
                        appDeveloperInfoEntity.setContent("开发商:安徽力瀚科技有限13645532096公司\n运维人员:耿悦\n运维电话:13645532096 13645538096");
                        appIntroduceEntity.setAppDeveloperInfoEntity(appDeveloperInfoEntity);

                        mAppIntroduceInfos = new ArrayList<>();
                        mAppIntroduceInfos.add(preImageEntity);
                        mAppIntroduceInfos.add(appInfoEntity);
                        mAppIntroduceInfos.add(appSummaryEntity);
                        mAppIntroduceInfos.add(appUpdateLogEntity);
//                        mAppIntroduceInfos.add(appLabelEntity);
                        mAppIntroduceInfos.add(appDeveloperInfoEntity);

                        sendResponseMessage(false,0);
                    }
                });
    }

    /**
     * 获取头部面板数据的网络请求
     * */
    private void getHeaderPanelData() {
        OkGo.<String>get("http://apis.juhe.cn/cook/query.php")
                .tag(TAG_CANCEL_REQUEST_HEADER_PANEL)
                .params("key","469fd874804b7e62c4e20da9c1b624b0")
                .params("menu","红烧肉")
                .execute(new StringCallback() {

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.d(TAG,"--------"+response.getException().getMessage());
                        sendResponseMessage(true,0);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.d(TAG,"--------"+response.body());
                        sendResponseMessage(false,0);
                    }
                });
    }

    /**
     * 接受多个网络请求的结果信息
     * @param isError 网络请求是否发生异常 true 是 false 否
     * @param empty 网络请求的结果是不是空数据.empty的取值范围0或者1
     *              0表示不是空数据，1表示当前网络请求是空数据
     * */
    private void sendResponseMessage(boolean isError,@IntRange(from = 0,to = 1) int empty) {

        if (isError) {
            OkGo.getInstance().cancelTag(TAG_CANCEL_REQUEST_HEADER_PANEL);
            OkGo.getInstance().cancelTag(TAG_CANCEL_REQUEST_INTRODUCE);
            if (mLoadingAndRetryManager != null) {
                Log.d(TAG, "loadingAndRetryManager");
                mLoadingAndRetryManager.showRetry();
            }
            return;
        }

        emptyDataTotalCount += empty;

        responseTotalCount++;
        if (responseTotalCount == 2) {
            if (emptyDataTotalCount == 0) {
                //TODO 注意这个判空操作一定要加
                if (mLoadingAndRetryManager != null) {
                    mLoadingAndRetryManager.showContent();
                    mRadioGroup.check(R.id.btn_introduce);
                }
            }else{
                if (mLoadingAndRetryManager != null) {
                    mLoadingAndRetryManager.showEmpty();
                }
            }
        }
    }

    private void setFoldAnim() {
        mImgDisplayMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDown) {

                    if (mUpAnimatorSet != null && mUpAnimatorSet.isRunning()) {
                        return;
                    }

                    MoreLayoutWrapper layoutWrapper = new MoreLayoutWrapper(mLayoutDisplayMore);
                    int height = ScreenUtil.dp2px(58f);

                    ObjectAnimator layoutDownAnimator = ObjectAnimator.ofInt(layoutWrapper, "height", 0, height);
                    ObjectAnimator imgMoreAnimatorDown = ObjectAnimator.ofFloat(mImgDisplayMore,"rotationX",0f,180f);
                    if (mDownAnimatorSet == null) {
                        mDownAnimatorSet = new AnimatorSet();
                    }
                    mDownAnimatorSet.setDuration(500);
                    mDownAnimatorSet.setInterpolator(new BounceInterpolator());
                    mDownAnimatorSet.playTogether(layoutDownAnimator,imgMoreAnimatorDown);
                    mDownAnimatorSet.start();

                    isDown = false;
                }else{

                    if (mDownAnimatorSet != null && mDownAnimatorSet.isRunning()) {
                        return;
                    }

                    MoreLayoutWrapper layoutWrapper = new MoreLayoutWrapper(mLayoutDisplayMore);
                    int height = ScreenUtil.dp2px(58f);

                    ObjectAnimator layoutUpAnimator = ObjectAnimator.ofInt(layoutWrapper, "height", height,0);
                    ObjectAnimator imgMoreAnimatorUp = ObjectAnimator.ofFloat(mImgDisplayMore,"rotationX",180f,0f);
                    if (mUpAnimatorSet == null) {
                        mUpAnimatorSet = new AnimatorSet();
                    }
                    mUpAnimatorSet.setDuration(500);
                    mUpAnimatorSet.setInterpolator(new AccelerateInterpolator());
                    mUpAnimatorSet.playTogether(layoutUpAnimator,imgMoreAnimatorUp);
                    mUpAnimatorSet.start();

                    isDown = true;
                }
            }
        });
    }

    @Override
    protected boolean isShowBacking() {
        return false;
    }

    @Override
    protected void setStatusBarColor() {
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                StatusBarUtil.setColor(this,Color.WHITE,0);
         }else{
             StatusBarUtil.setColor(this,Color.BLACK,0);
         }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        hideAllFragment(fragmentTransaction);
        Fragment presentFragment = null;
        switch (checkedId) {
            case R.id.btn_introduce:
                if (Analyzing.isEmpty(mAppIntroduceFragment)) {
                    mAppIntroduceFragment = AppIntroduceFragment.newInstance(mAppIntroduceInfos);
                    fragmentTransaction.add(R.id.fm_app_detail, mAppIntroduceFragment);
                }
                presentFragment = mAppIntroduceFragment;
                break;
            case R.id.btn_all_comment:
                if (Analyzing.isEmpty(mAppAllCommentFragment)) {
                    mAppAllCommentFragment = AppAllCommentFragment.newInstance("", "");
                    fragmentTransaction.add(R.id.fm_app_detail, mAppAllCommentFragment);
                }
                presentFragment = mAppAllCommentFragment;
                break;
            case R.id.btn_comment:
                if (Analyzing.isEmpty(mAppCommitCommentFragment)) {
                    mAppCommitCommentFragment = AppCommitCommentFragment.newInstance("", "");
                    fragmentTransaction.add(R.id.fm_app_detail, mAppCommitCommentFragment);
                }
                presentFragment = mAppCommitCommentFragment;
                break;
        }
        fragments.add(presentFragment);
        fragmentTransaction.show(presentFragment);
        fragmentTransaction.commit();
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

    //显示更多操作按钮的布局包装类，用于对布局组件的高度做属性动画
    private static class MoreLayoutWrapper{

        private View mTarget;

        MoreLayoutWrapper(View target) {
            this.mTarget = target;
        }

        int getHeight() {
            if (mTarget != null) {
                return mTarget.getLayoutParams().height;
            }
            return 0;
        }

        void setHeight(int height) {
            if (mTarget != null) {
                mTarget.getLayoutParams().height = height;
                mTarget.requestLayout();
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消网络请求
        OkGo.getInstance().cancelTag(TAG_CANCEL_REQUEST_HEADER_PANEL);
        OkGo.getInstance().cancelTag(TAG_CANCEL_REQUEST_INTRODUCE);

        //取消动画
        if (mDownAnimatorSet != null && mDownAnimatorSet.isRunning()) {
            mDownAnimatorSet.cancel();
        }
        if (mUpAnimatorSet != null && mUpAnimatorSet.isRunning()) {
            mUpAnimatorSet.cancel();
        }

        AppStore.getRefWatcher(getApplicationContext()).watch(this);
    }
}
