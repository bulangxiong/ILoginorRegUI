package com.bwei.iloginorregui.presenter;

import com.bwei.iloginorregui.adapter.QAdapter;
import com.bwei.iloginorregui.app.MyApp;
import com.bwei.iloginorregui.bean.ubean.UserInfo;
import com.bwei.iloginorregui.core.DataCall;
import com.bwei.iloginorregui.core.ILogin;
import com.bwei.iloginorregui.core.db.DaoMaster;
import com.bwei.iloginorregui.core.db.DaoSession;
import com.bwei.iloginorregui.core.db.UserInfoDao;
import com.bwei.iloginorregui.http.HttpUtils;

import java.util.List;

import io.reactivex.Observable;

public class DianZanPresenter extends BasePresenter {
    public DianZanPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        ILogin iLogin = HttpUtils.getHttpUtils().create(ILogin.class);

        return iLogin.addCircleGreat((int) args[0], (String) args[1], (int) args[2]);
    }
}
