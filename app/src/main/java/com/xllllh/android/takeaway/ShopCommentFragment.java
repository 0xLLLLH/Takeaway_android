package com.xllllh.android.takeaway;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONObject;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 */
public class ShopCommentFragment extends Fragment {

    private static final String ARG_SHOP_ID = "shopId";
    private String shopId = "1";

    private RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ShopCommentFragment() {
    }

    @SuppressWarnings("unused")
    public static ShopCommentFragment newInstance(String shopId) {
        ShopCommentFragment fragment = new ShopCommentFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopcomment_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            new LoadShopCommentsTask(shopId).execute();
        }
        return view;
    }
    class LoadShopCommentsTask extends AsyncTask<Void,Void,Boolean> {

        String shopId;
        List<JSONObject> commentList;

        LoadShopCommentsTask(String shopId) {
            this.shopId = shopId;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                commentList = ShopUtils.Comments.getCommentList(shopId);
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
                recyclerView.setAdapter(new MyShopCommentRecyclerViewAdapter(commentList));
            }
        }
    }
}
