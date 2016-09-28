package com.fly.pulg;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.ColorInt;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fly.activity.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TSJ on 2016/9/13.
 */

public class FlyAnimatorLayout extends LinearLayout {
    private final static String TAG = "FlyAnimatorLayout";

    /**
     * child view is TextView
     */
    public static final int CHILD_TEXT = 0;
    /**
     * child view is ImageView
     */
    public static final int CHILD_IMAGE = 1;
    /**
     * child view is Button
     */
    public static final int CHILD_BUTTON = 2;
    /**
     * flyDirection left to right
     */
    public static final int LEFT_TO_RIGHT = 0;
    /**
     * flyDirection right to left
     */
    public static final int RIGHT_TO_LEFT = 1;
    /**
     * flyDirection top to bottom
     */
    public static final int TOP_TO_BOTTOM = 2;
    /**
     * flyDirection bottom to top
     */
    public static final int BOTTOM_TO_TOP = 3;
    /**
     * default duration
     */
    public static final int DEFUALT_DURATION = 5000;
    /**
     * default view num
     */
    public static final int DEFUALT_VIEW_NUM = 1;


    /**
     * 子元素类型
     */
    private int mChildView = CHILD_TEXT;
    /**
     * 飞行方向
     */
    private int mFlyDirection = LEFT_TO_RIGHT;
    /**
     * 飞行速度
     */
    private int mDuration = DEFUALT_DURATION;
    /**
     * 设置的单个类型控件飞行元素的总量
     */
    private int mOneViewNum = DEFUALT_VIEW_NUM;
    /**
     * 子元素数量
     */
    private int mChildCount = 0;
    /**
     * 布局类型
     */
    private int mOrientation;
    /**
     * 子元素文字内容数组
     */
    private String[] mTextContent;
    /**
     * 是否开始动画
     */
    private boolean isStartAnimator = false;
    /**
     * 已经飞行的顺序号
     */
    private List<Integer> mNowMoveNum = new ArrayList<>();
    /**
     * 设置文字颜色,默认颜色黑色
     */
    private ColorStateList mTextColor = ColorStateList.valueOf(Color.BLACK);
    /**
     * 设置文字大小
     */
    private float mTextSize = 18;
    /**
     * 图片uri
     */
    private Uri[] mImgUri = null;
    /**
     * 图片资源
     */
    private int[] mImgResource = null;


    public FlyAnimatorLayout(Context context) {
        super(context);
    }

