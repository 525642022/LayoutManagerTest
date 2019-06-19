package com.example.layoutmanagertest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.layoutmanagerlib.layoutmanager.arc.ArcControl;
import com.example.layoutmanagerlib.layoutmanager.arc.ArcLayoutManager;
import com.example.layoutmanagertest.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ArcActivity extends AppCompatActivity {
    RecyclerView layoutTestRv;
    private List<String> list = new ArrayList<>();
    private CommonAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv);
        layoutTestRv = findViewById(R.id.layoutTestRv);
        initData();
        ArcControl arcControl = new ArcControl(400, 30, 0, 10, -120, 120);
        ArcLayoutManager arcLayoutManager = new ArcLayoutManager(arcControl);
        layoutTestRv.setLayoutManager(arcLayoutManager);
        mAdapter = new CommonAdapter<String>(this, R.layout.item_arc_card, list) {
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
