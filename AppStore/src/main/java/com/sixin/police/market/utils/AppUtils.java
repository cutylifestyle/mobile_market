package com.sixin.police.market.utils;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.WindowManager;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

import butterknife.internal.Utils;

/**
 * Created by malia on 2017/6/12 15:41.
 *
 * @project SixinIMAndroidProject
 * @package：com.sixinfor.common.utils
 * @describe：These are common utils and can be used for other projects as well
 * @change
 * @chang time
 */

public final class AppUtils {

    private static Application mApplication;

    static WeakReference<Activity> mTopActivityWeakRef;
    static List<Activity> sActivityList = new LinkedList<Activity>();

    private AppUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static ActivityLifecycleCallbacks mCallbacks = new ActivityLifecycleCallbacks() {

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            sActivityList.add(activity);
            setTopActivityWeakRef(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
            setTopActivityWeakRef(activity);
        }

        @Override
        public void onActivityResumed(Activity activity) {
            setTopActivityWeakRef(activity);
        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            sActivityList.remove(activity);
        }
    };

    /**
     * 初始化工具类
     *
     * @param app
     *            应用
     */
    public static void init(@NonNull final Application app) {
        AppUtils.mApplication = app;
        app.registerActivityLifecycleCallbacks(mCallbacks);
    }

    /**
     * 得到上下文
     *
     * @return
     */
    public static Application getContext() {
        if (mApplication != null) {
            return mApplication;
        }
        throw new NullPointerException("u should init first");
    }

    /**
     * 设置当前显示的activity
     * @param activity
     */
    private static void setTopActivityWeakRef(Activity activity) {
        if (mTopActivityWeakRef == null || !activity.equals(mTopActivityWeakRef.get())) {
            mTopActivityWeakRef = new WeakReference<Activity>(activity);
        }
    }

    /**
     * 获取当前显示的activity
     * @return
     */
    public static Activity getTopActivity() {
        Activity topActivity = null;
        if (mTopActivityWeakRef != null) {
            topActivity = mTopActivityWeakRef.get();
        }
        return topActivity;
    }

    /**
     * 得到resources对象
     *
     * @return
     */
    public static Resources getResource() {
        return getContext().getResources();
    }

    /**
     * 得到string.xml中的字符串
     *
     * @param resId
     * @return
     */
    public static String getString(int resId) {
        return getResource().getString(resId);
    }

    /**
     * 得到string.xml中的字符串，带点位符
     *
     * @return
     */
    public static String getString(int id, Object... formatArgs) {
        return getResource().getString(id, formatArgs);
    }

    /**
     * 得到string.xml中和字符串数组
     *
     * @param resId
     * @return
     */
    public static String[] getStringArr(int resId) {
        return getResource().getStringArray(resId);
    }

    /**
     * 得到colors.xml中的颜色
     *
     * @param colorId
     * @return
     */
    public static int getColor(int colorId) {
        return getResource().getColor(colorId);
    }

    /**
     * 得到应用程序的包名
     *
     * @return
     */
    public static String getPackageName() {
        return getContext().getPackageName();
    }

    /**
     * 实现文本复制功能
     *
     * @param msg
     */
    public static void copy(String msg, Context mContext) {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setPrimaryClip(ClipData.newPlainText(null, msg.trim()));
    }

    /**
     * 实现粘贴功能
     *
     * @param context
     * @return
     */
    public static String paste(Context context) {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        return cmb.getText().toString().trim();
    }

    /**
     * @param context used to get system services
     * @return screenWidth in pixels
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        wm.getDefaultDisplay().getSize(size);
        return size.x;
    }

    /**
     * @param context used to get system services
     * @return screenWidth in pixels
     */
    public static int getScreenHight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        wm.getDefaultDisplay().getSize(size);
        return size.y;
    }

    /**
     * This method can be extended to get all android attributes color, string, dimension ...etc
     *
     * @param context          used to fetch android attribute
     * @param androidAttribute attribute codes like R.attr.colorAccent
     * @return in this case color of android attribute
     */
    public static int fetchContextColor(Context context, int androidAttribute) {
        TypedValue typedValue = new TypedValue();
        TypedArray a = context.obtainStyledAttributes(typedValue.data, new int[]{androidAttribute});
        int color = a.getColor(0, 0);
        a.recycle();
        return color;
    }

    /**
     * @param context used to fetch display metrics
     * @param dp      dp value
     * @return pixel value
     */
    public static int dp2px(Context context, float dp) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
        return Math.round(px);
    }

    /**
     * 提升读写权限
     *
     * @param filePath 文件路径
     * @return
     * @throws IOException
     */
    public static void setPermission(String filePath) {
        String command = "chmod" + "777" + filePath;
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return 当前版本名称
     */
    public static String getVersionName() {
        String name = "";
        try {
            PackageManager packageManager = getContext().getPackageManager();
            String pkname = getContext().getPackageName();
            PackageInfo packageInfo = packageManager.getPackageInfo(pkname, 0);
            name = packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }

    /**
     * @return 当前版本号
     */
    public static int getVersionCode() {
        int code = 0;
        try {
            PackageManager packageManager = getContext().getPackageManager();
            String pkname = getContext().getPackageName();
            PackageInfo packageInfo = packageManager.getPackageInfo(pkname, 0);
            code = packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return code;
    }

    /**SDCard 检测
     * @return :true has SDCard
     * 			false has no SDCard or SDCard can't be used
     */
    public static boolean hasSDCard(){
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            return true;
        }else {
            return false;
        }
    }
}
