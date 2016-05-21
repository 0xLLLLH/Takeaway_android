package com.xllllh.android.takeaway;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

/**
 * Created by 0xLLLLH on 16-5-11.
 *
 */
public class TopBar extends RelativeLayout {

    Button mTitleButton;
    Drawable mTitleIcon;
    String mTitleText;
    float mTitleTextSize;
    int mTitleTextColor;
    Drawable mTitleBackground;
    LayoutParams mTitleParams;

    Button mMenuButton;
    Drawable mMenuIcon;
    String mMenuText;
    float mMenuTextSize;
    int mMenuTextColor;
    Drawable mMenuBackground;
    LayoutParams mMenuParams;
    topbarClickListener mListener;

    boolean mShowMenu;

    public TopBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TopBar);

        //get attrs of title
        mTitleIcon = typedArray.getDrawable(R.styleable.TopBar_titleIcon);
        mTitleText = typedArray.getString(R.styleable.TopBar_titleText);
        mTitleTextSize = typedArray.getDimension(R.styleable.TopBar_titleTextSize, 10);
        mTitleTextColor = typedArray.getColor(R.styleable.TopBar_textColor, 0);
        mTitleBackground = typedArray.getDrawable(R.styleable.TopBar_titleBackground);

        //get attrs of menu
        mShowMenu = typedArray.getBoolean(R.styleable.TopBar_showMenu,false);
        mMenuIcon = typedArray.getDrawable(R.styleable.TopBar_menuIcon);
        mMenuText = typedArray.getString(R.styleable.TopBar_menuText);
        mMenuTextSize = typedArray.getDimension(R.styleable.TopBar_menuTextSize, 10);
        mMenuTextColor = typedArray.getColor(R.styleable.TopBar_textColor, 0);
        mMenuBackground = typedArray.getDrawable(R.styleable.TopBar_menuBackground);

        //Should call recycle method after using
        typedArray.recycle();

        mTitleButton = new Button(context);

        //set attrs of title
        if (mTitleIcon!=null)
            mTitleIcon.setBounds(0,0,2*mTitleIcon.getMinimumWidth(),2*mTitleIcon.getMinimumHeight());//size to display, it's necessary
        mTitleButton.setCompoundDrawables(mTitleIcon,null,null,null);
        mTitleButton.setText(mTitleText);
        mTitleButton.setTextAlignment(TEXT_ALIGNMENT_TEXT_START);
        mTitleButton.setTextColor(mTitleTextColor);
        mTitleButton.setTextSize(mTitleTextSize);
        mTitleButton.setBackground(mTitleBackground);
        mTitleButton.setElevation(0);
        mTitleButton.setPadding(0,0,0,0);
        mTitleButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.titleClick();
            }
        });

        //add view
        mTitleParams = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mTitleParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
        addView(mTitleButton,mTitleParams);

        if (mShowMenu) {
            mMenuButton = new Button(context);
            //set attrs of menu
            if (mMenuIcon != null)
                mMenuIcon.setBounds(0, 0, 2 * mMenuIcon.getMinimumWidth(), 2 * mMenuIcon.getMinimumHeight());//size to display, it's necessary
            mMenuButton.setCompoundDrawables(mMenuIcon, null, null, null);
            mMenuButton.setText(mMenuText);
            mMenuButton.setTextColor(mMenuTextColor);
            mMenuButton.setTextSize(mMenuTextSize);
            mMenuButton.setBackground(mMenuBackground);
            mMenuButton.setElevation(0);
            mMenuButton.setPadding(0, 0, 0, 0);
            mMenuButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.menuClick();
                }
            });

            mMenuParams = new LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            mMenuParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
            addView(mMenuButton, mMenuParams);
        }
    }

    public interface topbarClickListener{
        void titleClick();
        void menuClick();
    }

    public void setOnTopbarClickListener(topbarClickListener mListener){
        this.mListener = mListener;
    }

    public void setMenuButtonVisible(boolean visible){
        if (visible)
            mMenuButton.setVisibility(View.VISIBLE);
        else
            mMenuButton.setVisibility(View.GONE);
    }
}