package com.luania.witchpot.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by luania on 16/7/9.
 */
public class LargenAnimDraweeView extends SimpleDraweeView {
    public LargenAnimDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setImageURIWithAnim(String uri){
        if(!"noImage".equals(uri)){
            show();
        }else{
            hide();
        }
        setImageURI(uri);
    }

    private void show(){
        ValueAnimator valueAnimator = ValueAnimator.ofInt(getHeight(), getWidth());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                setLayoutParams(new LinearLayout.LayoutParams(getWidth(), value));
            }
        });
        valueAnimator.setDuration(500);
        valueAnimator.start();
    }

    private void hide(){
        setLayoutParams(new LinearLayout.LayoutParams(getWidth(), 0));
    }
}
