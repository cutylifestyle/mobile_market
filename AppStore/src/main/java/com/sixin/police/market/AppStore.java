package com.sixin.police.market;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.sixin.police.market.bean.UserInfoBean;
import com.sixin.police.market.utils.AppUtils;
import com.sixin.police.market.utils.LogUtils;
import com.sixin.police.market.utils.SharedPreferencesUtils;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by malx on 2018/11/13
 */
public class AppStore extends Application {

    private static float sNoncompatDensity;
    private static float sNoncompatScaledDensity;

    private RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
//        setCustomDensity(this);
        // 初始化
        AppUtils.init(this);
        // 图片加载默认初始化
        Fresco.initialize(this);

        mRefWatcher = setupLeakCanary();
    }

    private RefWatcher setupLeakCanary(){
        if(LeakCanary.isInAnalyzerProcess(this)){
            return RefWatcher.DISABLED;
        }
        return LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher(Context context) {
        AppStore app = (AppStore) context.getApplicationContext();
        return app.mRefWatcher;
    }

    /**
     * 适配
     * @param application
     */
    private static void setCustomDensity(@NonNull final Application application) {
        final DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();

        if (sNoncompatDensity == 0) {
            sNoncompatDensity = appDisplayMetrics.density;
            sNoncompatScaledDensity = appDisplayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        sNoncompatScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }

        final float targetDensity = appDisplayMetrics.widthPixels / 640;
        final float targetScaledDensity = targetDensity * (sNoncompatScaledDensity / sNoncompatDensity);
        final int targetDensityDpi = (int) (160 * targetDensity);

        appDisplayMetrics.density = targetDensity;
        appDisplayMetrics.scaledDensity = targetScaledDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;

        //final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        //activityDisplayMetrics.density = targetDensity;
        //activityDisplayMetrics.scaledDensity = targetScaledDensity;
        //activityDisplayMetrics.densityDpi = targetDensityDpi;
    }

    /**
     * 获取用户登录信息
     * @return
     */
    public UserInfoBean getUserInfo(){
        UserInfoBean userInfo = null;
        userInfo = SharedPreferencesUtils.getObject(UserInfoBean.class.getCanonicalName(), UserInfoBean.class);
        return  userInfo;
    }

    /**
     * 注册eventbus
     *
     * @param subscriber
     */
    public void setRegister(Object subscriber) {
        LogUtils.e("注册" + subscriber.getClass().getName());
        if (!EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().register(subscriber);
        }
    }

    /**
     * 反注册eventbus
     *
     * @param subscriber
     */
    public void unRegister(Object subscriber) {
        LogUtils.e("取消" + subscriber.getClass().getName());
        if (EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().unregister(subscriber);
        }
    }


}
