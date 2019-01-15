package com.bwei.iloginorregui.presenter;

import com.bwei.iloginorregui.core.DataCall;
import com.bwei.iloginorregui.core.ILogin;
import com.bwei.iloginorregui.http.HttpUtils;

import io.reactivex.Observable;

public class SeekPresenter extends BasePresenter {
    public SeekPresenter(DataCall dataCall) {
        super(dataCall);
    }
//    private int page = 1;
//    private boolean refresh = false;
    @Override
    protected Observable observable(Object... args) {
        ILogin iLogin = HttpUtils.getHttpUtils().create(ILogin.class);
//        refresh = (boolean) args[0];
//        if (refresh) {
//            page = 1;
//        } else {
//            page++;
//        }
        return iLogin.findCommodityByKeyword((String)args[0],(int)args[1],(int)args[2]);
    }
}
