package cn.ehanmy.hospital.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import io.reactivex.Observable;
import cn.ehanmy.hospital.mvp.contract.CommitOrderContract;
import cn.ehanmy.hospital.mvp.model.api.service.InterfaceService;
import cn.ehanmy.hospital.mvp.model.entity.request.GoodsBuyRequest;
import cn.ehanmy.hospital.mvp.model.entity.request.GoodsConfirmRequest;
import cn.ehanmy.hospital.mvp.model.entity.request.MakeSureRequest;
import cn.ehanmy.hospital.mvp.model.entity.response.GoodsBuyResponse;
import cn.ehanmy.hospital.mvp.model.entity.response.GoodsConfirmResponse;
import cn.ehanmy.hospital.mvp.model.entity.response.MakeSureResponse;


@ActivityScope
public class CommitOrderModel extends BaseModel implements CommitOrderContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public CommitOrderModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<GoodsBuyResponse> goodsBuy(GoodsBuyRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .buyGoods(request);
    }

    @Override
    public Observable<MakeSureResponse> makeSureOrder(MakeSureRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .makeSureOrder(request);
    }
}