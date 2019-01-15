package com.bwei.iloginorregui.fragment.details;

import android.content.Intent;
import android.net.Uri;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bwei.iloginorregui.R;
import com.bwei.iloginorregui.bean.GoodsDetailsBean;
import com.bwei.iloginorregui.bean.Result;
import com.bwei.iloginorregui.bean.ubean.UserInfo;
import com.bwei.iloginorregui.core.DataCall;
import com.bwei.iloginorregui.core.db.DaoMaster;
import com.bwei.iloginorregui.core.db.DaoSession;
import com.bwei.iloginorregui.core.db.UserInfoDao;
import com.bwei.iloginorregui.core.exception.ApiException;
import com.bwei.iloginorregui.presenter.DetailsGoodsPresenter;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class GoodIntroduceFragment extends Fragment {

    @BindView(R.id.details_goods_itroduce_img)
    SimpleDraweeView mDetailsGoodsItroduceImg;
    @BindView(R.id.details_goods_itroduce_price)
    TextView mDetailsGoodsItroducePrice;
    @BindView(R.id.details_goods_itroduce_title)
    TextView mDetailsGoodsItroduceTitle;
    @BindView(R.id.details_goods_itroduce_kg)
    TextView mDetailsGoodsItroduceKg;
    private View view;
    private Unbinder unbinder;
    private UserInfoDao userInfoDao;
    private DaoSession daoSession;
    private DetailsGoodsPresenter detailsGoodsPresenter = new DetailsGoodsPresenter(new DetailsGoodsCall());
    private String sessionId;
    private int userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //首先查询个人信息
        daoSession = DaoMaster.newDevSession(getContext(), UserInfoDao.TABLENAME);
        userInfoDao = daoSession.getUserInfoDao();
        List<UserInfo> userInfos = userInfoDao.loadAll();
        UserInfo userInfo1 = userInfos.get(0);
        userId = userInfo1.getUserId();
        sessionId = userInfo1.getSessionId();
        View view = inflater.inflate(R.layout.one_item_xiang, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();

        return view;
    }

    private void initData() {
        //得到从Activity传来的数据
        Intent intent = getActivity().getIntent();
        int cid1 = intent.getIntExtra("cid", getId());
//        Bundle bundle = this.getArguments();
//        int cid = bundle.getInt("cid");
        detailsGoodsPresenter.reqeust(userId, sessionId, cid1);


    }

    class DetailsGoodsCall implements DataCall<Result<GoodsDetailsBean>> {

        @Override
        public void success(Result<GoodsDetailsBean> data) {
            if (data.getStatus().equals("0000")) {
                GoodsDetailsBean goodsDetailsBean = data.getResult();
                Toast.makeText(getContext(), goodsDetailsBean.toString(), Toast.LENGTH_SHORT).show();
//                mDetailsGoodsItroduceImg.setImageURI(Uri.parse(goodsDetailsBean.getPicture()));
                mDetailsGoodsItroduceTitle.setText(goodsDetailsBean.getCommodityName());
                mDetailsGoodsItroducePrice.setText("￥ " + goodsDetailsBean.getPrice());
                mDetailsGoodsItroduceKg.setText(goodsDetailsBean.getWeight() + " kg");
            } else {
                Toast.makeText(getContext(), "获取数据有误", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
