package com.bwei.iloginorregui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.bwei.iloginorregui.R;
import com.bwei.iloginorregui.bean.Result;
import com.bwei.iloginorregui.bean.ubean.UserInfo;
import com.bwei.iloginorregui.core.DataCall;
import com.bwei.iloginorregui.core.db.DaoMaster;
import com.bwei.iloginorregui.core.db.DaoSession;
import com.bwei.iloginorregui.core.db.UserInfoDao;
import com.bwei.iloginorregui.core.exception.ApiException;
import com.bwei.iloginorregui.presenter.LoginPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.modile)
    EditText modile;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.jizhumima)
    CheckBox jizhumima;
    @BindView(R.id.jiami)
    ImageView jiami;
    private boolean issJ = false;
    LoginPresenter loginPresenter;
    private UserInfoDao userInfoDao;
    private DaoSession daoSession;
    private UserInfo result;
    private boolean pasVisibile = false;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        loginPresenter = new LoginPresenter(new RegiestCall());
        daoSession = DaoMaster.newDevSession(this, UserInfoDao.TABLENAME);
        userInfoDao = daoSession.getUserInfoDao();
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemenber = pref.getBoolean("remember_password", false);
        if (isRemenber) { //将账号和密码都设置到文本中
            String account = pref.getString("account", "");
            String password1 = pref.getString("password", "");
            modile.setText(account);
            password.setText(password1);
            jizhumima.setChecked(true);
        }

    }

    @OnClick(R.id.btn_login)
    public void loginRx() {

        loginPresenter.reqeust(modile.getText().toString(), password.getText().toString());


    }

    @OnClick(R.id.registration)
    public void getMM() {
        Intent intent = new Intent(MainActivity.this, RegActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.jiami)
    public void onViewClicked() {
        if (pasVisibile){//密码显示，则隐藏
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            pasVisibile = false;
        }else{//密码隐藏则显示
            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            pasVisibile = true;
        }
    }


    class RegiestCall implements DataCall<Result<UserInfo>> {


        @Override
        public void success(Result<UserInfo> data) {
            result = data.getResult();

            if (data.getStatus().equals("0000")) {
                result.setStatus(1);
                UserInfoDao userInfoDao = DaoMaster.newDevSession(getBaseContext(), UserInfoDao.TABLENAME).getUserInfoDao();
                userInfoDao.deleteAll();
                userInfoDao.insertOrReplace(result);
                String account = modile.getText().toString();
                String password1 = password.getText().toString();
                editor = pref.edit();
                if (jizhumima.isChecked()) {
                    editor.putBoolean("remember_password", true);
                    editor.putString("account", account);
                    editor.putString("password", password1);

                } else {
                    editor.clear();
                }
                editor.commit();
                Intent intent = new Intent(MainActivity.this, ShoppingActivity.class);
                startActivity(intent);
                finish();
            }


        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
