package com.lehand.adapter.entity;

import java.util.List;

/**
 * implement the interface if the item is expandable
 * @author malx
 * @date 2018-11-16
 */
public interface IExpandable<T> {
    boolean isExpanded();
    void setExpanded(boolean expanded);
    List<T> getSubItems();

    /**
     * Get the level of this item. The level start from 0.
     * If you don't care about the level, just return a negative.
     */
    int getLevel();
}
