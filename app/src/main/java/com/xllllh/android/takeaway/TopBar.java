package com.xllllh.android.takeaway;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

/**
 * Created by mephist0 on 16-5-11.
 */
public class TopBar extends RelativeLayout {

    Button mTitleButton;
    String mTitleText;
    float mTitleTextSize;
    int mTitleTextColor;
    Drawable mTitleBackground;
    LayoutParams mTitleParams;

    Button mMenuButton;
    String mMenuText;
    float mMenuTextSize;
    int mMenuTextColor;
    Drawable mMenuBackground;
    LayoutParams mMenuParams;

    public TopBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TopBar);

        //get attrs of title
        mTitleText = typedArray.getString(R.styleable.TopBar_titleText);
        mTitleTextSize = typedArray.getDimension(R.styleable.TopBar_titleTextSize, 10);
        mTitleTextColor = typedArray.getColor(R.styleable.TopBar_titleTextColor, 0);
        mTitleBackground = typedArray.getDrawable(R.styleable.TopBar_titleBackground);

        //get attrs of menu
        mMenuText = typedArray.getString(R.styleable.TopBar_menuText);
        mMenuTextSize = typedArray.getDimension(R.styleable.TopBar_menuTextSize, 10);
        mMenuTextColor = typedArray.getColor(R.styleable.TopBar_menuTextColor, 0);
        mMenuBackground = typedArray.getDrawable(R.styleable.TopBar_menuBackground);

        //Should call recycle method after using
        typedArray.recycle();

        mTitleButton = new Button(context);
        mMenuButton = new Button(context);

        //set attrs of title
        mTitleButton.setText(mTitleText);
        mTitleButton.setTextColor(mTitleTextColor);
        mTitleButton.setTextSize(mTitleTextSize);
        mTitleButton.setBackground(mTitleBackground);

        //add view
        mTitleParams = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mTitleParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
        addView(mTitleButton,mTitleParams);

        //set attrs of menu
        mMenuButton.setText(mMenuText);
        mMenuButton.setTextColor(mMenuTextColor);
        mMenuButton.setTextSize(mMenuTextSize);
        mMenuButton.setBackground(mMenuBackground);

        mMenuParams = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mMenuParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
        addView(mMenuButton,mMenuParams);
    }

    public interface topbarClickListener{
        void titleClick();
        void memueClick();
    }
}
