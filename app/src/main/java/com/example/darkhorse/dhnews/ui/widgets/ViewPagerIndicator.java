package com.example.darkhorse.dhnews.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.darkhorse.dhnews.R;

import java.util.List;

/**
 * Created by DarkHorse on 2017/8/22.
 */

public class ViewPagerIndicator extends LinearLayout {

    private int mTabVisibleCount;       //可见Tab数量
    private int mTitleSize;             //标题字体大小
    private int mTitleColorNormal;      //标题正常颜色
    private int mTitleColorSelected;    //标题选中颜色

    private static final int DEFAULT_TAB_VISIBLE_COUNT = 4;     //默认可见Tab数量
    private static final int DEFAULT_TITLE_SIZE = 16;           //默认字体大小
    private static final int DEFAULT_COLOR_TEXT_NORMAL = 0x772E2E2E;    //默认标题正常颜色
    private static final int DEFAULT_COLOR_TEXT_SELECTED = 0x77EE0000; //默认标题选中颜色

    private static final int NONE = 0;
    private static final int TRIANGLE = 1;
    private static final int LINE = 2;
    private static final int BACKGROUND = 3;

    private int mPointerType;

    private Paint mPaint;   //画笔
    private Path mPath;     //路径

    private int mPointerWidth;     //指示器宽度
    private int mPointerHeight;    //指示器高度

    private static final float RADIO_TRIANGLE_WIDTH = 1 / 6F;
    private static final float RADIO_LINT_WIDTH = 1F;

    private int mInitTranslationX;      //指示器初始偏移量
    private int mTranslationX;      //指示器偏移量
    private int mPointerColor;      //指示器颜色

    private static final int DEAFULT_POINTER_COLOR = 0x77EE0000;      //默认指示器颜色

    public List<String> mTitles;    //标题组
    private ViewPager mViewPager;

    public ViewPagerIndicator(Context context) {
        this(context, null);
    }

    public ViewPagerIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator);

        //获取可见Tab的数量
        mTabVisibleCount = typeArray.getInt(R.styleable.ViewPagerIndicator_visible_tab_count, DEFAULT_TAB_VISIBLE_COUNT);

        //获取标题字体大小
        mTitleSize = typeArray.getInt(R.styleable.ViewPagerIndicator_title_size, DEFAULT_TITLE_SIZE);

        //获取可见Tab的默认颜色和被选中时的颜色
        mTitleColorNormal = typeArray.getInt(R.styleable.ViewPagerIndicator_title_color_normal, DEFAULT_COLOR_TEXT_NORMAL);
        mTitleColorSelected = typeArray.getInt(R.styleable.ViewPagerIndicator_title_color_selected, DEFAULT_COLOR_TEXT_SELECTED);

        //获取指示器类型
        mPointerType = typeArray.getInt(R.styleable.ViewPagerIndicator_pointer_type, NONE);

        //获取指示器颜色
        mPointerColor = typeArray.getInt(R.styleable.ViewPagerIndicator_pointer_color, DEAFULT_POINTER_COLOR);

        //获取指示器度
        mPointerWidth = typeArray.getInt(R.styleable.ViewPagerIndicator_pointer_width, 0);

        typeArray.recycle();

