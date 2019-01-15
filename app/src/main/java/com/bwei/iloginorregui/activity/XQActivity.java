package com.bwei.iloginorregui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bwei.iloginorregui.R;
import com.bwei.iloginorregui.bean.Result;
import com.bwei.iloginorregui.bean.sbean.ShoppingBean;
import com.bwei.iloginorregui.bean.sbean.XQBean;
import com.bwei.iloginorregui.bean.ubean.UserInfo;
import com.bwei.iloginorregui.core.DataCall;
import com.bwei.iloginorregui.core.IdeaScrollView;
import com.bwei.iloginorregui.core.IdeaViewPager;
import com.bwei.iloginorregui.core.StatusBarCompat;
import com.bwei.iloginorregui.core.db.DaoMaster;
import com.bwei.iloginorregui.core.db.DaoSession;
import com.bwei.iloginorregui.core.db.UserInfoDao;
import com.bwei.iloginorregui.core.exception.ApiException;
import com.bwei.iloginorregui.fragment.DetailsFragment;
import com.bwei.iloginorregui.presenter.AddToCar;
import com.bwei.iloginorregui.presenter.ShoppingPresenter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class XQActivity extends AppCompatActivity {
    @BindView(R.id.x_fragment1)
    FrameLayout xFragment1;
    @BindView(R.id.x_fragment2)
    FrameLayout xFragment2;
    @BindView(R.id.x_fragment3)
    FrameLayout xFragment3;
    @BindView(R.id.xq_back)
    RadioButton xqBack;
    @BindView(R.id.xq_add)
    ImageView xqAdd;
    @BindView(R.id.xq_buy)
    ImageView xqBuy;
    private IdeaViewPager viewPager;
    private IdeaScrollView ideaScrollView;
    private TextView text;
    private LinearLayout header;
    private RadioGroup radioGroup;
    private LinearLayout headerParent;
    private ImageView icon;
    private View layer;
    private float currentPercentage = 0;
    private RadioGroup.OnCheckedChangeListener radioGroupListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
                radioButton.setTextColor(radioButton.isChecked() ? getRadioCheckedAlphaColor(currentPercentage) : getRadioAlphaColor(currentPercentage));
                if (radioButton.isChecked() && isNeedScrollTo) {
                    ideaScrollView.setPosition(i);
                }
            }
        }
    };
    private boolean isNeedScrollTo = true;
    private List<XQBean> list2 = new ArrayList<>();
    ;
    private long userId;
    private String sessionId;
    private ShoppingBean shoppingBean;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xq);
        ButterKnife.bind(this);
        StatusBarCompat.translucentStatusBar(this);
        //header = (LinearLayout)findViewById(R.id.header);
        headerParent = (LinearLayout) findViewById(R.id.headerParent);
        //icon = (ImageView)findViewById(R.id.icon);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        ideaScrollView = (IdeaScrollView) findViewById(R.id.ideaScrollView);
        viewPager = (IdeaViewPager) findViewById(R.id.viewPager);
        layer = findViewById(R.id.layer);
        DaoSession daoSession = DaoMaster.newDevSession(this, UserInfoDao.TABLENAME);
        UserInfoDao userDao = daoSession.getUserInfoDao();
        List<UserInfo> list = userDao.loadAll();
        userId = list.get(0).getUserId();
        sessionId = list.get(0).getSessionId();
        new ShoppingPresenter(new MyC()).reqeust((int) userId, sessionId);

        Rect rectangle = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rectangle);
        ideaScrollView.setViewPager(viewPager, getMeasureHeight(headerParent) - rectangle.top);
        //icon.setImageAlpha(0);
        radioGroup.setAlpha(0);
        radioGroup.check(radioGroup.getChildAt(0).getId());

        View one = findViewById(R.id.one);
        View two = findViewById(R.id.two);
        //View four = findViewById(R.id.four);
        View three = findViewById(R.id.three);
        ArrayList<Integer> araryDistance = new ArrayList<>();

        araryDistance.add(0);
        araryDistance.add(getMeasureHeight(one) - getMeasureHeight(headerParent));
        araryDistance.add(getMeasureHeight(one) + getMeasureHeight(two) - getMeasureHeight(headerParent));
        araryDistance.add(getMeasureHeight(one) + getMeasureHeight(two) + getMeasureHeight(three) - getMeasureHeight(headerParent));

        ideaScrollView.setArrayDistance(araryDistance);

        ideaScrollView.setOnScrollChangedColorListener(new IdeaScrollView.OnScrollChangedColorListener() {
            @Override
            public void onChanged(float percentage) {

                int color = getAlphaColor(percentage > 0.9f ? 1.0f : percentage);
                //header.setBackgroundDrawable(new ColorDrawable(color));
                radioGroup.setBackgroundDrawable(new ColorDrawable(color));
                //icon.setImageAlpha((int) ((percentage>0.9f?1.0f:percentage)*255));
                radioGroup.setAlpha((percentage > 0.9f ? 1.0f : percentage) * 255);

                setRadioButtonTextColor(percentage);

            }

            @Override
            public void onChangedFirstColor(float percentage) {

            }

            @Override
            public void onChangedSecondColor(float percentage) {

            }
        });

        ideaScrollView.setOnSelectedIndicateChangedListener(new IdeaScrollView.OnSelectedIndicateChangedListener() {
            @Override
            public void onSelectedChanged(int position) {
                isNeedScrollTo = false;
                radioGroup.check(radioGroup.getChildAt(position).getId());
                isNeedScrollTo = true;
            }
        });

        radioGroup.setOnCheckedChangeListener(radioGroupListener);
        DetailsFragment detailsFragment = new DetailsFragment();
