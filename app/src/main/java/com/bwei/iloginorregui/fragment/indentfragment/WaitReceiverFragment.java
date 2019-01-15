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
import android.widget.Toast;


import com.bwei.iloginorregui.R;
import com.bwei.iloginorregui.adapter.d_adapter.OneReceivedAdapter;
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

public class WaitReceiverFragment extends Fragment {

    private int userId;
    private String sessionId;
    private RecyclerView recyclerView;
    private OneReceivedAdapter oneReceivedAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.waitreceiver_fragment, container, false);

        ListPresenter listPresenter = new ListPresenter(new ReceiCall());

        //数据库
        DaoSession daoSession = DaoMaster.newDevSession(getActivity(), UserInfoDao.TABLENAME);
        //获取操作数据库
        UserInfoDao userDao = daoSession.getUserInfoDao();
        List<UserInfo> list = userDao.loadAll();
        for (int i = 0; i < list.size(); i++) {
            userId = list.get(i).getUserId();
            sessionId = list.get(i).getSessionId();
        }

        recyclerView = view.findViewById(R.id.receivedRecycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        //设置适配器
        oneReceivedAdapter = new OneReceivedAdapter(getActivity());
        //设置适配器
        recyclerView.setAdapter(oneReceivedAdapter);
        //待收货
        listPresenter.reqeust(userId,sessionId,2,1,5);


        return view;
    }

    class ReceiCall implements DataCall<Result> {



        @Override
        public void success(Result result) {
            if(result.getStatus().equals("0000"))
            {
                List<AllOrderUser> orderList = (List<AllOrderUser>) result.getOrderList();

                Toast.makeText(getActivity(), ""+orderList.size(), Toast.LENGTH_SHORT).show();
                //第一个添加数据
                oneReceivedAdapter.addItem(orderList);

                oneReceivedAdapter.notifyDataSetChanged();

            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

}
