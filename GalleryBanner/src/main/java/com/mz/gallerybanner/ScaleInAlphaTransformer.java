package com.mz.gallerybanner;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * 作者：Created by MZ on 2018/4/4.
 * 邮箱：maozhou3@163.com
 * QQ：770627270
 */

public class ScaleInAlphaTransformer extends BasePageTransformer {
    private static final float DEFAULT_MIN_SCALE = 0.7f;
    private float mMinScale = DEFAULT_MIN_SCALE;

    private static final float DEFAULT_MIN_ALPHA = 0.3f;
    private float mMinAlpha = DEFAULT_MIN_ALPHA;

    public ScaleInAlphaTransformer() {
    }

    public ScaleInAlphaTransformer(float minAlpha, float minScale) {
        this(minAlpha, minScale, NonPageTransformer.INSTANCE);
    }

    public ScaleInAlphaTransformer(ViewPager.PageTransformer pageTransformer) {
        this(DEFAULT_MIN_SCALE, DEFAULT_MIN_SCALE, pageTransformer);
    }

    public ScaleInAlphaTransformer(float minScale, float minAlpha, ViewPager.PageTransformer pageTransformer) {
        mMinScale = minScale;
        mMinAlpha = minAlpha;
        mPageTransformer = pageTransformer;
    }

    @Override
    protected void pageTransform(View view, float position) {
        alpha(view, position);
        scaleIn(view, position);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void alpha(View view, float position) {
        if (position < -1) { // [-Infinity,-1)
            view.setAlpha(mMinAlpha);
        } else if (position <= 1) { // [-1,1]

            if (position < 0) //[0，-1]
            {           //[1,min]
                float factor = mMinAlpha + (1 - mMinAlpha) * (1 + position);
                view.setAlpha(factor);
            } else//[1，0]
            {
                //[min,1]
                float factor = mMinAlpha + (1 - mMinAlpha) * (1 - position);
                view.setAlpha(factor);
            }
        } else { // (1,+Infinity]
            view.setAlpha(mMinAlpha);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void scaleIn(View view, float position) {
        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();

        view.setPivotY(pageHeight / 2);
        view.setPivotX(pageWidth / 2);
        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setScaleX(mMinScale);
            view.setScaleY(mMinScale);
            view.setPivotX(pageWidth);
        } else if (position <= 1) { // [-1,1]
            // Modify the default slide transition to shrink the page as well
            if (position < 0) //1-2:1[0,-1] ;2-1:1[-1,0]
            {

                float scaleFactor = (1 + position) * (1 - mMinScale) + mMinScale;
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                view.setPivotX(pageWidth * (DEFAULT_CENTER + (DEFAULT_CENTER * -position)));

            } else //1-2:2[1,0] ;2-1:2[0,1]
            {
                float scaleFactor = (1 - position) * (1 - mMinScale) + mMinScale;
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);
                view.setPivotX(pageWidth * ((1 - position) * DEFAULT_CENTER));
            }


        } else { // (1,+Infinity]
            view.setPivotX(0);
            view.setScaleX(mMinScale);
            view.setScaleY(mMinScale);
        }
    }

}
