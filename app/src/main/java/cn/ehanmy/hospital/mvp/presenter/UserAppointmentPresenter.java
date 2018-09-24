package cn.ehanmy.hospital.mvp.presenter;

import android.app.Application;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.List;

import cn.ehanmy.hospital.mvp.model.OrderFormCenterModel;
import cn.ehanmy.hospital.mvp.model.UserAppointmentModel;
import cn.ehanmy.hospital.mvp.model.entity.ShopAppointment;
import cn.ehanmy.hospital.mvp.model.entity.UserAppointment;
import cn.ehanmy.hospital.mvp.model.entity.UserBean;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderBean;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderListRequest;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderListResponse;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.GetUserAppointmentPageRequest;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.GetUserAppointmentPageResponse;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.OrderProjectDetailBean;
import cn.ehanmy.hospital.mvp.ui.adapter.UserAppointmentAdapter;
import cn.ehanmy.hospital.util.CacheUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.UserAppointmentContract;


@ActivityScope
public class UserAppointmentPresenter extends BasePresenter<UserAppointmentContract.Model, UserAppointmentContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public UserAppointmentPresenter(UserAppointmentContract.Model model, UserAppointmentContract.View rootView) {
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
    UserAppointmentAdapter mAdapter;
    @Inject
    List<OrderProjectDetailBean> orderBeanList;

    public void requestOrderList(String type){
        requestOrderList(1,type,true);
    }

    public void nextPage(){
        requestOrderList(nextPageIndex,currType,false);
    }

    private String currType = UserAppointmentModel.SEARCH_TYPE_NEW;
    private int nextPageIndex = 1;

    private void requestOrderList(int pageIndex,String type,final boolean clear) {
        GetUserAppointmentPageRequest request = new GetUserAppointmentPageRequest();
        request.setPageIndex(pageIndex);
        request.setStatus(type);
        request.setPageSize(10);

        UserBean cacheUserBean = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        request.setToken(cacheUserBean.getToken());

        mModel.getUserAppintmentPage(request)
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
                .subscribe(new Consumer<GetUserAppointmentPageResponse>() {
                    @Override
                    public void accept(GetUserAppointmentPageResponse response) throws Exception {
                        if (response.isSuccess()) {
                            if(clear){
                                orderBeanList.clear();
                                orderBeanList.add(new OrderProjectDetailBean());
                            }
                            currType = type;
                            nextPageIndex = response.getNextPageIndex();
                            mRootView.setEnd(nextPageIndex == -1);
                            List<OrderProjectDetailBean> orderList = response.getOrderProjectDetailList();
                            orderBeanList.addAll(orderList);
                            for(OrderProjectDetailBean ob : orderBeanList){
                                ob.setSearcyType(currType);
                            }
                            mAdapter.notifyDataSetChanged();
                            mRootView.hideLoading();
                        } else {
                            mRootView.showMessage(response.getRetDesc());
                        }
                    }
                });
    }


//
//    public void doSearch(String key,int searchType){
//        List<UserAppointment> shopAppointments = mModel.doSearch(key, searchType);
////        mRootView.updateList(shopAppointments);
//    }
}
