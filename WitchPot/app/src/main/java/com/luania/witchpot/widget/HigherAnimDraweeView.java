package com.luania.witchpot.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.luania.witchpot.util.PropertyAnimUtil;

/**
 * Created by luania on 16/7/9.
 */
public class HigherAnimDraweeView extends SimpleDraweeView {
    public HigherAnimDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setImage(String uri){
        if(!"noImage".equals(uri)){
            show();
        }else{
            hide();
        }
        setImageURI(uri);
    }

    private void show(){
        PropertyAnimUtil.getHigherToSquare(this);
    }

    private void hide(){
        setLayoutParams(new LinearLayout.LayoutParams(getWidth(), 0));
    }
}
