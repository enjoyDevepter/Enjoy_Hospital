package me.jessyan.mvparms.demo.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.mvparms.demo.mvp.model.entity.UserBean;
import me.jessyan.mvparms.demo.mvp.model.entity.request.MemberInfoRequest;
import me.jessyan.mvparms.demo.mvp.model.entity.response.MemberInfoResponse;
import me.jessyan.mvparms.demo.util.GlobalConfig;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import me.jessyan.mvparms.demo.mvp.contract.BuyCenterContract;


@ActivityScope
public class BuyCenterPresenter extends BasePresenter<BuyCenterContract.Model, BuyCenterContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public BuyCenterPresenter(BuyCenterContract.Model model, BuyCenterContract.View rootView) {
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

    public void requestHospitalInfo(String username){
        MemberInfoRequest memberInfoRequest = new MemberInfoRequest();
        UserBean user = (UserBean) ArmsUtils.obtainAppComponentFromContext(ArmsUtils.getContext()).extras().get(GlobalConfig.CACHE_KEY_USER);
        memberInfoRequest.setUsername(username);
        memberInfoRequest.setToken(user.getToken());
        mModel.requestMemberinfo(memberInfoRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MemberInfoResponse>() {
                    @Override
                    public void accept(MemberInfoResponse response) {
                        if (response.isSuccess()) {
                            mRootView.updateCodeisRight(true);
                        }else{
                            mRootView.updateCodeisRight(false);
                            mRootView.showMessage(response.getRetDesc());
                        }
                    }
                });;
    }
}
