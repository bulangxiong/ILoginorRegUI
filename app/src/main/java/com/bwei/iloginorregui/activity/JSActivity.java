package com.bwei.iloginorregui.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bwei.iloginorregui.R;
import com.bwei.iloginorregui.adapter.MyTJAdapter;
import com.bwei.iloginorregui.adapter.PopuAddressAdapter;
import com.bwei.iloginorregui.bean.Result;
import com.bwei.iloginorregui.bean.sbean.AddressBean;
import com.bwei.iloginorregui.bean.sbean.ShoppingBean;
import com.bwei.iloginorregui.bean.ubean.UserInfo;
import com.bwei.iloginorregui.core.DataCall;
import com.bwei.iloginorregui.core.db.DaoMaster;
import com.bwei.iloginorregui.core.db.DaoSession;
import com.bwei.iloginorregui.core.db.ShoppingBeanDao;
import com.bwei.iloginorregui.core.db.UserInfoDao;
import com.bwei.iloginorregui.core.exception.ApiException;
import com.bwei.iloginorregui.presenter.AddressPreseter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class    JSActivity extends AppCompatActivity {

    @BindView(R.id.ti_image)
    ImageView tiImage;
    @BindView(R.id.ti_recy)
    RecyclerView tiRecy;
    @BindView(R.id.shopping_num)
    TextView shoppingNum;
    @BindView(R.id.shopping_sum)
    TextView shoppingSum;
    @BindView(R.id.address_text_name)
    TextView addressTextName;
    @BindView(R.id.address_text_phone)
    TextView addressTextPhone;
    @BindView(R.id.address_text_address)
    TextView addressTextAddress;
    @BindView(R.id.tijiao)
    Button tijiao;
    private MyTJAdapter myTJAdapter;
    private View view;
    private RecyclerView recyclerView;
    private PopuAddressAdapter popuAddressAdapter;
    private PopupWindow popupWindow;
    private List<ShoppingBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_js);
        ButterKnife.bind(this);
        DaoSession daoSession = DaoMaster.newDevSession(this, ShoppingBeanDao.TABLENAME);
        ShoppingBeanDao shoppingBeanDao = daoSession.getShoppingBeanDao();
        list = shoppingBeanDao.loadAll();
        getSum();
        myTJAdapter = new MyTJAdapter();
        myTJAdapter.setCountListener(new MyTJAdapter.CountListener() {
            @Override
            public void getCount(int count) {
                getSum();
            }
        });
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        myTJAdapter.addList(list);
        tiRecy.setLayoutManager(manager);
        tiRecy.setAdapter(myTJAdapter);
        DaoSession daoSession1 = DaoMaster.newDevSession(this, UserInfoDao.TABLENAME);
        UserInfoDao userDao = daoSession1.getUserInfoDao();
        List<UserInfo> list1 = userDao.loadAll();
        long userId = list1.get(0).getUserId();
        String sessionId = list1.get(0).getSessionId();
        view = View.inflate(this, R.layout.popu_address_layout, null);
        new AddressPreseter(new My()).reqeust((int) userId, sessionId);
        recyclerView = view.findViewById(R.id.address_recycle_show);
        popuAddressAdapter = new PopuAddressAdapter();
        StaggeredGridLayoutManager manager1 = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager1);
        recyclerView.setAdapter(popuAddressAdapter);
        popuAddressAdapter.setOnCheckListener(new PopuAddressAdapter.CheckListener() {
            @Override
            public void check(AddressBean address) {
                String address1 = address.getAddress();
                String phone = address.getPhone();
                String realName = address.getRealName();
                addressTextName.setText(realName);
                addressTextPhone.setText(phone);
                addressTextAddress.setText(address1);
                popupWindow.dismiss();
            }
        });
    }

    @OnClick(R.id.ti_image)
    public void onViewClicked() {
        popupWindow = new PopupWindow(view, 800, 200,
                true);
//        int height = getWindowManager().getDefaultDisplay().getHeight();
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x000000));
        popupWindow.showAsDropDown(tiImage);

    }

    @OnClick(R.id.tijiao)
    public void onViewClicked(View view) {
        Intent intent = new Intent(JSActivity.this,ZFActivity.class);
        startActivity(intent);
    }

    class My implements DataCall<Result<List<AddressBean>>> {


        @Override
        public void success(Result<List<AddressBean>> result) {
            if (result.getStatus().equals("0000")) {
                List<AddressBean> result1 = result.getResult();
                popuAddressAdapter.addList(result1);
                popuAddressAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    public void getSum() {
        double sum = 0.0;
        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            sum += list.get(i).getCount() * list.get(i).getPrice();
            count += list.get(i).getCount();
        }
        shoppingSum.setText(sum + "");
        shoppingNum.setText(count + "");
    }
}
