package com.xllllh.android.takeaway;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UsercenterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UsercenterFragment extends Fragment {

    private LinearLayout headerNotLogin;
    private LinearLayout headerLogin;

    public UsercenterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment UsercenterFragment.
     */
    public static UsercenterFragment newInstance() {
        UsercenterFragment fragment = new UsercenterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_usercenter, container, false);

        headerNotLogin = (LinearLayout) view.findViewById(R.id.header_not_login);
        headerLogin = (LinearLayout) view.findViewById(R.id.header_login);

        if (UserUtils.isLogined()) {
            headerNotLogin.setVisibility(View.GONE);
            headerLogin.setVisibility(View.VISIBLE);
        } else {
            headerNotLogin.setVisibility(View.VISIBLE);
            headerLogin.setVisibility(View.GONE);
        }

        bindHeaderLogin();
        bindHeaderNotLogin();
        bindMenu();

        return view;
    }

    private void bindHeaderNotLogin(){
        headerNotLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    private void bindHeaderLogin(){
        View avatar_block = headerLogin.findViewById(R.id.avatar_block);
        avatar_block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);
            }
        });
        TextView username_view = (TextView) avatar_block.findViewById(R.id.username_view);
        username_view.setText(UserUtils.mUsername);
    }
    private void bindMenu(){

    }
}
