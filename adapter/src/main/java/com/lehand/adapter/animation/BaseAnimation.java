package com.lehand.adapter.animation;

import android.animation.Animator;
import android.view.View;

/**
 * @author malx
 * @date 2018-11-16
 */
public interface BaseAnimation {

    Animator[] getAnimators(View view);
}
