package com.luania.witchpot.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.luania.witchpot.R;

/**
 * Created by luania on 16/7/9.
 */
public class LargenAnimDraweeFrameLayout extends FrameLayout {

    private OnHideListener onHideListener;

    private SimpleDraweeView simpleDraweeView;
    private ImageView ivRemove;

    public LargenAnimDraweeFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSimpleDraweeView(context);
        initIvRemove(context);

        addView(simpleDraweeView);
        addView(ivRemove);
    }

    private void initSimpleDraweeView(Context context){
        simpleDraweeView = new SimpleDraweeView(context);
        simpleDraweeView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    private void initIvRemove(Context context){
        ivRemove = new ImageView(context);

        int size = (int) context.getResources().getDimension(R.dimen.icon_size_with_padding);
        LayoutParams lp = new LayoutParams(size,size);
        lp.gravity = Gravity.RIGHT;
        ivRemove.setLayoutParams(lp);

        int padding = (int) context.getResources().getDimension(R.dimen.base_padding);
        ivRemove.setPadding(padding,padding,padding,padding);

        ivRemove.setClickable(true);
        int id = android.support.design.R.drawable.abc_item_background_holo_dark;
        ivRemove.setBackground(context.getResources().getDrawable(id));
        ivRemove.setImageResource(R.drawable.ic_highlight_remove_white_48dp);
        ivRemove.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });
    }

    public void setImage(String uri) {
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
        simpleDraweeView.setImageURI(uri);
    }

    private void hide() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(getHeight(), 0);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                setLayoutParams(new LinearLayout.LayoutParams(getWidth(), value));
                if (value == 0) {
                    if(onHideListener != null){
                        onHideListener.onHide();
                        simpleDraweeView.setImageURI("");
                    }
                }
            }
        });
        valueAnimator.setDuration(500);
        valueAnimator.start();
    }

    public interface OnHideListener {
        void onHide();
    }

    public SimpleDraweeView getSimpleDraweeView() {
        return simpleDraweeView;
    }

    public ImageView getIvRemove() {
        return ivRemove;
    }

    public void setOnHideListener(OnHideListener onHideListener) {
        this.onHideListener = onHideListener;
    }
}
