package me.jessyan.mvparms.demo.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.jessyan.mvparms.demo.mvp.contract.BuyCenterContract;
import me.jessyan.mvparms.demo.mvp.model.api.service.InterfaceService;
import me.jessyan.mvparms.demo.mvp.model.entity.request.MemberInfoRequest;
import me.jessyan.mvparms.demo.mvp.model.entity.response.MemberInfoResponse;


@ActivityScope
public class BuyCenterModel extends BaseModel implements BuyCenterContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public BuyCenterModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }


    @Override
    public Observable<MemberInfoResponse> requestMemberinfo(MemberInfoRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .requestMemberInfo(request);
    }
}