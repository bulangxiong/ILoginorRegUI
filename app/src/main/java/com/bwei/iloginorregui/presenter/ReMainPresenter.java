package com.bwei.iloginorregui.presenter;

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

public class ReMainPresenter extends BasePresenter {
    public ReMainPresenter(DataCall dataCall) {
        super(dataCall);
    }
    private UserInfoDao userInfoDao;
    private DaoSession daoSession;
    @Override
    protected Observable observable(Object... args) {
        ILogin iLogin = HttpUtils.getHttpUtils().create(ILogin.class);
        daoSession = DaoMaster.newDevSession(MyApp.getContext(), UserInfoDao.TABLENAME);
        userInfoDao = daoSession.getUserInfoDao();
        List<UserInfo> userInfos = userInfoDao.loadAll();
        UserInfo userInfo = userInfos.get(0);
        int userId = userInfo.getUserId();
        String sessionId = userInfo.getSessionId();
        return iLogin.AllOrder(userId,sessionId,3,1,5);
    }
}
