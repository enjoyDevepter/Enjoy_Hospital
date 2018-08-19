package cn.ehanmy.hospital.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.cache.IntelligentCache;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.List;

import cn.ehanmy.hospital.mvp.model.OrderFormCenterModel;
import cn.ehanmy.hospital.mvp.model.entity.User;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import cn.ehanmy.hospital.mvp.model.entity.UserBean;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderBean;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderListRequest;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderListResponse;
import cn.ehanmy.hospital.util.CacheUtil;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.OrderFormCenterContract;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

import static cn.ehanmy.hospital.mvp.model.OrderFormCenterModel.SEARCH_TYPE_ALL;


@ActivityScope
public class OrderFormCenterPresenter extends BasePresenter<OrderFormCenterContract.Model, OrderFormCenterContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;


    @Inject
    RecyclerView.Adapter mAdapter;
    @Inject
    List<OrderBean> orderBeanList;

    @Inject
    public OrderFormCenterPresenter(OrderFormCenterContract.Model model, OrderFormCenterContract.View rootView) {
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

    public void requestOrderList(String type){
        requestOrderList(1,type,true);
    }

    public void nextPage(){
        requestOrderList(nextPageIndex,currType,false);
    }

    private String currType = OrderFormCenterModel.SEARCH_TYPE_UNPAID;
    private int nextPageIndex = 1;

    private void requestOrderList(int pageIndex,String type,final boolean clear) {
        OrderListRequest request = new OrderListRequest();
        request.setPageIndex(pageIndex);
        request.setOrderStatus(type);
        request.setPageSize(10);

        UserBean cacheUserBean = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        request.setToken(cacheUserBean.getToken());

        mModel.requestOrderListPage(request)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    if (clear)
                        mRootView.showLoading();//显示下拉刷新的进度条
                    else
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
                .subscribe(new Consumer<OrderListResponse>() {
                    @Override
                    public void accept(OrderListResponse response) throws Exception {
                        if (response.isSuccess()) {
                            if(clear){
                                orderBeanList.clear();
                            }
                            currType = type;
                            nextPageIndex = response.getNextPageIndex();
                            mRootView.setEnd(nextPageIndex == -1);
                            List<OrderBean> orderList = response.getOrderList();
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
