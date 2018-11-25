package com.sixinfor.common.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

public class ServiceUtil {
	
	
	/**
	 * 判断后台Service是否启动
	 * @param context
	 * @param clazz
	 * @return
	 */
	public static boolean isServiceWorked(Context context, Class<? extends Service> clazz) {
		ActivityManager myManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		ArrayList<RunningServiceInfo> runningService = (ArrayList<RunningServiceInfo>) myManager.getRunningServices(30);
		for (int i = 0; i < runningService.size(); i++) {
			if (runningService.get(i).service.getClassName().toString().equals(clazz.getName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断某个服务是否正在运行的方法
	 *
	 * @param mContext
	 * @param serviceName
	 *            是包名+服务的类名（例如：net.long.TestService）
	 * @return true代表正在运行，false代表服务没有正在运行
	 */
	public boolean isServiceWork(Context mContext, String serviceName) {
		boolean isWork = false;
		ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> runningServiceInfos = activityManager.getRunningServices(40);
		if (runningServiceInfos.size() <= 0) {
			return false;
		}
		for (int i = 0; i < runningServiceInfos.size(); i++) {
			String mName = runningServiceInfos.get(i).service.getClassName().toString();
			if (mName.equals(serviceName)) {
				isWork = true;
				break;
			}
		}
		return isWork;
	}
	
	/**
	 * 启动一个后台Service
	 * @param context
	 * @param clazz
	 */
	public static void startService(Context context, Class<? extends Service> clazz){
		Intent service = new Intent();
		service.setClass(context, clazz);
		context.startService(service);
	}
	
	/**
	 * 停止一个后台Service
	 * @param context
	 * @param clazz
	 */
	public static void stopService(Context context, Class<? extends Service> clazz){
		Intent service = new Intent();
		service.setClass(context, clazz);
		context.stopService(service);
	}
	
}
