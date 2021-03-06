package com.example.layoutmanagertest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.layoutmanagertest.activity.ArcActivity;
import com.example.layoutmanagertest.activity.TanTanActivity;
import com.example.layoutmanagertest.activity.TrapezoidActivity;

public class MainActivity extends AppCompatActivity {
    private TextView trapezoid_tv;
    private TextView arc_tv;
    private TextView tan_tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        trapezoid_tv =findViewById(R.id.trapezoid_tv);
        arc_tv =findViewById(R.id.arc_tv);
        tan_tv =findViewById(R.id.tan_tv);
        trapezoid_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TrapezoidActivity.class);
                startActivity(intent);
            }
        });

        arc_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ArcActivity.class);
                startActivity(intent);
            }
        });
        tan_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TanTanActivity.class);
                startActivity(intent);
            }
        });
    }


}
