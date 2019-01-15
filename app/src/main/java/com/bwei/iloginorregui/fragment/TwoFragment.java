package com.bwei.iloginorregui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bwei.iloginorregui.R;
import com.bwei.iloginorregui.adapter.QAdapter;
import com.bwei.iloginorregui.app.MyApp;
import com.bwei.iloginorregui.bean.Result;
import com.bwei.iloginorregui.bean.sbean.FindCircleList;
import com.bwei.iloginorregui.bean.ubean.UserInfo;
import com.bwei.iloginorregui.core.DataCall;
import com.bwei.iloginorregui.core.db.DaoMaster;
import com.bwei.iloginorregui.core.db.DaoSession;
import com.bwei.iloginorregui.core.db.UserInfoDao;
import com.bwei.iloginorregui.core.exception.ApiException;
import com.bwei.iloginorregui.presenter.DianZanPresenter;
import com.bwei.iloginorregui.presenter.FindCircleListPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TwoFragment extends Fragment {
    @BindView(R.id.quan_recycler_view)
    RecyclerView quanRecyclerView;
    Unbinder unbinder;
    private FindCircleListPresenter findCircleListPresenter;
    private QAdapter qAdapter;
    private UserInfoDao userInfoDao;
    private DaoSession daoSession;
    private DianZanPresenter dianZanPresenter;
    private int userId;
    private String sessionId;
    private int id;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.two_fragment, null);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        daoSession = DaoMaster.newDevSession(MyApp.getContext(), UserInfoDao.TABLENAME);
        userInfoDao = daoSession.getUserInfoDao();
        List<UserInfo> userInfos = userInfoDao.loadAll();
        UserInfo userInfo = userInfos.get(0);
        userId = userInfo.getUserId();
        sessionId = userInfo.getSessionId();
        quanRecyclerView.setAdapter(qAdapter);
        return view;
    }

    private void initData() {
        findCircleListPresenter.reqeust(1,50);
        dianZanPresenter = new DianZanPresenter(new FindCirc());
    }

    private void initView() {
        findCircleListPresenter= new FindCircleListPresenter(new FindCirc());
        qAdapter = new QAdapter(getContext());
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        quanRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

      class FindCirc implements DataCall<Result<List<FindCircleList>>> {
        @Override
        public void success(Result<List<FindCircleList>> data) {
            Log.e("qwer","====="+data.getMessage());
            List<FindCircleList> list = data.getResult();
            qAdapter.addItem(list);
            qAdapter.setOnItemClickListener(new QAdapter.OnItemClickListener() {


                @Override
                public void onItemClick(int num,int id) {

                    dianZanPresenter.reqeust(userId,sessionId,id);
                    qAdapter.notifyDataSetChanged();
                    qAdapter.notifyItemChanged(id);
                }
            });
            qAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        dianZanPresenter.reqeust(userId,sessionId,id);
    }
}
