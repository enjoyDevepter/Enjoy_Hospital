package cn.ehanmy.hospital.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.List;

import cn.ehanmy.hospital.mvp.model.entity.UserBean;
import cn.ehanmy.hospital.mvp.model.entity.order.GetOrderAppointmentTimeRequest;
import cn.ehanmy.hospital.mvp.model.entity.order.GetOrderAppointmentTimeResponse;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.GetUserAppointmentTimeRequest;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.GetUserAppointmentTimeResponse;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.ReservationDateBean;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.ReservationTimeBean;
import cn.ehanmy.hospital.mvp.ui.activity.OrderChoiceTimeActivity;
import cn.ehanmy.hospital.mvp.ui.adapter.DateAdapter;
import cn.ehanmy.hospital.mvp.ui.adapter.TimeAdapter;
import cn.ehanmy.hospital.util.CacheUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.OrderChoiceTimeContract;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class OrderChoiceTimePresenter extends BasePresenter<OrderChoiceTimeContract.Model, OrderChoiceTimeContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;


    @Inject
    List<ReservationDateBean> appointments;
    @Inject
    DateAdapter dateAdapter;
    @Inject
    List<ReservationTimeBean> timeList;
    @Inject
    TimeAdapter timeAdapter;

    @Inject
    public OrderChoiceTimePresenter(OrderChoiceTimeContract.Model model, OrderChoiceTimeContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }



    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void getAppointmentTime() {
        GetOrderAppointmentTimeRequest request = new GetOrderAppointmentTimeRequest();
        UserBean ub = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        request.setToken(ub.getToken());
        request.setGoodsId(mRootView.getActivity().getIntent().getStringExtra(OrderChoiceTimeActivity.KEY_FOR_GOODS_ID));
        request.setMerchId(mRootView.getActivity().getIntent().getStringExtra(OrderChoiceTimeActivity.KEY_FOR_MERCH_ID));
        mModel.getOrderAppointmentTime(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//显示下拉刷新的进度条
                }).doFinally(() -> {
            mRootView.hideLoading();//隐藏下拉刷新的进度条
        }).retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<GetOrderAppointmentTimeResponse>(mErrorHandler) {
                    @Override
                    public void onNext(GetOrderAppointmentTimeResponse response) {
                        if (response.isSuccess()) {
                            appointments.clear();
                            appointments.addAll(response.getReservationDateList());
                            if (appointments.size() > 0) {
                                appointments.get(0).setChoose(true);
                                mRootView.getCache().put("appointmentsDate", appointments.get(0).getDate());
                                timeList.addAll(appointments.get(0).getReservationTimeList());
                            }
                            timeAdapter.notifyDataSetChanged();
                            dateAdapter.notifyDataSetChanged();
                        } else {
                            mRootView.showMessage(response.getRetDesc());
                        }
                    }
                });
    }
}
