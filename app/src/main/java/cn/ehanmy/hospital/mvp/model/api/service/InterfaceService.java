package cn.ehanmy.hospital.mvp.model.api.service;

import cn.ehanmy.hospital.mvp.model.entity.QiniuRequest;
import cn.ehanmy.hospital.mvp.model.entity.QiniuResponse;
import cn.ehanmy.hospital.mvp.model.entity.UpdateRequest;
import cn.ehanmy.hospital.mvp.model.entity.UpdateResponse;
import cn.ehanmy.hospital.mvp.model.entity.activity.AddActivityRequest;
import cn.ehanmy.hospital.mvp.model.entity.activity.AddActivityResponse;
import cn.ehanmy.hospital.mvp.model.entity.activity.ChangeActivityInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.activity.ChangeActivityInfoResponse;
import cn.ehanmy.hospital.mvp.model.entity.activity.DeleteActivityInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.activity.DeleteActivityInfoResponse;
import cn.ehanmy.hospital.mvp.model.entity.activity.GetActivityInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.activity.GetActivityInfoResponse;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.CategoryRequest;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.CategoryResponse;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.GoodsPageRequest;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.GoodsPageResponse;
import cn.ehanmy.hospital.mvp.model.entity.hospital.ChangeHospitalImageRequest;
import cn.ehanmy.hospital.mvp.model.entity.hospital.ChangeHospitalImageResponse;
import cn.ehanmy.hospital.mvp.model.entity.hospital.ChangeHospitalInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.hospital.ChangeHospitalInfoResponse;
import cn.ehanmy.hospital.mvp.model.entity.hospital.HospitalInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.hospital.HospitalInfoResponse;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberInfoByIdRequest;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberInfoByIdResponse;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberInfoResponse;
import cn.ehanmy.hospital.mvp.model.entity.order.GetOrderAppointmentTimeRequest;
import cn.ehanmy.hospital.mvp.model.entity.order.GetOrderAppointmentTimeResponse;
import cn.ehanmy.hospital.mvp.model.entity.order.GetPayStatusRequest;
import cn.ehanmy.hospital.mvp.model.entity.order.GetPayStatusResponse;
import cn.ehanmy.hospital.mvp.model.entity.order.GoPayRequest;
import cn.ehanmy.hospital.mvp.model.entity.order.GoPayResponse;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderInfoResponse;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderListRequest;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderListResponse;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderPayRequest;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderPayResponse;
import cn.ehanmy.hospital.mvp.model.entity.placeOrder.GoodsBuyRequest;
import cn.ehanmy.hospital.mvp.model.entity.placeOrder.GoodsBuyResponse;
import cn.ehanmy.hospital.mvp.model.entity.request.GoodsConfirmRequest;
import cn.ehanmy.hospital.mvp.model.entity.request.GoodsConfirmWithSpecRequest;
import cn.ehanmy.hospital.mvp.model.entity.request.LoginRequest;
import cn.ehanmy.hospital.mvp.model.entity.response.GoodsConfirmResponse;
import cn.ehanmy.hospital.mvp.model.entity.response.LoginResponse;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.CancelShopAppointmentRequest;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.CancelShopAppointmentResponse;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.ConfirmShopAppointmentRequest;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.ConfirmShopAppointmentResponse;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.GetRelatedListRequest;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.GetRelatedListResponse;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.GetShopAppointmentPageRequest;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.GetShopAppointmentPageResponse;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.ShopAppointmentInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.ShopAppointmentInfoResponse;
import cn.ehanmy.hospital.mvp.model.entity.user.ChangePasswordRequest;
import cn.ehanmy.hospital.mvp.model.entity.user.ChangePasswordResponse;
import cn.ehanmy.hospital.mvp.model.entity.user.GetCategoryGoodsListRequest;
import cn.ehanmy.hospital.mvp.model.entity.user.GetCategoryGoodsListResponse;
import cn.ehanmy.hospital.mvp.model.entity.user.ProjectSettingRequest;
import cn.ehanmy.hospital.mvp.model.entity.user.ProjectSettingResponse;
import cn.ehanmy.hospital.mvp.model.entity.user.SettingProjectRequest;
import cn.ehanmy.hospital.mvp.model.entity.user.SettingProjectResponse;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.CancelAppointmentRequest;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.CancelAppointmentResponse;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.ChangeUserAppointmentTimeRequest;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.ChangeUserAppointmentTimeResponse;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.ConfirmAppointmentRequest;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.ConfirmAppointmentResponse;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.GetUserAppointmentInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.GetUserAppointmentInfoResponse;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.GetUserAppointmentPageRequest;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.GetUserAppointmentPageResponse;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.GetUserAppointmentTimeRequest;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.GetUserAppointmentTimeResponse;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.HuakouRequest;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.HuakouResponse;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;


/**
 * 服务器接口类
 */
public interface InterfaceService {

    // 升级检查
    @POST("gateway")
    Observable<UpdateResponse> checkUpdate(@Body UpdateRequest request);

    @POST("gateway")
    Observable<LoginResponse> login(@Body LoginRequest request);

    @POST("gateway")
    Observable<HospitalInfoResponse> requestHosptialInfo(@Body HospitalInfoRequest request);

