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

import org.json.JSONObject;

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
    private String price2send;
    private JSONObject shop_json ;

    public ShopFragmentPagerAdapter(FragmentManager fm, Context context, JSONObject shop_json) {
        super(fm);
        this.context = context;
        this.shop_json = shop_json;
        this.shopId = Utils.getValueFromJSONObject(shop_json,"id","0");
        this.price2send = Utils.getValueFromJSONObject(shop_json,"price_tosend","1");
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return DishListFragment.newInstance(shop_json);
            case 1:
                return ShopCommentFragment.newInstance(shopId);
            case 2:
                return ShopInfoFragment.newInstance();
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
