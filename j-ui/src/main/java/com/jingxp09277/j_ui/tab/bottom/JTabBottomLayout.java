package com.jingxp09277.j_ui.tab.bottom;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.jingxp09277.j_ui.R;
import com.jingxp09277.j_ui.tab.common.JTabLayout;
import com.jinxp09277.j.library.util.JDisplayUtil;
import com.jinxp09277.j.library.util.JViewUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by LWB on 2020/11/23
 */
public class JTabBottomLayout extends FrameLayout implements JTabLayout<JTabBottom, JTabBottomInfo<?>> {
    private List<OnTabSelectedListener<JTabBottomInfo<?>>> tabSelectedChangeListeners = new ArrayList<>();
    private JTabBottomInfo<?> selectedInfo;
    private float bottomAlpha = 1f;
    //TabBottom高度
    private float tabBottomHeight = 50;
    //TabBottom的头部线条高度
    private float bottomLineHeight = 0.5f;
    //TabBottom的头部线条颜色
    private String bottomLineColor = "#dfe0e1";
    private List<JTabBottomInfo<?>> infoList;

    public JTabBottomLayout(@NonNull Context context) {
        super(context);
    }

    public JTabBottomLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public JTabBottomLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public JTabBottom findTab(@NonNull JTabBottomInfo<?> info) {
        ViewGroup ll = findViewWithTag(TAG_TAB_BOTTOM);
        for (int i = 0; i < ll.getChildCount(); i++) {
            View child = ll.getChildAt(i);
            if (child instanceof JTabBottom) {
                JTabBottom tab = (JTabBottom) child;
                if (tab.getJTabInfo() == info) {
                    return tab;
                }
            }
        }
        return null;
    }

    @Override
    public void addTabSelectedChangeListener(OnTabSelectedListener<JTabBottomInfo<?>> listener) {
        tabSelectedChangeListeners.add(listener);
    }

    @Override
    public void defaultSelected(@NonNull JTabBottomInfo<?> defaultInfo) {
        onSelected(defaultInfo);
    }

    private static final String TAG_TAB_BOTTOM = "TAG_TAB_BOTTOM";

    @Override
    public void inflateInfo(@NonNull List<JTabBottomInfo<?>> infoList) {
        if (infoList.isEmpty()) {
            return;
        }
        this.infoList = infoList;
        //移除之前已经添加的view
        for (int i = getChildCount() - 1; i > 0; i--) {
            removeViewAt(i);
        }
        selectedInfo = null;
        addBackground();
        //清除之前添加的HiTabBottom listener.Tips: Java foreach remove问题,一边移除一边遍历会有crash错误
        Iterator<OnTabSelectedListener<JTabBottomInfo<?>>> iterator = tabSelectedChangeListeners.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() instanceof JTabBottom) {
                iterator.remove();
            }
        }
        FrameLayout ll = new FrameLayout(getContext());
        ll.setTag(TAG_TAB_BOTTOM);
        int width = JDisplayUtil.getDisplayWidthInPx(getContext()) / infoList.size();
        int height = JDisplayUtil.dp2px(tabBottomHeight, getResources());
        for (int i = 0; i < infoList.size(); i++) {
            final JTabBottomInfo<?> info = infoList.get(i);
            //Tips:为何不用LinearLayout: 当动态改变child大小后Gravity.BOTTOM会失效
            LayoutParams params = new LayoutParams(width, height);
            params.gravity = Gravity.BOTTOM;
            params.leftMargin = i * width;
            JTabBottom tabBottom = new JTabBottom(getContext());
            tabSelectedChangeListeners.add(tabBottom);
            tabBottom.setJTabInfo(info);
            ll.addView(tabBottom, params);
            tabBottom.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSelected(info);
                }
            });
        }
        LayoutParams flParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        flParams.gravity = Gravity.BOTTOM;
        addBottomLine();
        addView(ll, flParams);
        fixContentView();
    }

    public void setTabAlpha(float alpha) {
        this.bottomAlpha = alpha;
    }

    public void setTabHeight(float tabHeight) {
        this.tabBottomHeight = tabHeight;
    }

    public void setBottomLineHeight(float bottomLineHeight) {
        this.bottomLineHeight = bottomLineHeight;
    }

    public void setBottomLineColor(String bottomLineColor) {
        this.bottomLineColor = bottomLineColor;
    }

    private void addBottomLine() {
        View bottomLine = new View(getContext());
        bottomLine.setBackgroundColor(Color.parseColor(bottomLineColor));
        LayoutParams bottomLineParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, JDisplayUtil.dp2px(bottomLineHeight, getResources()));
        bottomLineParams.gravity = Gravity.BOTTOM;
        bottomLineParams.bottomMargin = JDisplayUtil.dp2px(tabBottomHeight - bottomLineHeight, getResources());
        addView(bottomLine, bottomLineParams);
        bottomLine.setAlpha(bottomAlpha);


    }

    private void onSelected(@NonNull JTabBottomInfo<?> nextInfo) {
        for (OnTabSelectedListener<JTabBottomInfo<?>> listener : tabSelectedChangeListeners) {
            listener.onTabSelectedChange(infoList.indexOf(nextInfo), selectedInfo, nextInfo);
        }
        this.selectedInfo = nextInfo;
    }

    private void addBackground() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.j_bottom_layout_bg, null);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, JDisplayUtil.dp2px(tabBottomHeight, getResources()));
        params.gravity = Gravity.BOTTOM;
        addView(view, params);
        view.setAlpha(bottomAlpha);
    }

    /**
     * 修复内容区域的底部Padding
     */
    private void fixContentView() {
        if (!(getChildAt(0) instanceof ViewGroup)) {
            return;
        }
        ViewGroup rootView = (ViewGroup) getChildAt(0);
        ViewGroup targetView = JViewUtil.findTypeView(rootView, RecyclerView.class);
        if (targetView == null) {
            targetView = JViewUtil.findTypeView(rootView, ScrollView.class);
        }
        if (targetView == null) {
            targetView = JViewUtil.findTypeView(rootView, AbsListView.class);
        }
        if(targetView!=null){
            targetView.setPadding(0,0,0,JDisplayUtil.dp2px(tabBottomHeight,getResources()));
            //setClipToPadding该属性定义了ViewGroup是否将裁剪它的子View，和根据
            //它的padding（如果padding不为0）调整任何边缘效果
            targetView.setClipToPadding(false);
        }
    }
}