    @POST("gateway")
    Observable<MemberInfoResponse> requestMemberInfo(@Body MemberInfoRequest request);

    @POST("gateway")
    Observable<MemberInfoByIdResponse> requestMemberInfoById(@Body MemberInfoByIdRequest request);

    @POST("gateway")
    Observable<GoodsPageResponse> requestGoodsInfo(@Body GoodsPageRequest request);

    @POST("gateway")
    Observable<GoodsConfirmResponse> confirmGoods(@Body GoodsConfirmRequest request);

    @POST("gateway")
    Observable<GoodsConfirmResponse> confirmGoodsWithSpec(@Body GoodsConfirmWithSpecRequest request);

    @POST("gateway")
    Observable<GoodsBuyResponse> placeGoodsOrder(@Body GoodsBuyRequest request);

    // 订单列表相关接口
    @POST("gateway")
    Observable<OrderListResponse> requestOrderListPage(@Body OrderListRequest request);

    // 请求订单列表
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

    // 修改医院信息设置
    @POST("gateway")
    Observable<ChangeHospitalInfoResponse> changeHospitalInfo(@Body ChangeHospitalInfoRequest
                                                                      request);


    // 修改医院照片
    @POST("gateway")
    Observable<ChangeHospitalImageResponse> changeHospitalImage
    (@Body ChangeHospitalImageRequest request);


    // 修改医院照片
    @POST("gateway")
    Observable<GetActivityInfoResponse> getActivityInfo(@Body GetActivityInfoRequest request);

    // 获取用户预约列表
    @POST("gateway")
    Observable<GetUserAppointmentPageResponse> getUserAppointmentPage
    (@Body GetUserAppointmentPageRequest request);


    // 获取用户预约列表
    @POST("gateway")
    Observable<ConfirmAppointmentResponse> confirmAppointment(@Body ConfirmAppointmentRequest
                                                                      request);

    // 获取用户预约列表
    @POST("gateway")
    Observable<CancelAppointmentResponse> cancelAppointment(@Body CancelAppointmentRequest
                                                                    request);

    // 添加活动
    @POST("gateway")
    Observable<AddActivityResponse> addActivity(@Body AddActivityRequest request);

    // 修改活动信息
    @POST("gateway")
    Observable<ChangeActivityInfoResponse> changeActivityInfo(@Body ChangeActivityInfoRequest
                                                                      request);

    // 删除活动
    @POST("gateway")
    Observable<DeleteActivityInfoResponse> deleteActivityInfo(@Body DeleteActivityInfoRequest
                                                                      request);

    // 获取用户预约详情
    @POST("gateway")
    Observable<GetUserAppointmentInfoResponse> getUserAppointmentInfo
    (@Body GetUserAppointmentInfoRequest request);

    // 获取用户预约时间
    @POST("gateway")
    Observable<GetUserAppointmentTimeResponse> getUserAppointmentTime
    (@Body GetUserAppointmentTimeRequest request);

    // 获取用户预约时间
    @POST("gateway")
    Observable<GetOrderAppointmentTimeResponse> getOrderAppointmentTime
    (@Body GetOrderAppointmentTimeRequest request);

    // 划扣账单
    @POST("gateway")
    Observable<HuakouResponse> huakou(@Body HuakouRequest request);

    // 修改用户预约时间
    @POST("gateway")
    Observable<ChangeUserAppointmentTimeResponse> changeUserAppointmentTime
    (@Body ChangeUserAppointmentTimeRequest request);

    // 修改用户预约时间
    @POST("gateway")
    Observable<GetShopAppointmentPageResponse> getShopAppointmentPage
    (@Body GetShopAppointmentPageRequest request);

    // 修改用户预约时间
    @POST("gateway")
    Observable<ShopAppointmentInfoResponse> shopAppointmentInfo
    (@Body ShopAppointmentInfoRequest request);

    // 修改用户预约时间
    @POST("gateway")
    Observable<CancelShopAppointmentResponse> cancelShopAppointment(@Body CancelShopAppointmentRequest request);

    // 修改用户预约时间
    @POST("gateway")
    Observable<GetRelatedListResponse> getRelatedList(@Body GetRelatedListRequest request);

    // 确定上铺预约
    @POST("gateway")
    Observable<ConfirmShopAppointmentResponse> confirmShopAppointment(@Body ConfirmShopAppointmentRequest request);

    // 列表支付接口
    @POST("gateway")
    Observable<GoPayResponse> goPay(@Body GoPayRequest request);

    // 获取支付状态
    @POST("gateway")
    Observable<GetPayStatusResponse> getPayStatus(@Body GetPayStatusRequest request);


    @POST("gateway")
        // 获取七牛上传信息
    Observable<QiniuResponse> getQiniuInfo(@Body QiniuRequest request);

    @POST("gateway")
        // 获取七牛上传信息
    Observable<OrderPayResponse> orderPay(@Body OrderPayRequest request);

    @POST("gateway")
        // 获取七牛上传信息
    Observable<GetCategoryGoodsListResponse> getCategoryGoodsList(@Body GetCategoryGoodsListRequest request);
}
