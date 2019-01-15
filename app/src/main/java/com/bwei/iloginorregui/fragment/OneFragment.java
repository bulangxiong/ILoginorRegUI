package com.bwei.iloginorregui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bwei.iloginorregui.R;
import com.bwei.iloginorregui.activity.SeekActivity;
import com.bwei.iloginorregui.activity.XQActivity;
import com.bwei.iloginorregui.adapter.HotMyAdapter;
import com.bwei.iloginorregui.adapter.MyAdapter2;
import com.bwei.iloginorregui.adapter.MyAdapter3;
import com.bwei.iloginorregui.bean.Result;
import com.bwei.iloginorregui.bean.sbean.BannerLIst;
import com.bwei.iloginorregui.bean.sbean.CommdityBean;
import com.bwei.iloginorregui.core.DataCall;
import com.bwei.iloginorregui.core.exception.ApiException;
import com.bwei.iloginorregui.presenter.BannerPresenter;
import com.bwei.iloginorregui.presenter.CommodityPresenter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

//import com.bwei.iloginorregui.presenter.BannerPresenter;

public class OneFragment extends Fragment {
    @BindView(R.id.sangang)
    ImageView sangang;
    @BindView(R.id.soushuokuang)
    EditText soushuokuang;
    @BindView(R.id.soushuo)
    TextView soushuo;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.recycel_heng)
    RecyclerView recycelHeng;
    @BindView(R.id.recycel_shu)
    RecyclerView recycelShu;
    @BindView(R.id.recycel_shu1)
    RecyclerView recycelShu1;
    Unbinder unbinder;
    private HotMyAdapter hotMyAdapter;
    CommodityPresenter commodityPresenter = new CommodityPresenter(new ShopCall());
    BannerPresenter bannerPresenter = new BannerPresenter(new MyCall());
    private MyAdapter2 adapter2;
    private MyAdapter3 adapter3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.one_fragment, null);
        unbinder = ButterKnife.bind(this, view);
        adapter2 = new MyAdapter2();
        adapter3 = new MyAdapter3();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        hotMyAdapter = new HotMyAdapter(getContext());
        commodityPresenter.reqeust();
        bannerPresenter.reqeust();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.sangang, R.id.soushuo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sangang:
                break;
            case R.id.soushuo:
                break;
        }
    }

    @OnClick(R.id.soushuokuang)
    public void onViewClicked() {
        Intent intent = new Intent(getContext(), SeekActivity.class);
        startActivity(intent);
    }

    //轮播图
    class MyCall implements DataCall<Result<List<BannerLIst>>> {


        @Override
        public void success(Result<List<BannerLIst>> data) {
            List<String> list = new ArrayList<>();
            List<BannerLIst> result = data.getResult();
            for (int i = 0; i < result.size(); i++) {
                list.add(result.get(i).getImageUrl());
            }
            banner.setImages(list);
            banner.setImageLoader(new MyBanner());
            banner.start();
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    //首页商品
    class ShopCall implements DataCall<Result<CommdityBean>> {

        @Override
        public void success(Result<CommdityBean> data) {
            List<CommdityBean.RxxpBean> rxxp = data.getResult().getRxxp();
            List<CommdityBean.RxxpBean.CommodityListBean> commodityList = rxxp.get(0).getCommodityList();
            List<CommdityBean.MlssBean> mlss = data.getResult().getMlss();
            List<CommdityBean.MlssBean.CommodityListBeanXX> commodityList1 = mlss.get(0).getCommodityList();
            List<CommdityBean.PzshBean> pzsh = data.getResult().getPzsh();
            List<CommdityBean.PzshBean.CommodityListBeanX> commodityList2 = pzsh.get(0).getCommodityList();


            hotMyAdapter.addItem(commodityList);
            adapter2.rxaddList(commodityList1);
            adapter3.rxaddList(commodityList2);

            StaggeredGridLayoutManager rx = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
            StaggeredGridLayoutManager ml = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
            StaggeredGridLayoutManager pz = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

            recycelHeng.setLayoutManager(rx);
            recycelHeng.setAdapter(hotMyAdapter);
            recycelShu.setLayoutManager(ml);
            recycelShu.setAdapter(adapter2);
            recycelShu1.setLayoutManager(pz);
            recycelShu1.setAdapter(adapter3);
            hotMyAdapter.setRxxpp(new HotMyAdapter.Rxxp() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(getActivity(), XQActivity.class);
                    intent.putExtra("id", position + "");
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.addToBackStack(null);
                    startActivity(intent);
                }
            });
            adapter2.setMlss(new MyAdapter2.Mlss() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(getActivity(), XQActivity.class);
                    intent.putExtra("id", position + "");
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.addToBackStack(null);
                    startActivity(intent);
                }
            });
            adapter3.setPzsh(new MyAdapter3.Pzsh() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(getActivity(), XQActivity.class);
                    intent.putExtra("id", position + "");
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.addToBackStack(null);
                    startActivity(intent);
                }
            });
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    //轮播图
    class MyBanner extends ImageLoader {


        @Override
        public ImageView createImageView(Context context) {
            SimpleDraweeView simpleDraweeView = (SimpleDraweeView) View.inflate(getContext(), R.layout.banner_layout, null);
            return simpleDraweeView;

        }

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Uri parse = Uri.parse((String) path);
            imageView.setImageURI(parse);
        }
    }
}