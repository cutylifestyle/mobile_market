package com.sixin.police.market.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import com.sixin.police.market.utils.AppUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by malia on 2017/6/16 10:16.
 *
 * @project SixinIMAndroidProject
 * @package：com.sixinfor.im.widget
 * @describe：屏幕相关工具类
 * @change 规范相关代码
 * @chang time 2017/7/5
 */

public class ScreenUtil {

	private static int screenWidth = 0;

	private static int screenHeight = 0;
	private static int screenTotalHeight = 0;
	private static int statusBarHeight = 0;

	private static final int TITLE_HEIGHT = 0;

	/**
	 * 获取屏幕的宽度
	 *
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context) {
		/*if (screenWidth != 0) {return screenWidth;}*/
		/*screenWidth = ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
		screenHeight = ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight();*/
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;
		return screenWidth;
	}

	/**
	 * 获取屏幕的高度
	 *
	 * @param context
	 * @return
	 */
	public static int getScreenHeight(Context context) {
		/*if (screenHeight != 0) {return screenHeight;}*/
		int top = 0;
		if (context instanceof Activity) {
			top = ((Activity) context).getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
			if (top == 0) {
				top = (int) (TITLE_HEIGHT * getScreenDensity(context));
			}
		}
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(dm);
		screenHeight = dm.heightPixels - top;
		return screenHeight;
	}

	public static int getScreenTotalHeight(Context context) {
		if (screenTotalHeight != 0) {
			return screenTotalHeight;
		}
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		screenTotalHeight = displayMetrics.heightPixels;
		return screenTotalHeight;
	}

	/**
	 * 获取屏幕密度
	 *
	 * @param context
	 * @return
	 */
	public static float getScreenDensity(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metric = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(metric);
		return metric.density;
	}

	public static float getScreenDensityDpi(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metric = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(metric);
		return metric.densityDpi;
	}

	/**
	 * 获取statusBar高度
	 * @return
	 */
	public int getStatusBarHeight() {
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, statusBarHeight = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = AppUtils.getContext().getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return statusBarHeight;
	}

	/**
	 * 获取状态栏bar高度
	 *
	 * @param mContext
	 * @return
	 */
	public static int getStatusBarHeight(Context mContext) {
		if (statusBarHeight != 0) {
			return statusBarHeight;
		}
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = mContext.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return statusBarHeight;
	}

	public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

	/**
	 * 获取屏幕原始尺寸高度，包括虚拟功能键高度
	 *
	 * @param context
	 * @return
	 */
	public static int getDpi(Context context){
		int dpi = 0;
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();
		DisplayMetrics displayMetrics = new DisplayMetrics();
		@SuppressWarnings("rawtypes")
        Class c;
		try {
			c = Class.forName("android.view.Display");
			@SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics",DisplayMetrics.class);
			method.invoke(display, displayMetrics);
			dpi=displayMetrics.heightPixels;
		}catch(Exception e){
			e.printStackTrace();
		}
		return dpi;
	}

	/**
	 * 获取 虚拟按键的高度
	 * @param context
	 * @return
	 */
	public static  int getBottomStatusHeight(Context context){
		int totalHeight = getDpi(context);
		int contentHeight = getScreenHeight(context);
		return totalHeight  - contentHeight;
	}

	/**
	 * 标题栏高度
	 * @return
	 */
	public static int getTitleHeight(Activity activity){
		return  activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
	}

	/**
	 * 获得状态栏的高度
	 *
	 * @param context
	 * @return
	 */
	public static int getStatusHeight(Context context)
	{
		int statusHeight = -1;
		try
		{
			Class<?> clazz = Class.forName("com.android.internal.R$dimen");
			Object object = clazz.newInstance();
			int height = Integer.parseInt(clazz.getField("status_bar_height")
					.get(object).toString());
			statusHeight = context.getResources().getDimensionPixelSize(height);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return statusHeight;
	}

	/**
	 * 通过反射，获取包含虚拟键的整体屏幕高度
	 *
	 * @return
	 */
	public static int getHasVirtualKey(Activity mContext) {
		int dpi = 0;
		Display display = mContext.getWindowManager().getDefaultDisplay();
		DisplayMetrics dm = new DisplayMetrics();
		@SuppressWarnings("rawtypes")
		Class c;
		try {
			c = Class.forName("android.view.Display");
			@SuppressWarnings("unchecked")
			Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
			method.invoke(display, dm);
			dpi = dm.heightPixels;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dpi;
	}

	/**
	 * 获取屏幕尺寸，但是不包括虚拟功能高度
	 *
	 * @return
	 */
	public static int getNoHasVirtualKey(Activity mActivity) {
		@SuppressWarnings("deprecation")
		int height = mActivity.getWindowManager().getDefaultDisplay().getHeight();
		return height;
	}

	/**
	 * 获取 虚拟按键的高度
	 *
	 * @param mActivity
	 * @return
	 */
	public static int getBottomStatusHeight(Activity mActivity) {
		return getHasVirtualKey(mActivity) - getNoHasVirtualKey(mActivity);
	}

	/**
	 * 获取是否存在NavigationBar
	 *
	 * @param context
	 * @return
	 */
	public static boolean checkDeviceHasNavigationBar(Context context) {
		boolean hasNavigationBar = false;
		Resources rs = context.getResources();
		int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
		if (id > 0) {
			hasNavigationBar = rs.getBoolean(id);
		}
		try {
			@SuppressWarnings("rawtypes")
			Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
			@SuppressWarnings("unchecked")
			Method m = systemPropertiesClass.getMethod("get", String.class);
			String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
			if ("1".equals(navBarOverride)) {
				hasNavigationBar = false;
			} else if ("0".equals(navBarOverride)) {
				hasNavigationBar = true;
			}
		} catch (Exception e) {

		}
		return hasNavigationBar;
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 *
	 * @param spValue
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 *
	 * @param spValue
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int sp2px(float spValue) {
		final float fontScale = AppUtils.getContext().getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	/**
	 * converts dip to px
	 *
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public static int dp2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * converts dip to px
	 *
	 * @param dip
	 * @return
	 */
	public static int dp2px(float dip) {
		return (int) (dip * AppUtils.getContext().getResources().getDisplayMetrics().density + 0.5f);
	}

	/**
	 * px2dp
	 *
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public static int px2dp(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * px2dp
	 *
	 * @param pxValue
	 * @return
	 */
	public static int px2dp(float pxValue) {
		final float scale = AppUtils.getContext().getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
}