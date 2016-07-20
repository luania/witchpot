package com.luania.witchpot.util;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by luania on 16/7/20.
 */
public class PropertyAnimUtil {

    public static void getHigherToSquare(final View view) {
        getHigherToSquare(view, null);
    }

    public static void getHigherToSquare(final View view, AnimatorEndListener animatorEndListener) {
        changeHeight(view, view.getWidth(), animatorEndListener);
    }

    public static void getLowerToGone(final View view, AnimatorEndListener animatorEndListener) {
        changeHeight(view, 0, animatorEndListener);
    }

    public static void changeHeight(final View view, int toValue, final AnimatorEndListener animatorEndListener) {
        baseValueAnimator(view.getHeight(), toValue, new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams lp = view.getLayoutParams();
                lp.height = value;
                view.setLayoutParams(lp);
            }
        },animatorEndListener);
    }

    public static void baseValueAnimator(int fromValue, int toValue, final ValueAnimator.AnimatorUpdateListener animatorUpdateListener, final AnimatorEndListener animatorEndListener){
        ValueAnimator valueAnimator = ValueAnimator.ofInt(fromValue, toValue);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        if(animatorUpdateListener != null){
            valueAnimator.addUpdateListener(animatorUpdateListener);
        }
        if(animatorEndListener != null){
            valueAnimator.addListener(animatorEndListener);
        }
        valueAnimator.setDuration(500);
        valueAnimator.start();
    }

    public abstract static class AnimatorEndListener implements Animator.AnimatorListener {
        @Override
        public void onAnimationRepeat(Animator animation) {

        }

        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }
    }

}
