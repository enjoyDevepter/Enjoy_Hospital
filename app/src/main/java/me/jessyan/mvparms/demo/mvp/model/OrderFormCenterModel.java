package me.jessyan.mvparms.demo.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.jessyan.mvparms.demo.mvp.contract.OrderFormCenterContract;
import me.jessyan.mvparms.demo.mvp.model.api.service.InterfaceService;
import me.jessyan.mvparms.demo.mvp.model.entity.order.OrderListRequest;
import me.jessyan.mvparms.demo.mvp.model.entity.order.OrderListResponse;
import me.jessyan.mvparms.demo.mvp.model.entity.request.GoodsPageRequest;
import me.jessyan.mvparms.demo.mvp.model.entity.response.GoodsPageResponse;


@ActivityScope
public class OrderFormCenterModel extends BaseModel implements OrderFormCenterContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    public static final int SEARCH_TYPE_UNPAID = 1;
    public static final int SEARCH_TYPE_SECEND = 2;
    public static final int SEARCH_TYPE_OK = 3;
    public static final int SEARCH_TYPE_ALL = 4;

    @Inject
    public OrderFormCenterModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<OrderListResponse> requestOrderListPage(OrderListRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .requestOrderListPage(request);
    }
}