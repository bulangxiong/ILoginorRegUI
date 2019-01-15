package com.bwei.iloginorregui.activity.my_page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwei.iloginorregui.R;
import com.bwei.iloginorregui.bean.ubean.UserInfo;
import com.bwei.iloginorregui.core.db.DaoMaster;
import com.bwei.iloginorregui.core.db.DaoSession;
import com.bwei.iloginorregui.core.db.UserInfoDao;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class My_ProfileActivity extends AppCompatActivity {
    @BindView(R.id.my_image)
    SimpleDraweeView myImage;
    @BindView(R.id.my_phone)
    TextView myPhone;
    @BindView(R.id.my_pwd)
    TextView myPwd;
    private DaoSession daoSession;
    private UserInfoDao userInfoDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile_item);
        ButterKnife.bind(this);
        daoSession = DaoMaster.newDevSession(this, UserInfoDao.TABLENAME);
        userInfoDao = daoSession.getUserInfoDao();
        List<UserInfo> userInfos = userInfoDao.loadAll();
        UserInfo userInfo = userInfos.get(0);

        myImage.setImageURI(userInfo.getHeadPic());
        myPhone.setText(userInfo.getNickName());
        myPwd.setText(userInfo.getPhone());
    }
}
