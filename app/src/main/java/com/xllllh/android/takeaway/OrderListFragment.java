package com.xllllh.android.takeaway;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderListFragment extends Fragment {

    Button login;
    RecyclerView orderList;
    SwipeRefreshLayout refreshLayout;

    public OrderListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment OrderListFragment.
     */
    public static OrderListFragment newInstance() {
        OrderListFragment fragment = new OrderListFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_list, container, false);
        if (UserUtils.isLogined()) {
            view.findViewById(R.id.view_not_login).setVisibility(View.GONE);
            view.findViewById(R.id.view_order_list).setVisibility(View.VISIBLE);
            orderList = (RecyclerView) view.findViewById(R.id.order_list);
            new LoadOrderListTask().execute();

            refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.order_refresh_layout);
            refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                    android.R.color.holo_red_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_green_light);
            refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    new LoadOrderListTask().execute();
                    Toast.makeText(getActivity(),"加载完成",Toast.LENGTH_SHORT).show();
                    refreshLayout.setRefreshing(false);
                }
            });
        } else {
            view.findViewById(R.id.view_not_login).setVisibility(View.VISIBLE);
            view.findViewById(R.id.view_order_list).setVisibility(View.GONE);

            login = (Button) view.findViewById(R.id.order_list_login);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            });
        }
        return view;
    }

    class LoadOrderListTask extends AsyncTask<Void,Void,Boolean> {

        ProgressDialog dialog;
        List<JSONObject> response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getActivity());
            dialog.setCancelable(false);
            dialog.setMessage("正在加载订单列表...");
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            response = OrderUtils.getOrderList(UserUtils.getUsername(),OrderUtils.STATE_ALL);
            return null!=response;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            dialog.dismiss();
            if (aBoolean) {
                OrderListRecyclerViewAdapter adapter = new OrderListRecyclerViewAdapter(response);
                orderList.setAdapter(adapter);
                orderList.setLayoutManager( new LinearLayoutManager(getActivity()));
            }
        }
    }
}
