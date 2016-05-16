package com.xllllh.android.takeaway;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by mephist0 on 16-5-16.
 */
public class SampleFragmentPagerAdapter extends FragmentPagerAdapter{
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[]{"首页","订单","我的"};
    private int imageDefaultId[] = new int[]{R.mipmap.tab_home,R.mipmap.tab_order,R.mipmap.tab_my};
    private int imageActiveId[] = new int[]{R.mipmap.tab_home_active,R.mipmap.tab_order_active,R.mipmap.tab_my_active};
    private Context context;

    public SampleFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    public View getTabView(int position) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
        TextView tv = (TextView) v.findViewById(R.id.textView);
        tv.setText(tabTitles[position]);
        ImageView img = (ImageView) v.findViewById(R.id.imageView);
        img.setImageResource(imageDefaultId[position]);
        return v;
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
