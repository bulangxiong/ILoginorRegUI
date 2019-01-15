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
import com.bwei.iloginorregui.adapter.d_adapter.OneEvaluateAdapter;
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

public class WaitPJFragment extends Fragment {

    private OneEvaluateAdapter oneEvaluateAdapter;
    private int userId;
    private String sessionId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.waitpj_fragment, container, false);

        ListPresenter listPresenter = new ListPresenter(new EvaluateCall());

        //数据库
        DaoSession daoSession = DaoMaster.newDevSession(getActivity(), UserInfoDao.TABLENAME);
//        获取操作数据库
        UserInfoDao userDao = daoSession.getUserInfoDao();
        List<UserInfo> list = userDao.loadAll();
        for (int i = 0; i < list.size(); i++) {
            userId = list.get(i).getUserId();
            sessionId = list.get(i).getSessionId();
        }

        RecyclerView recyclerView = view.findViewById(R.id.evaluateRecycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        //设置适配器
        oneEvaluateAdapter = new OneEvaluateAdapter(getActivity());
        //设置适配器
        recyclerView.setAdapter(oneEvaluateAdapter);

        //待评价
        listPresenter.reqeust( userId,sessionId,3,1,5);

        return view;
    }

    class EvaluateCall implements DataCall<Result> {

        @Override
        public void success(Result data) {
            if(data.getStatus().equals("0000"))
            {
                List<AllOrderUser> orderList = (List<AllOrderUser>) data.getOrderList();
                Toast.makeText(getActivity(), ""+orderList.size(), Toast.LENGTH_SHORT).show();
                //第一个添加数据
                oneEvaluateAdapter.addItem(orderList);

                oneEvaluateAdapter.notifyDataSetChanged();

            }
        }

        @Override
        public void fail(ApiException e) {

        }

    }

}
