package com.luania.witchpot.widget;

import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.luania.witchpot.R;
import com.luania.witchpot.util.PropertyAnimUtil;

/**
 * Created by luania on 16/7/9.
 */
public class HigherAnimDraweeFrameLayout extends FrameLayout {

    private PropertyAnimUtil.AnimatorEndListener onHideListener;

    private SimpleDraweeView simpleDraweeView;
    private ImageView ivRemove;

    public HigherAnimDraweeFrameLayout(Context context, AttributeSet attrs) {
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
        PropertyAnimUtil.getHigherToSquare(this);
        simpleDraweeView.setImageURI(uri);
    }

    private void hide() {
        PropertyAnimUtil.getLowerToGone(this, new PropertyAnimUtil.AnimatorEndListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if(onHideListener != null){
                    onHideListener.onAnimationEnd(animation);
                }
                simpleDraweeView.setImageURI("");
            }
        });
    }

    public ImageView getIvRemove() {
        return ivRemove;
    }

    public void setOnHideListener(PropertyAnimUtil.AnimatorEndListener onHideListener) {
        this.onHideListener = onHideListener;
    }
}
