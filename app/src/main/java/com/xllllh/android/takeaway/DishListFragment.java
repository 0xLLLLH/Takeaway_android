package com.xllllh.android.takeaway;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DishListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DishListFragment extends Fragment {
    private static final String ARG_SHOP_ID = "shopId";

    private StickyListHeadersListView stickyList;

    private String shopId;
    public DishListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @param shopId shop id.
     * @return A new instance of fragment DishListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DishListFragment newInstance(String shopId) {
        DishListFragment fragment = new DishListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SHOP_ID, shopId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            shopId = getArguments().getString(ARG_SHOP_ID);
        }
        LoadShopDetailTask detailTask = new LoadShopDetailTask(shopId);
        detailTask.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_dish_list, container, false);
        stickyList = (StickyListHeadersListView) view.findViewById(R.id.dish_list);
        // Inflate the layout for this fragment
        return view;
    }

    class LoadShopDetailTask extends AsyncTask<Void,Void,Boolean> {

        String shopId;
        JSONObject shopDetail;
        List<JSONObject> dishList;
        HashMap<String,String> dishType;

        LoadShopDetailTask(String shopId) {
            this.shopId = shopId;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                shopDetail = ShopUtils.Shop.getShopDetail(shopId);
                dishType = ShopUtils.Shop.getDishTypeList(shopDetail.getJSONArray("dishtype"));
                dishList = ShopUtils.Shop.getDishList(shopId);
                return true;
            }catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                StickListAdapter adapter = new StickListAdapter(getActivity(), dishList, dishType);
                stickyList.setAdapter(adapter);
            }
        }
    }
}
