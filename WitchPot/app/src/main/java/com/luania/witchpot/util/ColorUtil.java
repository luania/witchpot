package com.luania.witchpot.util;

import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;
import android.widget.ImageView;

/**
 * Created by luania on 16/7/14.
 */
public class ColorUtil {
    public static void tintImageView(Bitmap bitmap, final ImageView imageView) {
        Palette.generateAsync(bitmap,
                new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        Palette.Swatch vibrant = palette.getVibrantSwatch();
                        if(vibrant == null){
                            vibrant = palette.getMutedSwatch();
                        }
                        if (vibrant != null) {
                            imageView.setColorFilter(vibrant.getBodyTextColor());
                        }
                    }
                });
    }
}
