package cn.ehanmy.hospital.mvp.contract;

import android.app.Activity;

import com.jess.arms.integration.cache.Cache;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.ConfirmShopAppointmentRequest;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.ConfirmShopAppointmentResponse;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.GetRelatedListRequest;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.GetRelatedListResponse;
import io.reactivex.Observable;


public interface RelatedListContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void setLoadedAllItems(boolean has);

        Cache getCache();

        void showError(boolean hasDate);

        Activity getActivity();

        void startLoadMore();

        void endLoadMore();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<GetRelatedListResponse> getRelatedList(GetRelatedListRequest request);

        Observable<ConfirmShopAppointmentResponse> confirmShopAppointmentResponseObservable(ConfirmShopAppointmentRequest request);
    }
}
