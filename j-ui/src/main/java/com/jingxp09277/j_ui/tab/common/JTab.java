package com.jingxp09277.j_ui.tab.common;

import androidx.annotation.NonNull;
import androidx.annotation.Px;

/**
 * Created by LWB on 2020/11/23
 */
public interface JTab<D> extends JTabLayout.OnTabSelectedListener<D> {
    void setJTabInfo(@NonNull D data);

    /**
     * 动态修改某个item的大小
     */
    void resetHeight(@Px int height);
}
