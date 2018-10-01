package cn.ehanmy.hospital.mvp.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.List;

import cn.ehanmy.hospital.mvp.model.ShopAppointmentModel;
import cn.ehanmy.hospital.mvp.model.UserAppointmentModel;
import cn.ehanmy.hospital.mvp.model.entity.Order;
import cn.ehanmy.hospital.mvp.model.entity.ShopAppointment;
import cn.ehanmy.hospital.mvp.model.entity.UserBean;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.GetShopAppointmentPageRequest;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.GetShopAppointmentPageResponse;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.GetUserAppointmentPageRequest;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.GetUserAppointmentPageResponse;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.OrderProjectDetailBean;
import cn.ehanmy.hospital.mvp.ui.adapter.ShopAppointmentAdapter;
import cn.ehanmy.hospital.mvp.ui.adapter.UserAppointmentAdapter;
import cn.ehanmy.hospital.util.CacheUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.ShopAppointmentContract;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


@ActivityScope
public class ShopAppointmentPresenter extends BasePresenter<ShopAppointmentContract.Model, ShopAppointmentContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public ShopAppointmentPresenter(ShopAppointmentContract.Model model, ShopAppointmentContract.View rootView) {
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


    @Inject
    ShopAppointmentAdapter mAdapter;
    @Inject
    List<OrderProjectDetailBean> orderBeanList;

    public void requestOrderList(String type){
        requestOrderList(1,type,true);
    }

    //    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void init(){
        requestOrderList(currType);
    }

    public void nextPage(){
        requestOrderList(nextPageIndex,currType,false);
    }

    private String currType = ShopAppointmentModel.SEARCH_TYPE_APPOINTMENT;
    private int nextPageIndex = 1;

    private void requestOrderList(int pageIndex,String type,final boolean clear) {
        GetShopAppointmentPageRequest request = new GetShopAppointmentPageRequest();
        request.setPageIndex(pageIndex);
        request.setStatus(type);
        request.setPageSize(10);

        UserBean cacheUserBean = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        request.setToken(cacheUserBean.getToken());

        mModel.getShopAppointmentPage(request)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    if (clear) {
//                        mRootView.showLoading();//显示下拉刷新的进度条
                    }else
                        mRootView.startLoadMore();//显示上拉加载更多的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (clear)
                        mRootView.hideLoading();//隐藏下拉刷新的进度条
                    else
                        mRootView.endLoadMore();//隐藏上拉加载更多的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<GetShopAppointmentPageResponse>(mErrorHandler) {
                    @Override
                    public void onNext(GetShopAppointmentPageResponse response) {
                        if (response.isSuccess()) {
                            if(clear){
                                orderBeanList.clear();
                                orderBeanList.add(new OrderProjectDetailBean());
                            }
                            currType = type;
                            nextPageIndex = response.getNextPageIndex();
                            mRootView.setEnd(nextPageIndex == -1);
                            mRootView.showError(response.getOrderProjectDetailList().size() > 0);
                            List<OrderProjectDetailBean> orderList = response.getOrderProjectDetailList();
                            orderBeanList.addAll(orderList);
                            mAdapter.notifyDataSetChanged();
                            mRootView.hideLoading();
                        } else {
                            mRootView.showMessage(response.getRetDesc());
                        }
                    }
                });
    }
}
