package com.bwei.iloginorregui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bwei.iloginorregui.R;
import com.bwei.iloginorregui.activity.MainActivity;
import com.bwei.iloginorregui.activity.my_page.FootprintActivity;
import com.bwei.iloginorregui.activity.my_page.My_ProfileActivity;
import com.bwei.iloginorregui.activity.my_page.My_Shipping_AddressActivity;
import com.bwei.iloginorregui.activity.my_page.WalletActivity;
import com.bwei.iloginorregui.bean.ubean.UserInfo;
import com.bwei.iloginorregui.core.db.DaoMaster;
import com.bwei.iloginorregui.core.db.DaoSession;
import com.bwei.iloginorregui.core.db.UserInfoDao;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FiveFragment extends Fragment {
    @BindView(R.id.me_bg)
    SimpleDraweeView meBg;
    @BindView(R.id.me_nickname)
    TextView meNickname;
    @BindView(R.id.m_ziliao)
    TextView mZiliao;
    @BindView(R.id.me_info)
    RelativeLayout meInfo;
    @BindView(R.id.m_quanzi)
    TextView mQuanzi;
    @BindView(R.id.me_circle)
    RelativeLayout meCircle;
    @BindView(R.id.m_zouji)
    TextView mZouji;
    @BindView(R.id.me_foot)
    RelativeLayout meFoot;
    @BindView(R.id.m_money)
    TextView mMoney;
    @BindView(R.id.me_wallet)
    RelativeLayout meWallet;
    @BindView(R.id.m_souhuodizhi)
    TextView mSouhuodizhi;
    @BindView(R.id.me_address)
    RelativeLayout meAddress;
    @BindView(R.id.line1)
    LinearLayout line1;
    @BindView(R.id.me_avatar)
    SimpleDraweeView meAvatar;
    Unbinder unbinder;
    @BindView(R.id.t_login)
    Button tLogin;
    private DaoSession daoSession;
    private UserInfoDao userInfoDao;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.five_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        //透明状态栏
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
//        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        daoSession = DaoMaster.newDevSession(getContext(), UserInfoDao.TABLENAME);
        userInfoDao = daoSession.getUserInfoDao();
        List<UserInfo> userInfos = userInfoDao.loadAll();
        UserInfo userInfo = userInfos.get(0);
        String nickName = userInfo.getNickName();
        String headPic = userInfo.getHeadPic();
        meNickname.setText(nickName);
        meAvatar.setImageURI(headPic);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.me_nickname, R.id.m_ziliao, R.id.m_quanzi, R.id.m_zouji, R.id.m_money, R.id.m_souhuodizhi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.me_nickname:
                break;
            case R.id.m_ziliao:
                Intent intent = new Intent(getActivity(), My_ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.m_quanzi:
                break;
            case R.id.m_zouji:
                Intent intent3=new Intent(getActivity(),FootprintActivity.class);
                startActivity(intent3);
                break;
            case R.id.m_money:
                Intent intent1=new Intent(getActivity(),WalletActivity.class);
                startActivity(intent1);
                break;
            case R.id.m_souhuodizhi:
                Intent intent2 = new Intent(getActivity(), My_Shipping_AddressActivity.class);
                startActivity(intent2);
                break;
        }
    }

    @OnClick(R.id.t_login)
    public void onViewClicked() {
        userInfoDao.deleteAll();
        Intent intent = new Intent(getActivity(), MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
                ;

        startActivity(intent);

    }
}
