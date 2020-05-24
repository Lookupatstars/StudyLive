package com.aaron.studylive.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * Created by Aaron on 2020/3/30
 * The current project is StudyLive
 *
 * @Describe: 不允许滑动 ViewPager
 */
public class NoScrollViewPager extends ViewPager {

    private int mTouchSlop;
    private boolean mScrollble = true;

    public NoScrollViewPager(Context context) {
        super(context);
//        final ViewConfiguration vc= ViewConfiguration.get(context);
//        mTouchSlop = vc.getScaledTouchSlop();
     }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
//        final ViewConfiguration vc= ViewConfiguration.get(context);
//        mTouchSlop = vc.getScaledTouchSlop();
    }

    //拦截 TouchEvent     返回false 拦截
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return false;
    }

    //处理 TouchEvent
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
//        return super.onTouchEvent(arg0);
        return false;
    }


    //因为这个执行的顺序是  父布局先得到 action_down的事件
    /**
     * onInterceptTouchEvent(MotionEvent ev)方法，这个方法只有ViewGroup类有
     * 如LinearLayout，RelativeLayout等    可以包含子View的容器的
     *
     * 用来分发 TouchEvent
     * 此方法    返回true    就交给本 View的 onTouchEvent处理
     * 此方法    返回false   就交给本View的 onInterceptTouchEvent 处理
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //让父类不拦截触摸事件就可以了。
        this.getParent().requestDisallowInterceptTouchEvent(false);
        return super.dispatchTouchEvent(ev);

    }

    /*
    可以左右滑动
    int move_x,move_y;

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        //重写此方法，触摸时什么都不做，从而实现对滑动事件的禁用

        switch (e.getAction()){

            case MotionEvent.ACTION_DOWN:
                move_x = (int) e.getX();
                move_y = (int) e.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                Log.e("motion_event","down   x==y  "+move_x+" ==== "+move_y);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("motion_event","move   x==y  "+move_x+" ==== "+move_y);
                int y = (int) e.getY();
                int x = (int) e.getX();
                if(Math.abs(y-move_y)>mTouchSlop&&Math.abs(x-move_x)<mTouchSlop*2){
                    getParent().requestDisallowInterceptTouchEvent(false);
                }else {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.e("motion_event","up   x==y  "+move_x+" ==== "+move_y);
                break;
        }

        return super.onTouchEvent(e);
    }

     */

}
