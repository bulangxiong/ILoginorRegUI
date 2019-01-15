package com.bwei.iloginorregui.activity.my_page;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.bwei.iloginorregui.R;
import com.bwei.iloginorregui.adapter.My_AddressAdapter;
import com.bwei.iloginorregui.bean.Result;
import com.bwei.iloginorregui.bean.dbean.My_AddessBean;
import com.bwei.iloginorregui.bean.ubean.UserInfo;
import com.bwei.iloginorregui.core.DataCall;
import com.bwei.iloginorregui.core.db.DaoMaster;
import com.bwei.iloginorregui.core.db.DaoSession;
import com.bwei.iloginorregui.core.db.UserInfoDao;
import com.bwei.iloginorregui.core.exception.ApiException;
import com.bwei.iloginorregui.fragment.FiveFragment;
import com.bwei.iloginorregui.presenter.AddessPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class My_Shipping_AddressActivity extends AppCompatActivity {

    @BindView(R.id.me_address_wan)
    TextView meAddressWan;
    @BindView(R.id.me_shipping_address)
    RecyclerView meShippingAddress;
    @BindView(R.id.me_souhuo_btn)
    Button meSouhuoBtn;
    private My_AddressAdapter adapter;
    private AddessPresenter addessPresenter;
    private UserInfoDao userInfoDao;
    private DaoSession daoSession;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__shipping__address);
        ButterKnife.bind(this);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        addessPresenter = new AddessPresenter(new Addess());
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        meShippingAddress.setLayoutManager(layoutManager);
        adapter = new My_AddressAdapter(this);
        meShippingAddress.setAdapter(adapter);
        initData();
    }

    private void initData() {
        daoSession = DaoMaster.newDevSession(this, UserInfoDao.TABLENAME);
        userInfoDao = daoSession.getUserInfoDao();
        List<UserInfo> userInfos = userInfoDao.loadAll();
        UserInfo userInfo1 = userInfos.get(0);
        String sessionId1 = userInfo1.getSessionId();
        int userId = userInfo1.getUserId();
        addessPresenter.reqeust(userId,sessionId1);
    }

    @OnClick({R.id.me_address_wan, R.id.me_souhuo_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.me_address_wan:
                 finish();
                break;
            case R.id.me_souhuo_btn:
                Intent intent=new Intent(My_Shipping_AddressActivity.this,NewAddessActivity.class);
                startActivity(intent);
                break;
        }
    }

    private class Addess implements DataCall<Result<List<My_AddessBean>>> {
        @Override
        public void success(Result<List<My_AddessBean>> result) {
            List<My_AddessBean> beans = result.getResult();
            adapter.addItem(beans);
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
