package cn.ehanmy.hospital.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.PermissionUtil;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import cn.ehanmy.hospital.mvp.contract.LoginContract;
import cn.ehanmy.hospital.mvp.model.entity.UserBean;
import cn.ehanmy.hospital.mvp.model.entity.hospital.HospitalInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.request.LoginRequest;
import cn.ehanmy.hospital.mvp.model.entity.response.LoginResponse;
import cn.ehanmy.hospital.util.CacheUtil;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;


@ActivityScope
public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;

    @Inject
    public LoginPresenter(LoginContract.Model model, LoginContract.View rootView) {
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

    public void requestPermissions() {
        //请求外部存储权限用于适配android6.0的权限管理机制
        PermissionUtil.readPhoneState(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                //request permission success, do something.
            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {
                mRootView.showMessage("Request permissions failure");
            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                mRootView.showMessage("Need to go to the settings");
            }
        }, mRootView.getRxPermissions(), mErrorHandler);
    }


    public void login(String username, String password) {
        mRootView.showLoading();
        LoginRequest request = new LoginRequest();
        request.setUsername(username);
        request.setPassword(password);
////
//        mRootView.killMyself();
//        mRootView.goMainPage();

        mModel.login(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LoginResponse>() {
                    @Override
                    public void accept(LoginResponse response) {
                        if (response.isSuccess()) {
//                            CacheUtil.saveConstant(CacheUtil.CACHE_KEY_USER,new UserBean(username,response.getToken(),response.getSignkey()));
                            UserBean value = new UserBean(username, response.getToken(), response.getSignkey());
                            CacheUtil.saveConstant(CacheUtil.CACHE_KEY_USER,value);
                            HospitalInfoRequest hospitalInfoRequest = new HospitalInfoRequest();
                            hospitalInfoRequest.setToken(response.getToken());
                            mModel.requestHospitalInfo(hospitalInfoRequest)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(s -> {
                                        mRootView.hideLoading();
                                        if(s.isSuccess()){
                                            CacheUtil.saveConstant(CacheUtil.CACHE_KEY_USER_HOSPITAL_INFO,s.getHospital());
                                            mRootView.killMyself();
                                            mRootView.goMainPage();
                                        }else{
                                            mRootView.showMessage(s.getRetDesc());
                                        }
                                    });
                        } else {
                            mRootView.hideLoading();
                            mRootView.showMessage(response.getRetDesc());
                        }
                    }
                });

    }
}
