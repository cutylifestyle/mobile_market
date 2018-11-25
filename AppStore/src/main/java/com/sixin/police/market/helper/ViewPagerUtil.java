package com.sixin.police.market.helper;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import com.sixinfor.xc.zhdd.iter.OnPageScrollListener;

/**
 * Created by malia on 2017/7/14 17:27.
 *
 * @project PartyBuilding
 * @package：com.sixinfor.partybuilding.widget.utils
 * @describe：ViewPager工具类
 * 采用工具类ViewPagerUtil来绑定ViewPager和OnPageScrollListener
 * @change
 * @chang time
 */

public class ViewPagerUtil {
    /**
     * 给ViewPager绑定自定义的滚动监听
     *
     * @param viewPager
     * @param onPageScrollListener
     */
    public static void bind(@NonNull ViewPager viewPager, @NonNull OnPageScrollListener onPageScrollListener) {
        final ViewPagerHelper helper = new ViewPagerHelper();
        // 给helper设置滚动监听
        helper.setOnPageScrollListener(onPageScrollListener);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                helper.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                helper.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                helper.onPageScrollStateChanged(state);
            }
        });
    }
}
