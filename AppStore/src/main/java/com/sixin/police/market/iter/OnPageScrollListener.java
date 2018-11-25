package com.sixinfor.xc.zhdd.iter;

/**
 * Created by malia on 2017/7/14 17:24.
 *
 * @project PartyBuilding
 * @package：com.sixinfor.partybuilding.listener
 * @describe：ViewPage的页面滚动监听器
 * @change
 * @chang time
 */

public interface OnPageScrollListener {
    /**
     * 页面滚动时调用
     *
     * @param enterPosition 进入页面的位置
     * @param leavePosition 离开的页面的位置
     * @param percent       滑动百分比
     */
    void onPageScroll(int enterPosition, int leavePosition, float percent);

    /**
     * 页面选中时调用
     *
     * @param position 选中页面的位置
     */
    void onPageSelected(int position);

    /**
     * 页面滚动状态变化时调用
     *
     * @param state 页面的滚动状态
     */
    void onPageScrollStateChanged(int state);
}
