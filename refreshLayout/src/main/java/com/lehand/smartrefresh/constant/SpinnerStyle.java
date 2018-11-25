package com.lehand.smartrefresh.constant;

/**
 * 顶部和底部的组件在拖动时候的变换方式
 * Created by SCWANG on 2017/5/26.
 */

public enum SpinnerStyle {
    /**
     * 平行移动 特点: HeaderView高度不会改变
     */
    Translate,
    /**
     * 拉伸形变 特点：在下拉和上弹（HeaderView高度改变）时候，会自动触发OnDraw事件
     */
    Scale,
    /**
     *固定在背后 特点：HeaderView高度不会改变，
     */
    FixedBehind,
    /**
     *固定在前面 特点：HeaderView高度不会改变
     */
    FixedFront,
    /**
     *填满布局 特点：HeaderView高度不会改变，尺寸充满 RefreshLayout
     */
    MatchLayout
}