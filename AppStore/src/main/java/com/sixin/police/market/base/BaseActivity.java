package com.sixin.police.market.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.sixin.police.market.R;
import com.sixin.police.market.utils.AToast;
import com.sixin.police.market.utils.Analyzing;
import com.sixin.police.market.utils.AppUtils;
import com.sixin.police.market.utils.ScreenUtil;
import com.sixin.police.market.utils.StatusBarUtil;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by malia on 2017/4/18.
 */
public class BaseActivity extends AppCompatActivity {

    public static boolean USE_ACT_STACK = false;

    //If this view is optional add '@Nullable' (fields) or '@Optional' (methods) annotation.
    @Nullable
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_subtitle)
    @Nullable
    TextView mToolbarSubTitle;
    @BindView(R.id.toolbar)
    @Nullable
    Toolbar mToolbar;
    @BindView(R.id.toolbar_leftimage)
    @Nullable
    ImageView leftImageView;
    @BindView(R.id.toolbar_rightimage)
    @Nullable
    ImageView rightImageView;
    //右侧点击区域
    @BindView(R.id.right_clickable_area_rl)
    @Nullable
    View rightClickableArea;
    //Error:(56, 23) 错误: @BindView fields must not be private or static.
    // (com.sixinfor.partybuilding.base.BaseActivity.rightImageView)

    private FrameLayout parent;
    private View stasBarView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.BLUE);
        }*/
        //initialization <code></code>
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {

            }
        });

        getSupportFragmentManager().registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
            /**
             * Called right before the fragment's {@link Fragment#onAttach(Context)} method is called.
             * This is a good time to inject any required dependencies for the fragment before any of
             * the fragment's lifecycle methods are invoked.
             *
             * @param fm      Host FragmentManager
             * @param f       Fragment changing state
             * @param context Context that the Fragment is being attached to
             */
            @Override
            public void onFragmentPreAttached(FragmentManager fm, Fragment f, Context context) {
                super.onFragmentPreAttached(fm, f, context);
            }

            /**
             * Called after the fragment has been attached to its host. Its host will have had
             * <code>onAttachFragment</code> called before this call happens.
             *
             * @param fm      Host FragmentManager
             * @param f       Fragment changing state
             * @param context Context that the Fragment was attached to
             */
            @Override
            public void onFragmentAttached(FragmentManager fm, Fragment f, Context context) {
                super.onFragmentAttached(fm, f, context);
            }

            /**
             * Called after the fragment has returned from the FragmentManager's call to
             * {@link Fragment#onCreate(Bundle)}. This will only happen once for any given
             * fragment instance, though the fragment may be attached and detached multiple times.
             *
             * @param fm                 Host FragmentManager
             * @param f                  Fragment changing state
             * @param savedInstanceState Saved instance bundle from a previous instance
             */
            @Override
            public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
                super.onFragmentCreated(fm, f, savedInstanceState);
            }

            /**
             * Called after the fragment has returned from the FragmentManager's call to
             * {@link Fragment#onActivityCreated(Bundle)}. This will only happen once for any given
             * fragment instance, though the fragment may be attached and detached multiple times.
             *
             * @param fm                 Host FragmentManager
             * @param f                  Fragment changing state
             * @param savedInstanceState Saved instance bundle from a previous instance
             */
            @Override
            public void onFragmentActivityCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
                super.onFragmentActivityCreated(fm, f, savedInstanceState);
            }

            /**
             * Called after the fragment has returned a non-null view from the FragmentManager's
             * request to {@link Fragment#onCreateView(LayoutInflater, ViewGroup, Bundle)}
             *
             * @param fm                 Host FragmentManager
             * @param f                  Fragment that created and owns the view
             * @param v                  View returned by the fragment
             * @param savedInstanceState Saved instance bundle from a previous instance
             */
            @Override
            public void onFragmentViewCreated(FragmentManager fm, Fragment f, View v, Bundle savedInstanceState) {
                super.onFragmentViewCreated(fm, f, v, savedInstanceState);
            }

            /**
             * Called after the fragment has returned from the FragmentManager's call to
             * {@link Fragment#onStart()}.
             *
             * @param fm Host FragmentManager
             * @param f  Fragment changing state
             */
            @Override
            public void onFragmentStarted(FragmentManager fm, Fragment f) {
                super.onFragmentStarted(fm, f);
            }

            /**
             * Called after the fragment has returned from the FragmentManager's call to
             * {@link Fragment#onResume()}.
             *
             * @param fm Host FragmentManager
             * @param f  Fragment changing state
             */
            @Override
            public void onFragmentResumed(FragmentManager fm, Fragment f) {
                super.onFragmentResumed(fm, f);
            }

            /**
             * Called after the fragment has returned from the FragmentManager's call to
             * {@link Fragment#onPause()}.
             *
             * @param fm Host FragmentManager
             * @param f  Fragment changing state
             */
            @Override
            public void onFragmentPaused(FragmentManager fm, Fragment f) {
                super.onFragmentPaused(fm, f);
            }

            /**
             * Called after the fragment has returned from the FragmentManager's call to
             * {@link Fragment#onStop()}.
             *
             * @param fm Host FragmentManager
             * @param f  Fragment changing state
             */
            @Override
            public void onFragmentStopped(FragmentManager fm, Fragment f) {
                super.onFragmentStopped(fm, f);
            }

            /**
             * Called after the fragment has returned from the FragmentManager's call to
             * {@link Fragment#onSaveInstanceState(Bundle)}.
             *
             * @param fm       Host FragmentManager
             * @param f        Fragment changing state
             * @param outState Saved state bundle for the fragment
             */
            @Override
            public void onFragmentSaveInstanceState(FragmentManager fm, Fragment f, Bundle outState) {
                super.onFragmentSaveInstanceState(fm, f, outState);
            }

            /**
             * Called after the fragment has returned from the FragmentManager's call to
             * {@link Fragment#onDestroyView()}.
             *
             * @param fm Host FragmentManager
             * @param f  Fragment changing state
             */
            @Override
            public void onFragmentViewDestroyed(FragmentManager fm, Fragment f) {
                super.onFragmentViewDestroyed(fm, f);
            }

            /**
             * Called after the fragment has returned from the FragmentManager's call to
             * {@link Fragment#onDestroy()}.
             *
             * @param fm Host FragmentManager
             * @param f  Fragment changing state
             */
            @Override
            public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
                super.onFragmentDestroyed(fm, f);
            }

            /**
             * Called after the fragment has returned from the FragmentManager's call to
             * {@link Fragment#onDetach()}.
             *
             * @param fm Host FragmentManager
             * @param f  Fragment changing state
             */
            @Override
            public void onFragmentDetached(FragmentManager fm, Fragment f) {
                super.onFragmentDetached(fm, f);
            }
        }, true);

        if (null != mToolbar) {
            setSupportActionBar(mToolbar);
            if (null != mToolbarTitle) {
                mToolbarTitle.setText(getTitle());
                getSupportActionBar().setDisplayShowTitleEnabled(false);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        setStatusBarColor();
        ButterKnife.bind(this, this.getWindow().getDecorView());
    }

    private void setStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // Translucent navigation bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        /**
         * 判断是否有Toolbar,并默认显示返回按钮
         */
        if (!Analyzing.isEmpty(getToolbar()) && isShowBacking()) {
            showBack(needToReturn());
        }
    }

    /**
     * Toast
     * @param msg
     */
    public void Toasting(String msg) {
        if (Analyzing.isEmpty(msg)) {
            return;
        }
        AToast.show(msg);
    }

    /**
     * 获取头部标题的TextView
     *
     * @return
     */
    public TextView getmToolbarTitle() {
        if (null != mToolbarTitle) {
            return mToolbarTitle;
        } else {
            return (TextView) findViewById(R.id.toolbar_title);
        }
    }

    /**
     * 获取头部副标题的TextView
     *
     * @return
     */
    public TextView getmToolbarSubTitle() {
        if (null != mToolbarSubTitle) {
            return mToolbarSubTitle;
        } else {
            return (TextView) findViewById(R.id.toolbar_subtitle);
        }
    }

    /**
     * 是否显示右侧副标题的TextView
     *
     * @param isHide
     */
    public void setToolBarSubTitleVisibility(boolean isHide) {
        if (!Analyzing.isEmpty(mToolbarSubTitle)) {
            mToolbarSubTitle.setVisibility(isHide ? View.GONE : View.VISIBLE);
        } else {
            if (!Analyzing.isEmpty(findViewById(R.id.toolbar_subtitle))) {
                findViewById(R.id.toolbar_subtitle).setVisibility(isHide ? View.GONE : View.VISIBLE);
            }
        }
    }

    /**
     * 获取Toolbar
     *
     * @return
     */
    public Toolbar getToolbar() {
        if (null != mToolbar) {
            return mToolbar;
        } else {
            return (Toolbar) findViewById(R.id.toolbar);
        }
    }

    /**
     * 设置头部标题
     *
     * @param title
     */
    @Deprecated
    public void setToolBarTitle(CharSequence title) {
        if (mToolbarTitle != null) {
            mToolbarTitle.setText(title);
        } else {
            getToolbar().setTitle(title);
            setSupportActionBar(getToolbar());
        }
    }

    /**
     * 获取左侧的ImageView
     *
     * @return
     */
    public ImageView getToolBarLeftImageView() {
        if (!Analyzing.isEmpty(leftImageView)) {
            return leftImageView;
        } else if (!Analyzing.isEmpty(findViewById(R.id.toolbar_leftimage))) {
            return (ImageView) findViewById(R.id.toolbar_leftimage);
        }
        return null;
    }

    /**
     * 获取右侧的ImageView
     *
     * @return
     */
    public ImageView getToolBarRightImageView() {
        if (!Analyzing.isEmpty(rightImageView)) {
            return rightImageView;
        } else if (!Analyzing.isEmpty(findViewById(R.id.toolbar_rightimage))) {
            return (ImageView) findViewById(R.id.toolbar_rightimage);
        }
        return null;
    }

    /**
     * 获取右侧的点击区域View
     *
     * @return
     */
    public View getRightClickableAreaView() {
        if (!Analyzing.isEmpty(rightClickableArea)) {
            return rightClickableArea;
        } else if (!Analyzing.isEmpty(findViewById(R.id.right_clickable_area_rl))) {
            return findViewById(R.id.right_clickable_area_rl);
        }
        return null;
    }

    /**
     * 是否显示右侧点击区域View
     *
     * @param isHide
     */
    public void setToolbarRightClickableAreaVisibility(boolean isHide) {
        if (!Analyzing.isEmpty(rightClickableArea)) {
            rightClickableArea.setVisibility(isHide ? View.GONE : View.VISIBLE);
        } else {
            if (!Analyzing.isEmpty(findViewById(R.id.right_clickable_area_rl))) {
                findViewById(R.id.right_clickable_area_rl).setVisibility(isHide ? View.GONE : View.VISIBLE);
            }
        }
    }

    /**
     * 是否显示左侧图片
     *
     * @param isHide
     */
    public void setToolBarLeftImageVisibility(boolean isHide) {
        if (!Analyzing.isEmpty(leftImageView)) {
            leftImageView.setVisibility(isHide ? View.GONE : View.VISIBLE);
        } else {
            if (!Analyzing.isEmpty(findViewById(R.id.toolbar_leftimage))) {
                findViewById(R.id.toolbar_leftimage).setVisibility(isHide ? View.GONE : View.VISIBLE);
            }
        }
    }

    /**
     * 是否显示右侧图片
     *
     * @param isHide
     */
    public void setToolBarRightImageVisibility(boolean isHide) {
        if (!Analyzing.isEmpty(rightImageView)) {
            rightImageView.setVisibility(isHide ? View.GONE : View.VISIBLE);
            rightClickableArea.setVisibility(isHide ? View.GONE : View.VISIBLE);
        } else {
            if (!Analyzing.isEmpty(findViewById(R.id.toolbar_rightimage))) {
                findViewById(R.id.toolbar_rightimage).setVisibility(isHide ? View.GONE : View.VISIBLE);
            }
        }
    }

    /**
     * 设置左侧背景
     *
     * @param resid
     */
    public void setToolBarLeftImageBgRes(int resid) {
        if (!Analyzing.isEmpty(leftImageView)) {
            leftImageView.setImageResource(resid);
            leftImageView.setVisibility(View.VISIBLE);
        } else {
            if (!Analyzing.isEmpty(findViewById(R.id.toolbar_leftimage))) {
                ((ImageView) findViewById(R.id.toolbar_leftimage)).setImageResource(resid);
                findViewById(R.id.toolbar_leftimage).setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 设置右侧背景
     *
     * @param resid
     */
    //@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void setToolBarRightImageBgRes(int resid) {
        if (!Analyzing.isEmpty(rightImageView)) {
            rightImageView.setImageResource(resid);
            rightImageView.setVisibility(View.VISIBLE);
        } else {
            if (!Analyzing.isEmpty(findViewById(R.id.toolbar_rightimage))) {
                ((ImageView) findViewById(R.id.toolbar_rightimage)).setImageResource(resid);
                findViewById(R.id.toolbar_rightimage).setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 版本号小于21的后退按钮图片
     *
     * @param b
     */
    private void showBack(boolean b) {
        //setNavigationIcon必须在setSupportActionBar(toolbar);方法后面加入
        //if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {}
        //getToolbar().setNavigationIcon(R.mipmap.icon_back);
        getToolbar().setTitle("");
        if (Analyzing.isEmpty(getSupportActionBar())) {
            setSupportActionBar(getToolbar());
            if (!Analyzing.isEmpty(getSupportActionBar())) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (needToReturn()) {
                    onBackPressed();
                } else {
                    removeFragment();
                }
            }
        });
    }

    /**
     * 是否需要返回当前页面的上一层
     *
     * @return
     */
    protected boolean needToReturn() {
        return false;
    }

    /**
     * 是否显示后退按钮,默认显示,可在子类重写该方法.
     *
     * @return
     */
    protected boolean isShowBacking() {
        return true;
    }

    /**
     * 创建状态栏填充的 statusBarView
     *
     * @return
     */
    private View creatStatusBar() {
        int height = ScreenUtil.getStatusBarHeight(this);
        View statusBarView = new View(this);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        statusBarView.setBackgroundResource(R.color.colorAccent);
        statusBarView.setLayoutParams(lp);
        return statusBarView;
    }

    /**
     * 设置透明度
     *
     * @param bgAlhpa
     */
    protected void setBackgroundAlpha(float bgAlhpa) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlhpa;
        if (bgAlhpa == 1) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        getWindow().setAttributes(lp);
    }

    /**
     * 设置状态栏颜色也就是所谓沉浸式状态栏
     *
     * @Return
     */
    protected void setStatusBarColor() {
        /**
         * Android4.4以上  但是抽屉有点冲突，目前就重写一个方法暂时解决4.4的问题
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getToolbar() != null) {
                int flags = getToolbar().getSystemUiVisibility();
                // Color.parseColor("#d0000e")
                getWindow().setStatusBarColor(AppUtils.getColor(R.color.colorPrimary));
                flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
                //(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
                getToolbar().setSystemUiVisibility(flags);
            } else {
                StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary), 0);
            }
        } else {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary), 0);
        }
    }

    /**
     * 透明状态栏
     * android 4.4 kitkat及以上版本可用
     *
     * @param isTrans
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void setStatusBarTrans(boolean isTrans) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = this.getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (isTrans) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
    }

    /**
     * 设置高亮statusBar
     *
     * @param view
     */
    protected void setLightStatusBar(@NonNull View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            getWindow().setStatusBarColor(Color.BLUE);
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
        }
    }

    /**
     * 隐藏虚拟键
     * SDK版本要大于4.0才有用
     *
     * @param isAutoHideNav
     * @param isHideKey
     */
    public void setAutoHideNavBar(boolean isAutoHideNav, boolean isHideKey) {

        if (Build.VERSION.SDK_INT >= 14) {
            if (isAutoHideNav) {
                WindowManager.LayoutParams params = this.getWindow().getAttributes();
                params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
                if (isHideKey) {
                    params.systemUiVisibility = params.systemUiVisibility | View.SYSTEM_UI_FLAG_LOW_PROFILE;
                }
                this.getWindow().setAttributes(params);
            } else {
                WindowManager.LayoutParams params = this.getWindow().getAttributes();
                params.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE;
                if (isHideKey) {
                    params.systemUiVisibility = params.systemUiVisibility | View.SYSTEM_UI_FLAG_LOW_PROFILE;
                }
                this.getWindow().setAttributes(params);
            }

        }
    }

    /**
     * activity全屏实现隐藏底部虚拟按键，并且触摸不会弹出虚拟按键
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    protected void hideNavigationBar() {
        final View decorView = getWindow().getDecorView();
        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(flags);
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                    decorView.setSystemUiVisibility(flags);
                }
            }
        });
    }

    public View topView;

    public void setTopView(View view) {
        this.topView = view;
    }

    public void setTopView(int id) {
        topView = findViewById(id);
    }

    public void setStatusBarColorTopView(String color) {
        if (topView != null) {
            topView.setBackgroundColor(Color.parseColor(color));
            topView.invalidate();
        }
    }

    public void setStatusBarColorTopView(int color) {
        if (topView != null) {
            topView.setBackgroundColor(color);
            topView.invalidate();
        }
    }

    /**
     * 获取主题色
     */
    public int getColorPrimary() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            TypedValue typedValue = new TypedValue();
            getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
            return typedValue.data;
        } else {
            return R.color.colorPrimary;
        }
    }

    /**
     * 获取深主题色
     */
    public int getDarkColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        return typedValue.data;
    }

    /**
     * 跳转到其他activity中
     *
     * @param cls              Activity.class
     * @param closeCurrentPage 是否关闭当前页面
     */
    public void moveTo(Class<?> cls, boolean closeCurrentPage) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        this.startActivity(intent);

        if (closeCurrentPage) {
            this.finish();
        }
    }

    /**
     * 跳转到其他activity中
     *
     * @param cls              Activity.class
     * @param closeCurrentPage 是否关闭当前页面
     */
    public void moveToForResult(Class<?> cls, boolean closeCurrentPage, String key, Bundle bundle) {
        Intent intent = new Intent();
        if (!Analyzing.isEmpty(key) && bundle != null) {
            intent.putExtra(key, bundle);
        }
        intent.setClass(this, cls);
        int requestCode = 101;
        this.startActivityForResult(intent, requestCode);
        if (closeCurrentPage) {
            this.finish();
        }
    }

    /**
     * 跳转到其他activity中
     *
     * @param cls              Activity.class
     * @param closeCurrentPage 是否关闭当前页面
     * @param key              bundle的key
     * @param bundle           数据
     */
    public void moveTo(Class<?> cls, boolean closeCurrentPage, String key, Bundle bundle) {
        Intent intent = new Intent();
        intent.putExtra(key, bundle);
        intent.setClass(this, cls);
        this.startActivity(intent);
        if (closeCurrentPage) {
            this.finish();
        }
    }

    /**
     * 添加fragment
     *
     * @param fragment
     */
    protected void addFragment(BaseFragment fragment) {
        if (!Analyzing.isEmpty(fragment)) {
            /*if (Analyzing.isNull(findViewById(R.id.layout_ui))) {return;}*/
            getSupportFragmentManager().beginTransaction().replace(R.id.layout_ui, fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commitAllowingStateLoss();
        }
    }

    /**
     * 移除fragment
     */
    protected void removeFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    /*@Override
    public boolean onNavigateUp() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1){
            getSupportFragmentManager().popBackStack();
            return true;
        }
        return super.onNavigateUp();
    }*/

    /**
     * 返回按键的监听
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 双击退出程序
     */
    private static Boolean isExit = false;

    public void exitByClick() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true;
            Toasting("再次点击退出该程序...");
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 1000);

        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
