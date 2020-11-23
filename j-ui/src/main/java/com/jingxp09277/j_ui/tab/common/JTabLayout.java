package com.jingxp09277.j_ui.tab.common;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/**
 * Created by LWB on 2020/11/23
 */
public interface JTabLayout<Tab extends ViewGroup, D> {
    Tab findTab(@NonNull D data);

    void addTabSelectedChangeListener(OnTabSelectedListener<D> listener);

    void defaultSelected(@NonNull D defaultInfo);

    void inflateInfo(@NonNull List<D> infoList);

    interface OnTabSelectedListener<D> {
        void onTabSelectedChange(int index, @Nullable D prevInfo, @NonNull D nextInfo);
    }
}
