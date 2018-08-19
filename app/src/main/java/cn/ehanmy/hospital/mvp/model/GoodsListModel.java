package cn.ehanmy.hospital.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import io.reactivex.Observable;
import cn.ehanmy.hospital.mvp.contract.GoodsListContract;
import cn.ehanmy.hospital.mvp.model.api.service.InterfaceService;
import cn.ehanmy.hospital.mvp.model.entity.request.GoodsPageRequest;
import cn.ehanmy.hospital.mvp.model.entity.response.GoodsPageResponse;


@ActivityScope
public class GoodsListModel extends BaseModel implements GoodsListContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public GoodsListModel(IRepositoryManager repositoryManager,Gson gson,Application application) {
        super(repositoryManager);
        this.mGson = gson;
        this.mApplication = application;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<GoodsPageResponse> requestGoodsPage(GoodsPageRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .requestGoodsInfo(request);
    }
}