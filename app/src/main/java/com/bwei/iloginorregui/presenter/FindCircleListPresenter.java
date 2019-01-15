package com.bwei.iloginorregui.presenter;

import com.bwei.iloginorregui.core.DataCall;
import com.bwei.iloginorregui.core.ILogin;
import com.bwei.iloginorregui.http.HttpUtils;

import io.reactivex.Observable;

public class FindCircleListPresenter extends BasePresenter {
    public FindCircleListPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        ILogin iLogin = HttpUtils.getHttpUtils().create(ILogin.class);
        return iLogin.findCircleList((int)args[0],(int)args[1]);
    }
}
