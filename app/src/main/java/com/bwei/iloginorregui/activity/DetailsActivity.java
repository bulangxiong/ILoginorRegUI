package com.bwei.iloginorregui.activity;

import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


import com.bwei.iloginorregui.R;
import com.bwei.iloginorregui.adapter.DetailsPagerAdapter;
import com.bwei.iloginorregui.fragment.details.DetailsCommentsFragment;
import com.bwei.iloginorregui.fragment.details.DetailsIntroduceFragment;
import com.bwei.iloginorregui.fragment.details.GoodIntroduceFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author lmx
 * @date 2019/1/8
 * 商品详情页面
 */
public class DetailsActivity extends AppCompatActivity {


    @BindView(R.id.details_tablayout)
    TabLayout mDetailsTablayout;
    @BindView(R.id.details_viewpager)
    ViewPager mDetailsViewpager;
    //默认值
    private int cid = 1;
    private List<Fragment> mFragmentList;
    private List<String> mListTitle;
//    private GoodIntroduceFragment goodsIntroduceFragment = new GoodIntroduceFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        initData(cid);
        initView();
    }

    private void initView() {
        mFragmentList = new ArrayList<>();
        mListTitle = new ArrayList<>();
        mFragmentList.add(new GoodIntroduceFragment());
        mFragmentList.add(new DetailsIntroduceFragment());
        mFragmentList.add(new DetailsCommentsFragment());
        mListTitle.add("商品");
        mListTitle.add("详情");
        mListTitle.add("评论");
        //此方法就是让tablayout和ViewPager联动
        mDetailsViewpager.setAdapter(new DetailsPagerAdapter
                (getSupportFragmentManager(), DetailsActivity.this, mFragmentList, mListTitle));
        mDetailsTablayout.setupWithViewPager(mDetailsViewpager);
    }


    /**
     * 传商品的id
     *
     * @param cid
     */
    private void initData(int cid) {
        Intent intent = getIntent();
        int intExtra = intent.getIntExtra("goodsId", cid);
        intent.putExtra("cid", intExtra);
//        GoodIntroduceFragment fragment=new GoodIntroduceFragment();
//        FragmentManager manager = fragment.getFragmentManager();
//        manager.beginTransaction().replace(R.id.aaaaaa,fragment).commit();
//        Intent intent1 = new Intent(DetailsActivity.this, GoodIntroduceFragment.class);
//        startActivity(intent1);
        Toast.makeText(this, intExtra + "", Toast.LENGTH_SHORT).show();
    }

}
