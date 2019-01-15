package com.bwei.iloginorregui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bwei.iloginorregui.R;
import com.bwei.iloginorregui.adapter.MyDetailAdapter;
import com.bwei.iloginorregui.bean.Result;
import com.bwei.iloginorregui.bean.sbean.DetaileGoods;
import com.bwei.iloginorregui.bean.sbean.ShoppingBean;
import com.bwei.iloginorregui.bean.ubean.UserInfo;
import com.bwei.iloginorregui.core.DataCall;
import com.bwei.iloginorregui.core.db.DaoMaster;
import com.bwei.iloginorregui.core.db.DaoSession;
import com.bwei.iloginorregui.core.db.ShoppingBeanDao;
import com.bwei.iloginorregui.core.db.UserInfoDao;
import com.bwei.iloginorregui.core.exception.ApiException;
import com.bwei.iloginorregui.presenter.DetailsPresenter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DetailsFragment extends Fragment {
    private static final String TAG = "DetailsFragment";
    private ListView listView;
    private Banner banner;
    private MyDetailAdapter myDetailAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_fragment, container, false);
        listView = view.findViewById(R.id.detail_list);
        banner = view.findViewById(R.id.detail_banner);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Intent intent = getActivity().getIntent();
        String id = intent.getStringExtra("id");
        DaoSession daoSession = DaoMaster.newDevSession(getContext(), UserInfoDao.TABLENAME);
        UserInfoDao userDao = daoSession.getUserInfoDao();
        List<UserInfo> list = userDao.loadAll();
        long userId = list.get(0).getUserId();
        String sessionId = list.get(0).getSessionId();
        new DetailsPresenter(new MyCall()).reqeust((int)userId,sessionId,id);
        myDetailAdapter = new MyDetailAdapter();
        listView.setAdapter(myDetailAdapter);
    }
    class MyCall implements DataCall<Result<DetaileGoods>> {


        @Override
        public void success(Result<DetaileGoods> result) {
            if(result.getStatus().equals("0000")){
                DetaileGoods result1 = result.getResult();
                myDetailAdapter.addList(result1);
                String picture = result1.getPicture();
                String[] split = picture.split(",");
                banner.setImageLoader(new MyBanner());
                banner.setImages(Arrays.asList(split));
                banner.setBannerStyle(BannerConfig.NUM_INDICATOR);
                banner.isAutoPlay(false);
                banner.start();
                myDetailAdapter.notifyDataSetChanged();
                ShoppingBean shoppingBean = new ShoppingBean(1,result1.getCommodityId(),result1.getCategoryName(),1,split[0],result1.getPrice(),true,1);
                DaoSession daoSession = DaoMaster.newDevSession(getContext(), UserInfoDao.TABLENAME);
                ShoppingBeanDao shoppingBeanDao = daoSession.getShoppingBeanDao();
                shoppingBeanDao.deleteAll();
                shoppingBeanDao.insertOrReplace(shoppingBean);
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
    class MyBanner extends ImageLoader{

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Uri uri = Uri.parse((String) path);
            imageView.setImageURI(uri);
        }

        @Override
        public ImageView createImageView(Context context) {
            SimpleDraweeView simpleDraweeView = new SimpleDraweeView(context);
            return simpleDraweeView;
        }
    }
}
