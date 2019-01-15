package com.bwei.iloginorregui.fragment.indentfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.bwei.iloginorregui.R;
import com.bwei.iloginorregui.adapter.d_adapter.OneCompleteAdapter;
import com.bwei.iloginorregui.bean.Result;
import com.bwei.iloginorregui.bean.dbean.AllOrderUser;
import com.bwei.iloginorregui.bean.ubean.UserInfo;
import com.bwei.iloginorregui.core.DataCall;
import com.bwei.iloginorregui.core.db.DaoMaster;
import com.bwei.iloginorregui.core.db.DaoSession;
import com.bwei.iloginorregui.core.db.UserInfoDao;
import com.bwei.iloginorregui.core.exception.ApiException;
import com.bwei.iloginorregui.presenter.ListPresenter;

import java.util.List;

public class AlreadyFragment extends Fragment {

    private int userId;
    private String sessionId;
    private OneCompleteAdapter oneCompleteAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.already_fragment, container, false);

        ListPresenter listPresenter = new ListPresenter(new ComCall());
        //数据库
        //数据库
        DaoSession daoSession = DaoMaster.newDevSession(getActivity(), UserInfoDao.TABLENAME);
        //获取操作数据库
        UserInfoDao userDao = daoSession.getUserInfoDao();
        List<UserInfo> list = userDao.loadAll();
        userId = list.get(0).getUserId();
        sessionId = list.get(0).getSessionId();

        RecyclerView recyclerView = view.findViewById(R.id.ComRecycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        //设置适配器
        oneCompleteAdapter = new OneCompleteAdapter(getActivity());
        //设置适配器
        recyclerView.setAdapter(oneCompleteAdapter);

        //已完成
        listPresenter.reqeust(userId, sessionId, 9, 1, 5);


        return view;
    }

    class ComCall implements DataCall<Result> {


        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                List<AllOrderUser> orderList = (List<AllOrderUser>) result.getOrderList();
                //第一个添加数据
                oneCompleteAdapter.addItem(orderList);

                oneCompleteAdapter.notifyDataSetChanged();

            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

}
