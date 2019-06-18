package com.example.layoutmanagertest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.layoutmanagertest.R;
import com.example.layoutmanagertest.layoutmanager.tantantest.CardConfig;
import com.example.layoutmanagertest.layoutmanager.tantantest.TanTanLayoutManager;
import com.example.layoutmanagertest.layoutmanager.tantantest.TanTanTouchCallback;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class TanTanActivity extends AppCompatActivity {
    private RecyclerView layoutTestRv;
    private List<String> list = new ArrayList<>();
    private CommonAdapter<String> mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CardConfig.initConfig(this);
        setContentView(R.layout.activity_rv);
        layoutTestRv = findViewById(R.id.layoutTestRv);
        initData();
        layoutTestRv.setLayoutManager(new TanTanLayoutManager());
        mAdapter = new CommonAdapter<String>(this, R.layout.item_tantan_card, list) {
            @Override
            protected void convert(ViewHolder holder, String o, int position) {

            }

        };
        ItemTouchHelper.Callback callback = new TanTanTouchCallback(layoutTestRv, mAdapter, list);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(layoutTestRv);
        layoutTestRv.setAdapter(mAdapter);
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            list.add("test");
        }
    }
}
