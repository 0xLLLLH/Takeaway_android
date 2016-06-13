package com.xllllh.android.takeaway;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.ArrayList;

public class AddressListActivity extends Activity {

    ListView listView;
    ArrayList<String> addressList;
    TopBar topBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);


        topBar = (TopBar) findViewById(R.id.topbar);
        topBar.setOnTopbarClickListener(new TopBar.topbarClickListener() {
            @Override
            public void titleClick() {
                finish();
            }

            @Override
            public void menuClick() {

            }
        });

        addressList = new ArrayList<>();
        for (JSONObject json:AddressUtils.addressList) {
            addressList.add(String.format("%s\t\t%s\n%s",
                    json.optString("name").trim(),
                    json.optString("phone").trim(),
                    json.optString("address").trim()));
        }
        listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(new ArrayAdapter<>(AddressListActivity.this,
                android.R.layout.simple_list_item_1,addressList));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("index",position);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }
}
