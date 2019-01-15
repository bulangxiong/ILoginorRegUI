package com.bwei.iloginorregui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bwei.iloginorregui.R;
import com.bwei.iloginorregui.bean.ubean.UserInfo;
import com.bwei.iloginorregui.core.db.DaoMaster;
import com.bwei.iloginorregui.core.db.DaoSession;
import com.bwei.iloginorregui.core.db.UserInfoDao;

import java.util.List;

public class YinDaoActivity extends AppCompatActivity {

    private TextView tiaozhuan1;
    private UserInfoDao userInfoDao;
    private DaoSession daoSession;
    private int tiem = 3;
    private int status;
    private List<UserInfo> userInfos;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            tiem--;
            tiaozhuan1.setText("跳转" + tiem + "s");
            if (tiem == 0) {
                if (userInfos != null&&userInfos.size()>0) {

                    Intent intent = new Intent(YinDaoActivity.this, ShoppingActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(YinDaoActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            } else {

                mHandler.sendEmptyMessageDelayed(1, 1000);

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yin_dao);
        tiaozhuan1 = findViewById(R.id.tiaozhuan1);
        daoSession = DaoMaster.newDevSession(this, UserInfoDao.TABLENAME);
        userInfoDao = daoSession.getUserInfoDao();
        userInfos = userInfoDao.loadAll();


        mHandler.sendEmptyMessageDelayed(1, 1000);
        tiaozhuan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.removeMessages(1);

                if (userInfos != null) {

                    Intent intent = new Intent(YinDaoActivity.this, ShoppingActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(YinDaoActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }


                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(mHandler);

    }
}