    public FlyAnimatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray styled = getContext().obtainStyledAttributes(attrs, R.styleable.FlyAnimatorLayout);//获取属性文件
        mDuration = styled.getInt(R.styleable.FlyAnimatorLayout_duration, DEFUALT_DURATION);
        mOneViewNum = styled.getInt(R.styleable.FlyAnimatorLayout_viewNum, DEFUALT_DURATION);
        mFlyDirection = styled.getInt(R.styleable.FlyAnimatorLayout_flyDirection, LEFT_TO_RIGHT);
        mChildView = styled.getInt(R.styleable.FlyAnimatorLayout_childView, CHILD_TEXT);
        styled.recycle();
    }

    public FlyAnimatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray styled = getContext().obtainStyledAttributes(attrs, R.styleable.FlyAnimatorLayout);//获取属性文件
        mDuration = styled.getInt(R.styleable.FlyAnimatorLayout_duration, DEFUALT_DURATION);
        mOneViewNum = styled.getInt(R.styleable.FlyAnimatorLayout_viewNum, DEFUALT_DURATION);
        mFlyDirection = styled.getInt(R.styleable.FlyAnimatorLayout_flyDirection, LEFT_TO_RIGHT);
        mChildView = styled.getInt(R.styleable.FlyAnimatorLayout_childView, CHILD_TEXT);
        styled.recycle();
    }

    /**
     * 组件初始化
     */
    public void initView(){
        if(mFlyDirection == LEFT_TO_RIGHT || mFlyDirection == RIGHT_TO_LEFT){
            setOrientation(VERTICAL);
            setGravity(Gravity.CENTER_VERTICAL);
            mOrientation = VERTICAL;
        }else {
            setOrientation(HORIZONTAL);
            setGravity(Gravity.CENTER_HORIZONTAL);
            mOrientation = HORIZONTAL;
        }
        Log.e(TAG,"mOrientation:" + mOrientation);
        switch (mChildView){
            case CHILD_TEXT:
                addTextView();
                break;
            case CHILD_IMAGE:
                addImageView();
                break;
            case CHILD_BUTTON:

                break;
        }

    }

    /**
     * add TextView
     */
    private void addTextView(){
        //循环添加子元素
        for (int i = 0; i < mOneViewNum; i++) {
            TextView textView = new TextView(getContext());
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            if(mOrientation == HORIZONTAL && (mFlyDirection == TOP_TO_BOTTOM || mFlyDirection == BOTTOM_TO_TOP)){
//                textView.setRotation(90);
                textView.setEms(1);
            }
            textView.setTextColor(mTextColor);
            textView.setTextSize(mTextSize);
            if(mTextContent != null && i < mTextContent.length){
                textView.setText(mTextContent[i]);
            }
            addView(textView);
            textView.setVisibility(INVISIBLE);
        }
    }

    /**
     * add ImageView
     */
    private void addImageView(){
        //循环添加子元素
        for (int i = 0; i < mOneViewNum; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

            if(mImgResource != null && i < mImgResource.length){
                imageView.setImageResource(mImgResource[i]);
            }
            if(mImgUri != null && i < mImgUri.length){
                imageView.setImageURI(mImgUri[i]);
            }
            addView(imageView);
            imageView.setVisibility(INVISIBLE);
        }
    }

    /**
     * 设置动画
     */
    private void setAnimator(){
        Point point = getScreenMetrics(getContext());
        AnimatorSet mAnimatorSet = new AnimatorSet();
        switch (mChildView){
            case CHILD_TEXT:
                getNowFlyNum();
                TextView textView = (TextView) getChildAt(mNowMoveNum.get(mChildCount));
                textView.setVisibility(VISIBLE);
                TextPaint textPaint = textView.getPaint();
                float txtLength = textPaint.measureText(textView.getText().toString().trim());
//                Log.e(TAG,"txtLength:" + txtLength);

                ObjectAnimator objectAnimator = null;
                switch (mFlyDirection){
                    case LEFT_TO_RIGHT:
                        objectAnimator = ObjectAnimator.ofFloat(textView, "translationX", -txtLength, point.x + txtLength / 2);
                        break;
                    case RIGHT_TO_LEFT:
                        objectAnimator = ObjectAnimator.ofFloat(textView, "translationX", point.x + txtLength / 2, -txtLength);
                        break;
                    case TOP_TO_BOTTOM:
                        objectAnimator = ObjectAnimator.ofFloat(textView, "translationY", -(2 * txtLength), point.y + txtLength / 2);
                        break;
                    case BOTTOM_TO_TOP:
                        objectAnimator = ObjectAnimator.ofFloat(textView, "translationY", point.y + txtLength / 2, -(2 * txtLength));
                        break;
                }
                if(objectAnimator != null) {
                    objectAnimator.setRepeatMode(ObjectAnimator.RESTART);
                    objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
                    mAnimatorSet.play(objectAnimator);
                    mAnimatorSet.setDuration(mDuration).start();
                }
                handler.sendEmptyMessageDelayed(0,mDuration/10);
                break;
            case CHILD_IMAGE:

                break;
            case CHILD_BUTTON:

                break;
        }

    }

    /**
     * 启动动画
     */
    public void startAnimator(){
        setAnimator();
        isStartAnimator = true;
    }

    public void stopAnimator(){
        isStartAnimator = false;
    }

    /**
     * 获取当前飞行顺序号(随机)
     */
    private void getNowFlyNum(){
        int tempNum = (int)(Math.random() * mOneViewNum);
        boolean tempBool = false;
        for (Integer integer : mNowMoveNum) {
            if(integer == tempNum){
                tempBool = true;
                break;
            }
        }
        if(tempBool){
            if(mNowMoveNum.size() == mOneViewNum){
                return;
            }
            getNowFlyNum();
        }else {
            mNowMoveNum.add(tempNum);
            Log.e(TAG,"mNowMoveNum:" + mNowMoveNum);
        }
    }


    public int getOneViewNum() {
        return mOneViewNum;
    }

    public void setOneViewNum(int mOneViewNum) {
        this.mOneViewNum = mOneViewNum;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int mDuration) {
        this.mDuration = mDuration;
    }

    public int getFlyDirection() {
        return mFlyDirection;
    }

    public void setFlyDirection(int mFlyDirection) {
        this.mFlyDirection = mFlyDirection;
    }

    public String[] getTextContent() {
        return mTextContent;
    }

    public void setTextContent(String[] mTextContent) {
        this.mTextContent = mTextContent;
    }

    public boolean isStartAnimator() {
        return isStartAnimator;
    }

    public void setTextColor(@ColorInt int color) {
        mTextColor = ColorStateList.valueOf(color);
    }

    public void setTextColor(ColorStateList colors) {
        if (colors == null) {
            throw new NullPointerException();
        }
        mTextColor = colors;
    }

    public void setTextSize(float size) {
        this.mTextSize = size;
    }

    public void setImgUri(Uri[] imgUri){
        mImgResource = null;
        this.mImgUri = imgUri;
    }

    public void setImgResource(int[] imgResource){
        mImgUri = null;
        this.mImgResource = imgResource;
    }

    /**
     * 获取屏幕宽度和高度，单位为px
     * @param context
     * @return
     */
    private Point getScreenMetrics(Context context){
        DisplayMetrics dm =context.getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;
        int h_screen = dm.heightPixels;
        return new Point(w_screen, h_screen);

    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    mChildCount++;
                    if(mChildCount < mOneViewNum) {
                        setAnimator();
                    }
                    break;
                default:
                    Log.e(TAG,"no found what:" + msg.what);
                    break;
            }
        }
    };
}