//        //初始化画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);        //是否光滑，抗锯齿
        mPaint.setColor(mPointerColor);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setPathEffect(new CornerPathEffect(3));   //设置画笔的路径效果
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (mPointerType != 0) {
            canvas.save();

            canvas.translate(mInitTranslationX + mTranslationX, getHeight());
            canvas.drawPath(mPath, mPaint);

            canvas.restore();
        }

        super.dispatchDraw(canvas);
    }

    /**
     * 控件宽高发生变化的时候调用该方法
     *
     * @param w    控件宽度
     * @param h    控件高度
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        switch (mPointerType) {
            case TRIANGLE:
                if (mPointerWidth == 0) {
                    mPointerWidth = (int) (w / mTabVisibleCount * RADIO_TRIANGLE_WIDTH);
                }
                mPointerHeight = mPointerWidth / 2;
                break;
            case LINE:
                if (mPointerWidth == 0) {
                    mPointerWidth = (int) (w / mTabVisibleCount * RADIO_LINT_WIDTH);
                }
                mPointerHeight = mPointerWidth / 24;
                break;
            case BACKGROUND:
                if (mPointerWidth == 0) {
                    mPointerWidth =  w / mTabVisibleCount;
                }
                mPointerHeight = h;
                break;
            default:
        }

        mInitTranslationX = w / mTabVisibleCount / 2 - mPointerWidth / 2;
        initPointer();
    }

    /**
     * 绘制指示器
     */
    private void initPointer() {

        switch (mPointerType) {
            case TRIANGLE:
                mPath = new Path();
                mPath.moveTo(0, 0);
                mPath.lineTo(mPointerWidth, 0);
                mPath.lineTo(mPointerWidth / 2, -mPointerHeight);
                mPath.close();
                break;
            case LINE:
                mPath = new Path();
                mPath.moveTo(0, 0);
                mPath.lineTo(mPointerWidth, 0);
                mPath.lineTo(mPointerWidth, -mPointerHeight);
                mPath.lineTo(0, -mPointerHeight);
                mPath.close();
                break;
            case BACKGROUND:
                mPath = new Path();
                mPath.moveTo(0, 0);
                mPath.lineTo(mPointerWidth, 0);
                mPath.lineTo(mPointerWidth, -mPointerHeight);
                mPath.lineTo(0, -mPointerHeight);
                mPath.close();
                break;
            default:
        }
    }

    /**
     * 指示器跟随Tab位置进行移动
     *
     * @param position
     * @param positionOffset
     */
    public void pageScroll(int position, float positionOffset) {
        int tabWidth = getWidth() / mTabVisibleCount;
        mTranslationX = (int) (tabWidth * (positionOffset + position));

        //容器移动，在tab处于移动至最后一个时
        if (position >= (mTabVisibleCount - 1) && positionOffset > 0 && getChildCount() > mTabVisibleCount) {
            this.scrollTo((int) ((position - (mTabVisibleCount - 1)) * tabWidth + tabWidth * positionOffset), 0);
        }

        invalidate();
    }

    /**
     * 指示器跟随ViewPager位置改变选定的Tab
     *
     * @param position
     */
    public void pageChange(int position) {
        for (int i = 0; i < mTitles.size(); i++) {
            ((TextView) getChildAt(i)).setTextColor(mTitleColorNormal);
        }
        ((TextView) getChildAt(position)).setTextColor(mTitleColorSelected);
    }

    /**
     * 添加Tab标签
     *
     * @param titles
     */
    public void setTabItemTtiles(List<String> titles) {
        if (titles != null && titles.size() > 0) {
            this.removeAllViews();
            mTitles = titles;
            for (String title : mTitles) {
                this.addView(generateTextView(title));
            }
        }
    }

    /**
     * 根据title创建tab数量
     *
     * @param title
     * @return
     */

    private View generateTextView(String title) {
        TextView tv = new TextView(getContext());
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.MATCH_PARENT);
        lp.width = getScreenWidth() / mTabVisibleCount;
        tv.setText(title);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTitleSize);
        tv.setTextColor(mTitleColorNormal);
        tv.setLayoutParams(lp);
        return tv;
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public int getScreenWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 绑定关联的ViewPager
     *
     * @param mViewPager
     */
    public void setViewPager(ViewPager mViewPager) {
        this.mViewPager = mViewPager;

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                pageScroll(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                pageChange(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setItemClickEvent();
        mViewPager.setCurrentItem(0);
        pageChange(0);
    }

    /**
     * 设置Tab的点击事件
     */
    private void setItemClickEvent() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final int j = i;
            View view = getChildAt(i);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(j);
                }
            });
        }
    }
}
