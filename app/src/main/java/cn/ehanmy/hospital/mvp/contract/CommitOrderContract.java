package cn.ehanmy.hospital.mvp.contract;

import android.app.Activity;

import com.jess.arms.integration.cache.Cache;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import java.util.List;

import cn.ehanmy.hospital.mvp.model.entity.goods_list.PayEntry;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberBean;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberInfoResponse;
import cn.ehanmy.hospital.mvp.model.entity.order.GetPayStatusRequest;
import cn.ehanmy.hospital.mvp.model.entity.order.GetPayStatusResponse;
import cn.ehanmy.hospital.mvp.model.entity.order.GoPayRequest;
import cn.ehanmy.hospital.mvp.model.entity.order.GoPayResponse;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderBean;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderPayRequest;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderPayResponse;
import cn.ehanmy.hospital.mvp.model.entity.placeOrder.GoodsBuyRequest;
import cn.ehanmy.hospital.mvp.model.entity.placeOrder.GoodsBuyResponse;
import io.reactivex.Observable;


public interface CommitOrderContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        Cache getCache();
        Activity getActivity();
        void updateMember(MemberBean memberBean);
        void showPaySuccess(GoodsBuyResponse response);
        void showPaySuccess(GoPayResponse response,OrderBean orderBean);
        void payOk(String orderId, long orderTime);
        void updatePayEntry(List<PayEntry> payEntries);
        void showPayError(String errorInfo);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<OrderPayResponse> orderPay(OrderPayRequest request);
        Observable<GetPayStatusResponse> getPayStatus(GetPayStatusRequest request);
        Observable<GoodsBuyResponse> placeGoodsOrder(GoodsBuyRequest request);
        Observable<GoPayResponse> goPay(GoPayRequest request);
        Observable<MemberInfoResponse> requestMemberinfo(MemberInfoRequest request);
    }
}
