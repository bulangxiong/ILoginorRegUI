package com.bwei.iloginorregui.activity.my_page;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.bwei.iloginorregui.R;
import com.bwei.iloginorregui.adapter.MyFootAdapter;
import com.bwei.iloginorregui.bean.FootBean;
import com.bwei.iloginorregui.bean.Result;
import com.bwei.iloginorregui.bean.ubean.UserInfo;
import com.bwei.iloginorregui.core.DataCall;
import com.bwei.iloginorregui.core.SpaceItemDecoration;
import com.bwei.iloginorregui.core.db.DaoMaster;
import com.bwei.iloginorregui.core.db.DaoSession;
import com.bwei.iloginorregui.core.db.UserInfoDao;
import com.bwei.iloginorregui.core.exception.ApiException;
import com.bwei.iloginorregui.presenter.FootPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FootprintActivity extends AppCompatActivity {
    @BindView(R.id.foot_recy)
    RecyclerView footRecy;
    private MyFootAdapter myFootAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_footprint);
        ButterKnife.bind(this);
        DaoSession daoSession = DaoMaster.newDevSession(this, UserInfoDao.TABLENAME);
        UserInfoDao userDao = daoSession.getUserInfoDao();
        List<UserInfo> list = userDao.loadAll();
        long userId = list.get(0).getUserId();
        String sessionId = list.get(0).getSessionId();
        new FootPresenter(new MyCall()).reqeust((int)userId,sessionId,1,10);
        myFootAdapter = new MyFootAdapter(this);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        footRecy.setLayoutManager(manager);
        footRecy.setAdapter(myFootAdapter);
        footRecy.addItemDecoration(new SpaceItemDecoration(2));

    }
    class MyCall implements DataCall<Result<List<FootBean>>> {



        @Override
        public void success(Result<List<FootBean>> result) {
            List<FootBean> result1 = result.getResult();
            myFootAdapter.addList(result1);
            myFootAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
