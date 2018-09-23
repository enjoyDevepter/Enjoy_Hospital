package cn.ehanmy.hospital.mvp.model.api.service;

import cn.ehanmy.hospital.mvp.model.entity.goods_list.CategoryRequest;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.CategoryResponse;
import cn.ehanmy.hospital.mvp.model.entity.hospital.ChangeHospitalImageRequest;
import cn.ehanmy.hospital.mvp.model.entity.hospital.ChangeHospitalImageResponse;
import cn.ehanmy.hospital.mvp.model.entity.hospital.ChangeHospitalInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.hospital.ChangeHospitalInfoResponse;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderInfoResponse;
import cn.ehanmy.hospital.mvp.model.entity.request.GoodsConfirmWithSpecRequest;
import cn.ehanmy.hospital.mvp.model.entity.response.BaseResponse;
import cn.ehanmy.hospital.mvp.model.entity.user.ChangePasswordRequest;
import cn.ehanmy.hospital.mvp.model.entity.user.ChangePasswordResponse;
import cn.ehanmy.hospital.mvp.model.entity.user.ProjectSettingRequest;
import cn.ehanmy.hospital.mvp.model.entity.user.ProjectSettingResponse;
import cn.ehanmy.hospital.mvp.model.entity.user.SettingProjectRequest;
import cn.ehanmy.hospital.mvp.model.entity.user.SettingProjectResponse;
import io.reactivex.Observable;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderListRequest;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderListResponse;
import cn.ehanmy.hospital.mvp.model.entity.request.GoodsBuyRequest;
import cn.ehanmy.hospital.mvp.model.entity.request.GoodsConfirmRequest;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.GoodsPageRequest;
import cn.ehanmy.hospital.mvp.model.entity.hospital.HospitalInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.request.LoginRequest;
import cn.ehanmy.hospital.mvp.model.entity.request.MakeSureRequest;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.response.GoodsBuyResponse;
import cn.ehanmy.hospital.mvp.model.entity.response.GoodsConfirmResponse;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.GoodsPageResponse;
import cn.ehanmy.hospital.mvp.model.entity.hospital.HospitalInfoResponse;
import cn.ehanmy.hospital.mvp.model.entity.response.LoginResponse;
import cn.ehanmy.hospital.mvp.model.entity.response.MakeSureResponse;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberInfoResponse;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


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
    Observable<GoodsConfirmResponse> confirmGoodsWithSpec(@Body GoodsConfirmWithSpecRequest request);

    @POST("gateway")
    Observable<GoodsBuyResponse> buyGoods(@Body GoodsBuyRequest request);

    @POST("gateway")
    Observable<MakeSureResponse> makeSureOrder(@Body MakeSureRequest request);

    // 订单列表相关接口
    @POST("gateway")
    Observable<OrderListResponse> requestOrderListPage(@Body OrderListRequest request);  // 请求订单列表

    @POST("gateway")
    Observable<OrderInfoResponse> orderInfo(@Body OrderInfoRequest request);  // 请求订单详情

    @POST("gateway")
    Observable<CategoryResponse> getCategory(@Body CategoryRequest request);  // 获取分类信息

    @POST("gateway")
    // 修改密码
    Observable<ChangePasswordResponse> changePassword(@Body ChangePasswordRequest request);

    @POST("gateway")
    // 获取用户设置
    Observable<ProjectSettingResponse> getProjectSetting(@Body ProjectSettingRequest request);

    @POST("gateway")
    // 获取用户设置
    Observable<SettingProjectResponse> setProjectSetting(@Body SettingProjectRequest request);
    @POST("gateway")

    // 修改医院信息设置
    Observable<ChangeHospitalInfoResponse> changeHospitalInfo(@Body ChangeHospitalInfoRequest request);


    // 修改医院照片
    Observable<ChangeHospitalImageResponse> changeHospitalImage(@Body ChangeHospitalImageRequest request);


    @Multipart
    @POST("file/imageUpload")
    Observable<BaseResponse> uploadImage(@Part("type") String description, @Part MultipartBody.Part file);
}