//        PLFragment plFragment = new PLFragment();
//        XQFragment xqFragment = new XQFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.x_fragment1, detailsFragment).show(detailsFragment);
//        transaction.add(R.id.x_fragment2, xqFragment).show(xqFragment);
//        transaction.add(R.id.x_fragment3, plFragment).show(plFragment);
        transaction.commit();
        //new DetailsPresenter().request();
    }

    public void setRadioButtonTextColor(float percentage) {
        if (Math.abs(percentage - currentPercentage) >= 0.1f) {
            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
                radioButton.setTextColor(radioButton.isChecked() ? getRadioCheckedAlphaColor(percentage) : getRadioAlphaColor(percentage));
            }
            this.currentPercentage = percentage;
        }
    }

    public int getMeasureHeight(View view) {
        int width = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(width, height);
        return view.getMeasuredHeight();
    }

    public int getAlphaColor(float f) {
        return Color.argb((int) (f * 255), 0x09, 0xc1, 0xf4);
    }

    public int getLayerAlphaColor(float f) {
        return Color.argb((int) (f * 255), 0x09, 0xc1, 0xf4);
    }

    public int getRadioCheckedAlphaColor(float f) {
        return Color.argb((int) (f * 255), 0x44, 0x44, 0x44);
    }

    public int getRadioAlphaColor(float f) {
        return Color.argb((int) (f * 255), 0xFF, 0xFF, 0xFF);

    }

    @OnClick({R.id.xq_back, R.id.xq_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.xq_back:
                finish();
                break;
            case R.id.xq_add:
                Intent intent = getIntent();
                String id = intent.getStringExtra("id");
                //Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
                XQBean xqBean = new XQBean();
                xqBean.setCommodityId(Integer.parseInt(id));
                xqBean.setCount(1);
                list2.add(xqBean);
                Gson gson = new Gson();
                String s = gson.toJson(list2);
                new AddToCar(new MyCall()).reqeust((int) userId, sessionId, s);
                break;
        }
    }

    @OnClick(R.id.xq_buy)
    public void onViewClicked() {
        Intent intent = new Intent(this, JSActivity.class);
        startActivity(intent);
    }

    class MyCall implements DataCall<Result> {


        @Override
        public void success(Result result) {
            Toast.makeText(XQActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();

        }

        @Override
        public void fail(ApiException e) {

        }
    }

    class MyC implements DataCall<Result<List<ShoppingBean>>> {


        @Override
        public void success(Result<List<ShoppingBean>> result) {
            List<ShoppingBean> result1 = result.getResult();
            for (int i = 0; i < result1.size(); i++) {
                shoppingBean = result1.get(i);
                int commodityId = shoppingBean.getCommodityId();
                int count = shoppingBean.getCount();
                XQBean xqBean = new XQBean();
                xqBean.setCount(count);
                xqBean.setCommodityId(commodityId);
                list2.add(xqBean);
            }


        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
