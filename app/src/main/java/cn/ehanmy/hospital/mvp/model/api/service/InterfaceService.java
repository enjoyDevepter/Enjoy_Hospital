package cn.ehanmy.hospital.mvp.model.api.service;

import io.reactivex.Observable;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderListRequest;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderListResponse;
import cn.ehanmy.hospital.mvp.model.entity.request.GoodsBuyRequest;
import cn.ehanmy.hospital.mvp.model.entity.request.GoodsConfirmRequest;
import cn.ehanmy.hospital.mvp.model.entity.request.GoodsPageRequest;
import cn.ehanmy.hospital.mvp.model.entity.request.HospitalInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.request.LoginRequest;
import cn.ehanmy.hospital.mvp.model.entity.request.MakeSureRequest;
import cn.ehanmy.hospital.mvp.model.entity.request.MemberInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.response.GoodsBuyResponse;
import cn.ehanmy.hospital.mvp.model.entity.response.GoodsConfirmResponse;
import cn.ehanmy.hospital.mvp.model.entity.response.GoodsPageResponse;
import cn.ehanmy.hospital.mvp.model.entity.response.HospitalInfoResponse;
import cn.ehanmy.hospital.mvp.model.entity.response.LoginResponse;
import cn.ehanmy.hospital.mvp.model.entity.response.MakeSureResponse;
import cn.ehanmy.hospital.mvp.model.entity.response.MemberInfoResponse;
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

    // 订单列表相关接口
    @POST("gateway")
    Observable<OrderListResponse> requestOrderListPage(@Body OrderListRequest request);
}