package com.xllllh.android.takeaway;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 0xLLLLH on 16-6-1.
 *
 */
public class ShopFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[]{"点菜","评价","商家"};
    private Context context;
    private int mChildCount;
    private String shopId;

    public ShopFragmentPagerAdapter(FragmentManager fm, Context context, String shopId) {
        super(fm);
        this.context = context;
        this.shopId = shopId;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return DishListFragment.newInstance(shopId);
            case 1:
                return OrderListFragment.newInstance();
            case 2:
                return UsercenterFragment.newInstance();
            default:
                Log.d("FragmentPagerAdapter", "Can't find corresponding fragment at position " + position);
                return new Fragment();
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        // 重写getItemPosition,保证每次获取时都强制重绘UI
        if (mChildCount > 0) {
            mChildCount--;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }
}
