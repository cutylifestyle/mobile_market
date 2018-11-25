package com.lehand.smartrefresh.api;

import com.lehand.smartrefresh.listener.OnRefreshListener;
import com.lehand.smartrefresh.listener.OnRefreshLoadMoreListener;

/**
 * 刷新加载组合监听器
 * @deprecated 使用 {@link OnRefreshLoadMoreListener} 代替
 * Created by SCWANG on 2017/5/26.
 */
@Deprecated
public interface OnRefreshLoadmoreListener extends OnRefreshListener, OnLoadmoreListener {
}
