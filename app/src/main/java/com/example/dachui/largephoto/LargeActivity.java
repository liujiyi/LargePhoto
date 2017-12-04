package com.example.dachui.largephoto;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>描述：</p>
 * 作者： liujiyi<br>
 * 日期： 2017/11/30<br>
 * 版本： v2.2.1<br>
 */
public class LargeActivity extends Activity {
    public static final int DURATION = 300;
    private static final LinearInterpolator DEFAULT_INTERPOLATOR = new LinearInterpolator();
    private ViewPager mViewPager;
    private List<View> listViews = null;
    private int[] imgs = {R.mipmap.anim1, R.mipmap.anim2, R.mipmap.anim3,
            R.mipmap.anim4, R.mipmap.anim5, R.mipmap.anim6,};
    private int index = 0;
    private ViewPagerAdapter adapter;
    private LinearLayout mLayout;

    /**
     * 屏幕宽度和高度
     */
    private int mScreenWidth;
    private int mScreenHeight;
    /**
     * 上一个界面图片的宽度和高度
     */
    private int mOriginWidth;
    private int mOriginHeight;

    /**
     * 上一个界面图片的位置信息
     */
    private Rect mRect;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_large);

        mLayout = findViewById(R.id.layout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        // 获得屏幕尺寸
        getScreenSize();
        initView();
    }

    @Override
    public void onBackPressed() {
        // 使用退场动画
        runExitAnim();
    }

    private void initView() {
        // 获取上一个界面传入的信息
        mRect = getIntent().getSourceBounds();

        // 获取上一个界面中，图片的宽度和高度
        mOriginWidth = mRect.right - mRect.left;
        mOriginHeight = mRect.bottom - mRect.top;

        // 设置 ImageView 的位置，使其和上一个界面中图片的位置重合
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(mOriginWidth, mOriginHeight);
        params.setMargins(mRect.left, mRect.top - getStatusBarHeight(), mRect.right, mRect.bottom);
        mLayout.setLayoutParams(params);

        initData();
    }

    private void initData() {
        listViews = new ArrayList<View>();
        for (int i = 0; i < imgs.length; i++) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(
                    R.layout.item_viewpager, null);
            ImageView iv = (ImageView) view.findViewById(R.id.img);
            iv.setBackgroundResource(imgs[i]);
            listViews.add(view);
        }

        adapter = new ViewPagerAdapter(listViews);
        mViewPager.setAdapter(adapter);
        mViewPager.setOnPageChangeListener(new PageChangeListener());

        // 设置入场动画
        runEnterAnim();
    }

    /**
     * 模拟入场动画
     */
    private void runEnterAnim() {
        ValueAnimator widthAnimator = ValueAnimator.ofInt(mOriginWidth, mScreenWidth);
        widthAnimator.setDuration(DURATION);
        widthAnimator.setInterpolator(DEFAULT_INTERPOLATOR);
        widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {

                int currentValue = (Integer) animator.getAnimatedValue();
                // 获得每次变化后的属性值
                Log.d("===width===", currentValue+"");
                // 输出每次变化后的属性值进行查看

                ViewGroup.LayoutParams layoutParams = mLayout.getLayoutParams();
                layoutParams.width = currentValue;
                mLayout.setLayoutParams(layoutParams);
            }
        });
        ValueAnimator heightAnimator = ValueAnimator.ofInt(mOriginHeight, mScreenHeight);
        heightAnimator.setDuration(DURATION);
        heightAnimator.setInterpolator(DEFAULT_INTERPOLATOR);
        heightAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {

                int currentValue = (Integer) animator.getAnimatedValue();
                // 获得每次变化后的属性值
                Log.d("===height===", currentValue+"");
                // 输出每次变化后的属性值进行查看

                ViewGroup.LayoutParams layoutParams = mLayout.getLayoutParams();
                layoutParams.height = currentValue;
                mLayout.setLayoutParams(layoutParams);
            }
        });
        ValueAnimator leftAnimator = ValueAnimator.ofInt(mRect.left, 0);
        leftAnimator.setDuration(DURATION);
        leftAnimator.setInterpolator(DEFAULT_INTERPOLATOR);
        leftAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {

                int currentValue = (Integer) animator.getAnimatedValue();
                // 获得每次变化后的属性值
                Log.d("===left===", currentValue+"");
                // 输出每次变化后的属性值进行查看

                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mLayout.getLayoutParams();
                layoutParams.leftMargin = currentValue;
                mLayout.setLayoutParams(layoutParams);
            }
        });
        ValueAnimator topAnimator = ValueAnimator.ofInt(mRect.top, 0);
        topAnimator.setDuration(DURATION);
        topAnimator.setInterpolator(DEFAULT_INTERPOLATOR);
        topAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {

                int currentValue = (Integer) animator.getAnimatedValue();
                // 获得每次变化后的属性值
                Log.d("===top===", currentValue+"");
                // 输出每次变化后的属性值进行查看
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mLayout.getLayoutParams();
                layoutParams.topMargin = currentValue;
                mLayout.setLayoutParams(layoutParams);
            }
        });

        AnimatorSet animSet = new AnimatorSet();
        animSet.play(widthAnimator).with(heightAnimator).with(leftAnimator).with(topAnimator);
        animSet.start();
    }

    /**
     * 模拟退场动画
     */
    private void runExitAnim() {
        ValueAnimator widthAnimator = ValueAnimator.ofInt(mScreenWidth, mOriginWidth);
        widthAnimator.setDuration(DURATION);
        widthAnimator.setInterpolator(DEFAULT_INTERPOLATOR);
        widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {

                int currentValue = (Integer) animator.getAnimatedValue();
                // 获得每次变化后的属性值
                Log.d("===width===", currentValue+"");
                // 输出每次变化后的属性值进行查看

                ViewGroup.LayoutParams layoutParams = mLayout.getLayoutParams();
                layoutParams.width = currentValue;
                mLayout.setLayoutParams(layoutParams);
            }
        });
        ValueAnimator heightAnimator = ValueAnimator.ofInt(mScreenHeight, mOriginHeight);
        heightAnimator.setDuration(DURATION);
        heightAnimator.setInterpolator(DEFAULT_INTERPOLATOR);
        heightAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {

                int currentValue = (Integer) animator.getAnimatedValue();
                // 获得每次变化后的属性值
                Log.d("===height===", currentValue+"");
                // 输出每次变化后的属性值进行查看

                ViewGroup.LayoutParams layoutParams = mLayout.getLayoutParams();
                layoutParams.height = currentValue;
                mLayout.setLayoutParams(layoutParams);
            }
        });
        ValueAnimator leftAnimator = ValueAnimator.ofInt(0, mRect.left);
        leftAnimator.setDuration(DURATION);
        leftAnimator.setInterpolator(DEFAULT_INTERPOLATOR);
        leftAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {

                int currentValue = (Integer) animator.getAnimatedValue();
                // 获得每次变化后的属性值
                Log.d("===left===", currentValue+"");
                // 输出每次变化后的属性值进行查看

                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mLayout.getLayoutParams();
                layoutParams.leftMargin = currentValue;
                mLayout.setLayoutParams(layoutParams);
            }
        });
        ValueAnimator topAnimator = ValueAnimator.ofInt(0, mRect.top);
        topAnimator.setDuration(DURATION);
        topAnimator.setInterpolator(DEFAULT_INTERPOLATOR);
        topAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {

                int currentValue = (Integer) animator.getAnimatedValue();
                // 获得每次变化后的属性值
                Log.d("===top===", currentValue+"");
                if (currentValue >= getStatusBarHeight()) {
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

                }
                // 输出每次变化后的属性值进行查看
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mLayout.getLayoutParams();
                layoutParams.topMargin = currentValue;
                mLayout.setLayoutParams(layoutParams);
            }
        });

        AnimatorSet animSet = new AnimatorSet();
        animSet.play(widthAnimator).with(heightAnimator).with(leftAnimator).with(topAnimator);
        animSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                finish();
                overridePendingTransition(0, 0);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animSet.start();
    }
    
    /**
     * 获取屏幕尺寸
     */
    private void getScreenSize() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        mScreenWidth = size.x;
        mScreenHeight = size.y;
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    private int getStatusBarHeight() {
        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            return getResources().getDimensionPixelSize(resourceId);
        }
        return -1;
    }

    private class PageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int arg0) {
            Toast.makeText(getApplicationContext(), arg0 + "", Toast.LENGTH_SHORT).show();
            index = arg0;
        }
    }
}