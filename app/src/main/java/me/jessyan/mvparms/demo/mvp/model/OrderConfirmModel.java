package me.jessyan.mvparms.demo.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.jessyan.mvparms.demo.mvp.contract.OrderConfirmContract;
import me.jessyan.mvparms.demo.mvp.model.api.service.InterfaceService;
import me.jessyan.mvparms.demo.mvp.model.entity.request.GoodsConfirmRequest;
import me.jessyan.mvparms.demo.mvp.model.entity.request.GoodsPageRequest;
import me.jessyan.mvparms.demo.mvp.model.entity.response.GoodsConfirmResponse;
import me.jessyan.mvparms.demo.mvp.model.entity.response.GoodsPageResponse;


@ActivityScope
public class OrderConfirmModel extends BaseModel implements OrderConfirmContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public OrderConfirmModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<GoodsConfirmResponse> confirmGoods(GoodsConfirmRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .confirmGoods(request);
    }
}