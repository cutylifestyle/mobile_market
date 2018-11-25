package com.lehand.adapter.listener;

import android.support.v7.widget.RecyclerView;

/**
 * @author malx
 * @date 2018-11-16
 */
public interface OnItemDragListener {

    void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos);

    void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to);

    void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos);
}
