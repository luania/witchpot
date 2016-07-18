package com.luania.witchpot.widget;

import android.content.Context;
import android.support.annotation.MenuRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;

/**
 * Created by luania on 16/7/15.
 */
public class MenuToolbar extends Toolbar {
    public MenuToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setData(ToolbarData toolbarData) {
        setTitle(toolbarData.getTitle());
        if (toolbarData.getMenu() != 0) {
            inflateMenu(toolbarData.getMenu());
            setOnMenuItemClickListener(toolbarData.getOnMenuItemClickListener());
        }
    }

    public static class ToolbarData {
        private
        @StringRes
        int title;

        private
        @MenuRes
        int menu;

        private OnMenuItemClickListener onMenuItemClickListener;

        public ToolbarData(@StringRes int title, @MenuRes int menu, OnMenuItemClickListener onMenuItemClickListener) {
            this.title = title;
            this.menu = menu;
            this.onMenuItemClickListener = onMenuItemClickListener;
        }

        public ToolbarData(int title) {
            this(title, 0, null);
        }

        public int getTitle() {
            return title;
        }

        public void setTitle(int title) {
            this.title = title;
        }

        public int getMenu() {
            return menu;
        }

        public void setMenu(int menu) {
            this.menu = menu;
        }

        public OnMenuItemClickListener getOnMenuItemClickListener() {
            return onMenuItemClickListener;
        }

        public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
            this.onMenuItemClickListener = onMenuItemClickListener;
        }
    }
}
