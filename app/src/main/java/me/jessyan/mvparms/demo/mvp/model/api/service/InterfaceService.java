package me.jessyan.mvparms.demo.mvp.model.api.service;

import io.reactivex.Observable;
import me.jessyan.mvparms.demo.mvp.model.entity.request.GoodsBuyRequest;
import me.jessyan.mvparms.demo.mvp.model.entity.request.GoodsConfirmRequest;
import me.jessyan.mvparms.demo.mvp.model.entity.request.GoodsPageRequest;
import me.jessyan.mvparms.demo.mvp.model.entity.request.HospitalInfoRequest;
import me.jessyan.mvparms.demo.mvp.model.entity.request.LoginRequest;
import me.jessyan.mvparms.demo.mvp.model.entity.request.MakeSureRequest;
import me.jessyan.mvparms.demo.mvp.model.entity.request.MemberInfoRequest;
import me.jessyan.mvparms.demo.mvp.model.entity.response.GoodsBuyResponse;
import me.jessyan.mvparms.demo.mvp.model.entity.response.GoodsConfirmResponse;
import me.jessyan.mvparms.demo.mvp.model.entity.response.GoodsPageResponse;
import me.jessyan.mvparms.demo.mvp.model.entity.response.HospitalInfoResponse;
import me.jessyan.mvparms.demo.mvp.model.entity.response.LoginResponse;
import me.jessyan.mvparms.demo.mvp.model.entity.response.MakeSureResponse;
import me.jessyan.mvparms.demo.mvp.model.entity.response.MemberInfoResponse;
import retrofit2.http.Body;
import retrofit2.http.POST;


/**
 * 服务器接口类
 */
public interface InterfaceService {

    @POST("gateway")
    Observable<LoginResponse> login(@Body LoginRequest request);

    @POST("gateway")
    Observable<HospitalInfoResponse> requestHosptialInfo(@Body HospitalInfoRequest request);

    @POST("gateway")
    Observable<MemberInfoResponse> requestMemberInfo(@Body MemberInfoRequest request);

    @POST("gateway")
    Observable<GoodsPageResponse> requestGoodsInfo(@Body GoodsPageRequest request);

    @POST("gateway")
    Observable<GoodsConfirmResponse> confirmGoods(@Body GoodsConfirmRequest request);

    @POST("gateway")
    Observable<GoodsBuyResponse> buyGoods(@Body GoodsBuyRequest request);

    @POST("gateway")
    Observable<MakeSureResponse> makeSureOrder(@Body MakeSureRequest request);
}
