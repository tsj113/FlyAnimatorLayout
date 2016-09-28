package com.fly.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fly.pulg.FlyAnimatorLayout;

public class MainActivity extends AppCompatActivity {

    private FlyAnimatorLayout moveLayout;

    private String[] moveTextArr = new String[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moveLayout = (FlyAnimatorLayout)findViewById(R.id.move_layout);

        init();
    }

    private void init(){
        for (int i = 0; i < moveTextArr.length; i++) {
            moveTextArr[i] = "35343434" + i;
        }
        moveLayout.setTextContent(moveTextArr);
        moveLayout.setTextColor(Color.GREEN);
        moveLayout.setTextSize(18);
        moveLayout.initView();
        moveLayout.startAnimator();
    }
}
