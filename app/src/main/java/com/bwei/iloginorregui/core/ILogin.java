package com.bwei.iloginorregui.core;

import com.bwei.iloginorregui.bean.FootBean;
import com.bwei.iloginorregui.bean.GoodsDetailsBean;
import com.bwei.iloginorregui.bean.LabelListBean;
import com.bwei.iloginorregui.bean.Result;
import com.bwei.iloginorregui.bean.UserWallet;
import com.bwei.iloginorregui.bean.dbean.AllOrderBean;
import com.bwei.iloginorregui.bean.dbean.AllOrderUser;
import com.bwei.iloginorregui.bean.dbean.ByStatusBean;
import com.bwei.iloginorregui.bean.dbean.FindShoppingCartBean;
import com.bwei.iloginorregui.bean.dbean.FuBean;
import com.bwei.iloginorregui.bean.dbean.My_AddessBean;
import com.bwei.iloginorregui.bean.sbean.AddressBean;
import com.bwei.iloginorregui.bean.sbean.BannerLIst;
import com.bwei.iloginorregui.bean.sbean.CommdityBean;
import com.bwei.iloginorregui.bean.sbean.DetaileGoods;
import com.bwei.iloginorregui.bean.sbean.FindCircleList;
import com.bwei.iloginorregui.bean.sbean.FindCommodityListByLabel;
import com.bwei.iloginorregui.bean.sbean.ShoppingBean;
import com.bwei.iloginorregui.bean.ubean.UserInfo;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ILogin {
    //登录接口
    @FormUrlEncoded
    @POST("user/v1/login")
    Observable<Result<UserInfo>> loginRx(@Field("phone") String phone, @Field("pwd") String pwd);

    //注册接口
    @FormUrlEncoded
    @POST("user/v1/register")
    Observable<Result> regpx(@Field("phone") String mobile, @Field("pwd") String pwd);//注册接口

    //banner
    @GET("commodity/v1/bannerShow")
    Observable<Result<List<BannerLIst>>> bannerShow();


    //首頁
    @GET("commodity/v1/commodityList")
    Observable<Result<CommdityBean>> commodityList();

    //圈子
    @GET("circle/v1/findCircleList")
    Observable<Result<List<FindCircleList>>> findCircleList(
            @Query("page") int page,
            @Query("count") int count);

    //搜索
    @GET("commodity/v1/findCommodityByKeyword")
    Observable<Result<List<FindCommodityListByLabel>>> findCommodityByKeyword(
            @Query("keyword") String keyword,
            @Query("page") int page,
            @Query("count") int count
    );

    //我的地址
    @GET("user/verify/v1/receiveAddressList")
    Observable<Result<List<My_AddessBean>>> receiveAddressList(
            @Header("userId") int userId, @Header("sessionId") String sessionId);

    //购物车
    @GET("order/verify/v1/findShoppingCart")
    Observable<Result<List<FindShoppingCartBean>>> findShoppingCart(
            @Header("userId") int userId, @Header("sessionId") String sessionId);

    //    //查询订单
//    @GET("order/verify/v1/findOrderListByStatus")
//    Observable<Result<List<ByStatusBean>>> findOrderListByStatus(@Header("userId") int userId
//            , @Header("sessionId") String sessionId
//            , @Query("status") int status
//            , @Query("page") int page
//            , @Query("count") int count);
//详情页面展示
    @GET("commodity/v1/findCommodityDetailsById")
    Observable<Result<DetaileGoods>> details(@Header("userId") int userId,
                                             @Header("sessionId") String sessionId,
                                             @Query("commodityId") String commodityId);

    @GET("order/verify/v1/findShoppingCart")
    Observable<Result<List<ShoppingBean>>> findShopping(@Header("userId") int userId, @Header("sessionId") String sessionId);

    @GET("commodity1/findCommodityDetailsById")
    Observable<Result<GoodsDetailsBean>> detailsByIdGetData(
            @Header("userId") int userId,
            @Header("sessionId") String sessionId,
            @Query("commodityId") int commodityId);

    //订单
    @GET("commodity1/findCommodityDetailsById")
    Observable<Result<GoodsDetailsBean>> findOrderListByStatus1(
            @Header("userId") long userId,
            @Header("sessionId") String sessionId,
            @Query("commodityId") int commodityId);

    //查询全部订单
    @GET("order/verify/v1/findOrderListByStatus")
    Observable<Result<List<AllOrderUser<List<AllOrderBean>>>>> AllOrder(@Header("userId") int userId,
                                                                        @Header("sessionId") String sessionId,
                                                                        @Query("status") int status,
                                                                        @Query("page") int page,
                                                                        @Query("count") int count);

    @PUT("order/verify/v1/syncShoppingCart")
    @FormUrlEncoded
    Observable<Result> addTo(@Header("userId") int userId,
                             @Header("sessionId") String sessionId,
                             @Field("data") String data);

    @GET("user/verify/v1/findUserWallet")
    Observable<Result<UserWallet>> wallet(@Header("userId") int userId,
                                          @Header("sessionId") String sessionId,
                                          @Query("page") int page,
                                          @Query("count") int count);

    @GET("commodity/verify/v1/browseList")
    Observable<Result<List<FootBean>>> foot(@Header("userId") int userId,
                                            @Header("sessionId") String sessionId,
                                            @Query("page") int page,
                                            @Query("count") int count);
    //
    @GET("user/verify/v1/receiveAddressList")
    Observable<Result<List<AddressBean>>> addresslist(@Header("userId") int userId, @Header("sessionId") String sessionId);
    //点赞
    @FormUrlEncoded
    @POST("circle/verify/v1/addCircleGreat")
    Observable<Result<List<FindCircleList>>> addCircleGreat(@Header("userId") int userId,
                                                            @Header("sessionId") String sessionId,
                                                            @Field("circleId") int circleId);
     //添加地址
    @FormUrlEncoded
    @POST("user/verify/v1/addReceiveAddress")
    Observable<Result> addAddrss(@Header("userId") int userId,
                                 @Header("sessionId") String sessionId,
                                 @Field("realName") String realName,
                                 @Field("phone") String phone,
                                 @Field("address") String address,
                                 @Field("zipCode") String zipCode);
}
