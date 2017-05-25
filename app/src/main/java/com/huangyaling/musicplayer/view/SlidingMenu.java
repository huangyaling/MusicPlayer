package com.huangyaling.musicplayer.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.huangyaling.musicplayer.R;
import com.huangyaling.musicplayer.utils.ScreenUtils;

/**
 * Created by huangyaling on 2017/5/25.
 */
public class SlidingMenu extends HorizontalScrollView {
    private int mScreenWidth;//屏幕宽度
    private int mMenuRightPadding = 50;//dp
    private int mMenuWidth;//菜单宽度
    private int mHalfMenuWidth;
    private boolean once;
    private boolean isOpen;
    public SlidingMenu(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(!once){
            LinearLayout wrapper = (LinearLayout) getChildAt(0);
            ViewGroup menu = (ViewGroup) wrapper.getChildAt(0);
            ViewGroup content = (ViewGroup) wrapper.getChildAt(1);
            //dp to px
            mMenuRightPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,mMenuRightPadding,content.getResources().getDisplayMetrics());
            mMenuWidth = mScreenWidth - mMenuRightPadding;
            mHalfMenuWidth = mMenuWidth/2;
            menu.getLayoutParams().width = mScreenWidth;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(changed){
            //hide menu
            this.scrollTo(mMenuWidth,0);
            once = true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                if(scrollX>mHalfMenuWidth){
                    this.smoothScrollTo(mMenuWidth,0);
                    isOpen = false;
                }else{
                    this.smoothScrollTo(0,0);
                    isOpen = true;
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }

    //open menu
    public void openMenu(){
        if(isOpen){
            return;
        }
        this.smoothScrollTo(0,0);
        isOpen = true;
    }

    //close menu
    public void closeMenu(){
        if(isOpen){
            this.smoothScrollTo(mMenuWidth,0);
            isOpen = false;
        }
    }

    //change menu
    public void toggle(){
        if(isOpen){
            closeMenu();
        }else{
            openMenu();
        }
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScreenWidth = ScreenUtils.getScreenWidth(context);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs,R.styleable.SlidingMenu,defStyleAttr,0);
        int n = array.getIndexCount();
        for(int i = 0;i<n;i++){
            int attr = array.getIndex(i);
            switch (attr){
                case R.styleable.SlidingMenu_rightPadding:
                    mMenuRightPadding = array.getDimensionPixelSize(attr,(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,50f,getResources().getDisplayMetrics()));
                    break;
            }
        }
        array.recycle();
       // TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SlidingMenu);
        //float rightPadding = array.getDimension(R.styleable.SlidingMeny_righePadding,dip2px(50));
    }

}
