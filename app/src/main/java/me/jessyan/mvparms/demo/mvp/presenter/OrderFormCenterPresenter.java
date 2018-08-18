package me.jessyan.mvparms.demo.mvp.presenter;

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

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.mvparms.demo.mvp.model.entity.UserBean;
import me.jessyan.mvparms.demo.mvp.model.entity.order.OrderBean;
import me.jessyan.mvparms.demo.mvp.model.entity.order.OrderListRequest;
import me.jessyan.mvparms.demo.mvp.model.entity.order.OrderListResponse;
import me.jessyan.mvparms.demo.util.CacheUtil;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import me.jessyan.mvparms.demo.mvp.contract.OrderFormCenterContract;


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

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
        getStores();
    }

    public void getStores() {
        OrderListRequest request = new OrderListRequest();
        request.setPageIndex(1);
        request.setPageSize(10);
        UserBean cacheUserBean = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        request.setToken(cacheUserBean.getToken());


        mModel.requestOrderListPage(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<OrderListResponse>() {
                    @Override
                    public void accept(OrderListResponse response) throws Exception {
                        if (response.isSuccess()) {
                            orderBeanList.clear();
                            orderBeanList.addAll(response.getOrderList());
                            mAdapter.notifyDataSetChanged();
                        } else {
                            mRootView.showMessage(response.getRetDesc());
                        }
                    }
                });
    }

}
