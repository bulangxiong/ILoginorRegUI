package com.bwei.iloginorregui.activity.my_page;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.TextView;

import com.bwei.iloginorregui.R;
import com.bwei.iloginorregui.adapter.MyWalletAdapter;
import com.bwei.iloginorregui.bean.Result;
import com.bwei.iloginorregui.bean.UserWallet;
import com.bwei.iloginorregui.bean.WalletBean;
import com.bwei.iloginorregui.bean.ubean.UserInfo;
import com.bwei.iloginorregui.core.DataCall;
import com.bwei.iloginorregui.core.db.DaoMaster;
import com.bwei.iloginorregui.core.db.DaoSession;
import com.bwei.iloginorregui.core.db.UserInfoDao;
import com.bwei.iloginorregui.core.exception.ApiException;
import com.bwei.iloginorregui.presenter.WalletPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WalletActivity extends AppCompatActivity {

    @BindView(R.id.money_text)
    TextView moneyText;
    @BindView(R.id.mallet_recycle)
    RecyclerView malletRecycle;
    private MyWalletAdapter myWalletAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        ButterKnife.bind(this);
        DaoSession daoSession = DaoMaster.newDevSession(this, UserInfoDao.TABLENAME);
        UserInfoDao userDao = daoSession.getUserInfoDao();
        List<UserInfo> list = userDao.loadAll();
        long userId = list.get(0).getUserId();
        String sessionId = list.get(0).getSessionId();
        new WalletPresenter(new MyCall()).reqeust((int)userId,sessionId,1,10);
        myWalletAdapter = new MyWalletAdapter();
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        malletRecycle.setLayoutManager(manager);
        malletRecycle.setAdapter(myWalletAdapter);

    }
    class MyCall implements DataCall<Result<UserWallet>> {




        @Override
        public void success(Result<UserWallet> result) {
            UserWallet result1 = result.getResult();
            double balance = result1.getBalance();

            moneyText.setText(balance+"");
            List<WalletBean> detailList = result1.getDetailList();
            myWalletAdapter.addList(detailList);
            myWalletAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
