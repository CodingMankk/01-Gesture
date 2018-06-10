package com.oztaking.www.gesture;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        mViewPager.setAdapter(new ImageViewPagerAdapter(getApplicationContext()));
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.vp_iamge);
    }
}
