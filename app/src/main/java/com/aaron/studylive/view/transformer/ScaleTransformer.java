package com.aaron.studylive.view.transformer;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

//启动页动画的编写
public class ScaleTransformer implements ViewPager.PageTransformer {

    //缩小动画的值
    private static final float MIN_SCALE = 0.75f;
    //透明度的变化
    private static final float MIN_ALPHA = 0.5f;

    //滑动画面的时候，通过position的值来看计算出滑动的动画
    @Override
    public void transformPage(@NonNull View page, float position) {
        // a->b : a : position ,(0,-1)  b: position ,(1,0)
        // b->a : a: position ,(-1,0) b: position ,(0,1)
        if(position < -1){
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);
            page.setAlpha(MIN_ALPHA);
        }else if(position <= 1){
            //左边的界面
            if(position < 0){
                //[1,0.75] a->b : a : position ,(0,-1)
                float scaleA = MIN_SCALE+(1-MIN_SCALE)*(1+position);
                page.setScaleX(scaleA);
                page.setScaleY(scaleA);
                // [0.75,1]  b->a : a: position ,(-1,0)

                //透明度[1,0.5]
                float alphaA = MIN_ALPHA + (1 - MIN_ALPHA) * (1 + position);
                page.setAlpha(alphaA);

            }else{ //the right page
                // a->b : b: position ,(1,0)  [0.75,1]
                float scaleB = MIN_SCALE + (1-MIN_SCALE)*(1-position);
                page.setScaleX(scaleB);
                page.setScaleY(scaleB);
                // b->a : b: position ,(0,1)  [1,0.75]

                //透明度[0.5,1]
                float alphaB = MIN_ALPHA + (1 - MIN_ALPHA) * (1 - position);
                page.setAlpha(alphaB);
            }
        }else {
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);
            page.setAlpha(MIN_ALPHA);
        }

    }


}
