package com.aaron.studylive.view;

import android.content.Context;
import android.view.MotionEvent;

import com.aaron.studylive.views.RefreshListView;

/**
 * Created by Aaron on 2020/4/15
 * The current project is StudyLive
 *
 * @Describe:
 */
public class NoScrollRefreshListView extends RefreshListView {
    public NoScrollRefreshListView(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;
    }
}
