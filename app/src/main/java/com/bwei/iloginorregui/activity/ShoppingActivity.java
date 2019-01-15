package com.bwei.iloginorregui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bwei.iloginorregui.R;
import com.bwei.iloginorregui.adapter.FragmentAdapter;
import com.bwei.iloginorregui.fragment.FiveFragment;
import com.bwei.iloginorregui.fragment.Fragment4;
import com.bwei.iloginorregui.fragment.OneFragment;
import com.bwei.iloginorregui.fragment.ThreeFragment;
import com.bwei.iloginorregui.fragment.TwoFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bwei.iloginorregui.R.drawable.home_f;
import static com.bwei.iloginorregui.R.drawable.home_t;

public class ShoppingActivity extends AppCompatActivity {

    @BindView(R.id.fragment)
    ViewPager fragment;
    @BindView(R.id.rb1)
    RadioButton rb1;
    @BindView(R.id.rb2)
    RadioButton rb2;
    @BindView(R.id.rb3)
    RadioButton rb3;
    @BindView(R.id.rb4)
    RadioButton rb4;
    @BindView(R.id.rb5)
    RadioButton rb5;
    @BindView(R.id.btn_group)
    RadioGroup btnGroup;
    private boolean ischeck;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        ButterKnife.bind(this);
        viewPager = findViewById(R.id.fragment);
        List<Fragment> list = new ArrayList<>();
        list.add(new OneFragment());
        list.add(new TwoFragment());
        list.add(new ThreeFragment());
        list.add(new Fragment4());
        list.add(new FiveFragment());
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), list);
        viewPager.setAdapter(adapter);
        fragment.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                btnGroup.check(btnGroup.getChildAt(i).getId());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        btnGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb1:
                        fragment.setCurrentItem(0);
                        break;
                    case R.id.rb2:
                        fragment.setCurrentItem(1);
                        break;
                    case R.id.rb3:
                        fragment.setCurrentItem(2);
                        break;
                    case R.id.rb4:
                        fragment.setCurrentItem(3);
                        break;
                    case R.id.rb5:
                        fragment.setCurrentItem(4);
                        break;
                }
            }
        });
    }


}
