package com.example.layoutmanagertest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.TypedValue;

import com.example.layoutmanagertest.R;
import com.example.layoutmanagertest.layoutmanager.tantantest.TanTanControl;
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

        setContentView(R.layout.activity_rv);
        layoutTestRv = findViewById(R.id.layoutTestRv);
        initData();
        TanTanControl tanTanControl = new TanTanControl(4
                , 0.05f
                ,(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics()));
        layoutTestRv.setLayoutManager(new TanTanLayoutManager(tanTanControl));
        mAdapter = new CommonAdapter<String>(this, R.layout.item_tantan_card, list) {
            @Override
            protected void convert(ViewHolder holder, String o, int position) {

            }

        };
        ItemTouchHelper.Callback callback = new TanTanTouchCallback(layoutTestRv, mAdapter, list,tanTanControl);
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
