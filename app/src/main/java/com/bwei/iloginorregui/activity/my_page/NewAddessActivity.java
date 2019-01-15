package com.bwei.iloginorregui.activity.my_page;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bwei.iloginorregui.R;
import com.bwei.iloginorregui.bean.Result;
import com.bwei.iloginorregui.bean.ubean.UserInfo;
import com.bwei.iloginorregui.core.DataCall;
import com.bwei.iloginorregui.core.db.DaoMaster;
import com.bwei.iloginorregui.core.db.DaoSession;
import com.bwei.iloginorregui.core.db.UserInfoDao;
import com.bwei.iloginorregui.core.exception.ApiException;
import com.bwei.iloginorregui.presenter.AddAddressPresenter;
import com.lljjcoder.citypickerview.widget.CityPicker;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.Nullable;


public class NewAddessActivity extends AppCompatActivity {

    @BindView(R.id.mEt_Addressee_Mine)
    EditText mEtAddresseeMine;
    @BindView(R.id.mEt_Phone_Mine)
    EditText mEtPhoneMine;
    @BindView(R.id.mEt_Address_Mine)
    EditText mEtAddressMine;
    @BindView(R.id.mEt_Postal_Mine)
    EditText mEtPostalMine;
    @BindView(R.id.mBt_Add_Mine)
    Button mBtAddMine;
    @BindView(R.id.mTv_Address_Mine)
    TextView mTvAddressMine;
    private CityPicker mCP;
    private static final String TAG = "NewAddressActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_addess);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.mTv_Address_Mine, R.id.mBt_Add_Mine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mTv_Address_Mine:
                mYunCityPicher();
                mCP.show();
                break;
            case R.id.mBt_Add_Mine:
                DaoSession daoSession = DaoMaster.newDevSession(NewAddessActivity.this, UserInfoDao.TABLENAME);
                UserInfoDao userDao = daoSession.getUserInfoDao();
                List<UserInfo> list = userDao.loadAll();
                long userId = list.get(0).getUserId();
                String sessionId = list.get(0).getSessionId();
                Log.d(TAG, "onViewClicked:1111111 "+userId+sessionId);
                String name = mEtAddresseeMine.getText().toString();
                String phone = mEtPhoneMine.getText().toString();
                String sheng = mTvAddressMine.getText().toString();
                String xiang = mEtAddressMine.getText().toString();
                String post = mEtPostalMine.getText().toString();
                new AddAddressPresenter(new MyCall()).reqeust((int)userId,sessionId,name,
                        phone,sheng+xiang,post);
                break;
        }
    }
    class MyCall implements DataCall<Result> {


        @Override
        public void success(Result result) {
            if(result.getStatus().equals("0000")){
                Intent intent = new Intent(NewAddessActivity.this,My_Shipping_AddressActivity.class);
                startActivity(intent);
                finish();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
    public void mYunCityPicher() {
        mCP = new CityPicker.Builder(NewAddessActivity.this)
                .textSize(20)
                //地址选择
                .title("地址选择")
                .backgroundPop(0xa0000000)
                //文字的颜色
                .titleBackgroundColor("#0CB6CA")
                .titleTextColor("#000000")
                .backgroundPop(0xa0000000)
                .confirTextColor("#000000")
                .cancelTextColor("#000000")
                .province("xx省")
                .city("xx市")
                .district("xx区")
                //滑轮文字的颜色
                .textColor(Color.parseColor("#000000"))
                //省滑轮是否循环显示
                .provinceCyclic(true)
                //市滑轮是否循环显示
                .cityCyclic(false)
                //地区（县）滑轮是否循环显示
                .districtCyclic(false)
                //滑轮显示的item个数
                .visibleItemsCount(7)
                //滑轮item间距
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();

        //监听
        mCP.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省
                String province = citySelected[0];
                //市
                String city = citySelected[1];
                //区。县。（两级联动，必须返回空）
                String district = citySelected[2];
                //邮证编码
                String code = citySelected[3];

                mTvAddressMine.setText(province + city + district);
            }

            @Override
            public void onCancel() {


            }
        });
    }
}
