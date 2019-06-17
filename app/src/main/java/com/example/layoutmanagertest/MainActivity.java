package com.example.layoutmanagertest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.layoutmanagertest.layoutmanager.echelon.TrapezoidLayoutManager;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView layoutTestRv;
    private List<String> list = new ArrayList<>();
    private CommonAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layoutTestRv = findViewById(R.id.layoutTestRv);
        initData();
        TrapezoidLayoutManager trapezoidLayoutManager = new TrapezoidLayoutManager();
        layoutTestRv.setLayoutManager(trapezoidLayoutManager);
        mAdapter = new CommonAdapter<String>(this, R.layout.item_swipe_card, list) {
            @Override
            protected void convert(ViewHolder holder, String o, int position) {

            }

        };
        layoutTestRv.setAdapter(mAdapter);
    }

    private void initData() {
        for (int i = 0; i < 12; i++) {
            list.add("test");
        }
    }
}
