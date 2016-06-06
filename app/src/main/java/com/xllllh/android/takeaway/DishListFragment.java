package com.xllllh.android.takeaway;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private static final String ARG_PRICE_SEND = "price2Send";

    private StickyListHeadersListView stickyList;

    private String shopId;
    private String price2Send;
    private ImageView cart_image;
    private TextView cart_price;
    private Button cart_button;
    private int priceSum;

    public DishListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @param shopId shop id.
     * @param price2Send price start send.
     * @return A new instance of fragment DishListFragment.
     */
    public static DishListFragment newInstance(String shopId,String price2Send) {
        DishListFragment fragment = new DishListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SHOP_ID, shopId);
        args.putString(ARG_PRICE_SEND,price2Send);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            shopId = getArguments().getString(ARG_SHOP_ID);
            price2Send = getArguments().getString(ARG_PRICE_SEND);
        }
        priceSum = 0;
        LoadShopDetailTask detailTask = new LoadShopDetailTask(shopId);
        detailTask.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_dish_list, container, false);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        stickyList = (StickyListHeadersListView) view.findViewById(R.id.dish_list);
        cart_image = (ImageView) view.findViewById(R.id.cart_image);
        cart_price = (TextView) view.findViewById(R.id.cart_price_sum);
        cart_button = (Button) view.findViewById(R.id.cart_order_button);
        cart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),NewOrderActivity.class);
                startActivity(intent);
            }
        });
        setCartButton();
        return view;
    }

    protected void cartAddItem(int price) {
        priceSum += price;
        cart_price.setText(String.format("￥%d",priceSum));
        setCartImage();
        setCartButton();
    }

    protected void cartRemoveItem(int price) {
        priceSum -= price;
        if (priceSum > 0)
            cart_price.setText(String.format("￥%d",priceSum));
        else
            cart_price.setText(R.string.cart_no_dish);
        setCartImage();
        setCartButton();
    }

    protected void setCartImage() {
        if (priceSum>0)
            cart_image.setImageResource(R.mipmap.ic_cart_active);
        else
            cart_image.setImageResource(R.mipmap.ic_cart_inactive);
    }

    protected void setCartButton() {
        if (priceSum>=Integer.parseInt(price2Send)) {
            cart_button.setBackground(getResources().getDrawable(R.color.colorPrimary,null));
            cart_button.setText("去结算");
            cart_button.setTextColor(ContextCompat.getColor(getActivity(),R.color.colorTextInPrimaryColor));
            cart_button.setEnabled(true);
        } else {
            cart_button.setBackground(getResources().getDrawable(R.color.colorDarkGrey,null));
            cart_button.setText(String.format("￥%s起送",price2Send));
            cart_button.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.white));
            cart_button.setEnabled(false);
        }
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
                final StickListAdapter adapter = new StickListAdapter(getActivity(), dishList, dishType);
                stickyList.setAdapter(adapter);
                adapter.setButtonOnClickListener(new StickListAdapter.ButtonOnClickListener() {

                    @Override
                    public void plus(StickListAdapter.ViewHolder holder, JSONObject dish) {
                        holder.minus.setVisibility(View.VISIBLE);
                        Integer numInt = Integer.parseInt(holder.num.getText().toString());
                        numInt++;
                        holder.num.setText(numInt.toString());
                        holder.num.setVisibility(View.VISIBLE);
                        cartAddItem(Integer.parseInt(Utils.getValueFromJSONObject(dish,"price","0")));
                    }

                    @Override
                    public void minus(StickListAdapter.ViewHolder holder, JSONObject dish) {
                        Integer numInt = Integer.parseInt(holder.num.getText().toString());
                        if (numInt>0)
                        {
                            numInt--;
                            holder.num.setText(numInt.toString());
                            if (numInt==0)
                            {
                                holder.minus.setVisibility(View.INVISIBLE);
                                holder.num.setVisibility(View.INVISIBLE);
                            }
                            cartRemoveItem(Integer.parseInt(Utils.getValueFromJSONObject(dish,"price","0")));
                        }

                    }
                });
            }
        }
    }
}
