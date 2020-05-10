package com.aaron.studylive.view;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Aaron on 2020/4/15
 * The current project is StudyLive
 *
 * @Describe: 不允许滑动的RecyclerView
 */
public class NoScrollRecyclerView extends RecyclerView {
    public NoScrollRecyclerView( Context context) {
        super(context);
    }

    public NoScrollRecyclerView( Context context,  AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollRecyclerView( Context context,  AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return true;
    }
}
