package com.bwei.iloginorregui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bwei.iloginorregui.R;
import com.bwei.iloginorregui.adapter.GAdapter;
import com.bwei.iloginorregui.bean.Result;
import com.bwei.iloginorregui.bean.dbean.FindShoppingCartBean;
import com.bwei.iloginorregui.bean.ubean.UserInfo;
import com.bwei.iloginorregui.core.DataCall;
import com.bwei.iloginorregui.core.db.DaoMaster;
import com.bwei.iloginorregui.core.db.DaoSession;
import com.bwei.iloginorregui.core.db.UserInfoDao;
import com.bwei.iloginorregui.core.exception.ApiException;
import com.bwei.iloginorregui.presenter.GouPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ThreeFragment extends Fragment {
    @BindView(R.id.gou_recycel_view)
    RecyclerView gouRecycelView;
    @BindView(R.id.gou_check)
    CheckBox gouCheck;
    @BindView(R.id.gou_allmoney)
    TextView gouAllmoney;
    Unbinder unbinder;
    private GAdapter adapter;
    private GouPresenter gouPresenter;
    private UserInfoDao userInfoDao;
    private DaoSession daoSession;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.three_fragment, null);
        unbinder = ButterKnife.bind(this, view);
        gouPresenter = new GouPresenter(new ShoppingCart());
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        gouRecycelView.setLayoutManager(layoutManager);
        adapter = new GAdapter(getContext());
        gouRecycelView.setAdapter(adapter);
        initData();
        return view;
    }

    private void initData() {
        daoSession = DaoMaster.newDevSession(getContext(), UserInfoDao.TABLENAME);
        userInfoDao = daoSession.getUserInfoDao();
        List<UserInfo> userInfos = userInfoDao.loadAll();
        UserInfo userInfo1 = userInfos.get(0);
        String sessionId1 = userInfo1.getSessionId();
        int userId = userInfo1.getUserId();
        gouPresenter.reqeust(userId,sessionId1);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.gou_check, R.id.gou_allmoney})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.gou_check:
                break;
            case R.id.gou_allmoney:
                break;
        }
    }

    private class ShoppingCart implements DataCall<Result<List<FindShoppingCartBean>>> {
        @Override
        public void success(Result<List<FindShoppingCartBean>> result) {
            List<FindShoppingCartBean> beanList = result.getResult();
            adapter.addItem(beanList);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
