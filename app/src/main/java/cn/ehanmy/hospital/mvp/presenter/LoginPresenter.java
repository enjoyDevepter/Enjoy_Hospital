package cn.ehanmy.hospital.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.PermissionUtil;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.List;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.LoginContract;
import cn.ehanmy.hospital.mvp.model.entity.UserBean;
import cn.ehanmy.hospital.mvp.model.entity.hospital.HospitalInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.hospital.HospitalInfoResponse;
import cn.ehanmy.hospital.mvp.model.entity.request.LoginRequest;
import cn.ehanmy.hospital.mvp.model.entity.response.LoginResponse;
import cn.ehanmy.hospital.util.CacheUtil;
import cn.ehanmy.hospital.util.SPUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


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

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void checkUser() {
        requestPermissions();
    }

    public void requestPermissions() {
        //请求外部存储权限用于适配android6.0的权限管理机制
        PermissionUtil.readPhoneState(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                checkToken();
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

    private void checkToken() {
        UserBean spUserbean = SPUtils.get(SPUtils.KEY_FOR_USER_INFO, new UserBean("", "", ""));
        if (null != spUserbean && !ArmsUtils.isEmpty(spUserbean.getToken())) {
            getHospitalInfo();
        }
    }

    public void login(String username, String password) {
        LoginRequest request = new LoginRequest();
        request.setUsername(username);
        request.setPassword(password);
        mModel.login(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<LoginResponse>(mErrorHandler) {
                    @Override
                    public void onNext(LoginResponse response) {
                        if (response.isSuccess()) {
                            UserBean value = new UserBean(username, response.getToken(), response.getSignkey());
                            CacheUtil.saveConstant(CacheUtil.CACHE_KEY_USER, value);
                            CacheUtil.saveConstant(CacheUtil.CACHE_KEY_USER_LOGIN_NAME, username);
                            SPUtils.put(SPUtils.KEY_FOR_USER_INFO, value);
                            SPUtils.put(SPUtils.KEY_FOR_USER_NAME, username);
                            getHospitalInfo();
                        } else {
                            mRootView.showMessage(response.getRetDesc());
                        }
                    }
                });

    }

    private void getHospitalInfo() {
        UserBean spUserbean = SPUtils.get(SPUtils.KEY_FOR_USER_INFO, new UserBean("", "", ""));
        HospitalInfoRequest hospitalInfoRequest = new HospitalInfoRequest();
        hospitalInfoRequest.setToken(spUserbean.getToken());
        mModel.requestHospitalInfo(hospitalInfoRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<HospitalInfoResponse>(mErrorHandler) {
                    @Override
                    public void onNext(HospitalInfoResponse response) {
                        if (response.isSuccess()) {
                            CacheUtil.saveConstant(CacheUtil.CACHE_KEY_USER, spUserbean);
                            CacheUtil.saveConstant(CacheUtil.CACHE_KEY_USER_LOGIN_NAME, spUserbean.getUserName());
                            CacheUtil.saveConstant(CacheUtil.CACHE_KEY_USER_HOSPITAL_INFO, response.getHospital());
                            SPUtils.put(SPUtils.KEY_FOR_HOSPITAL_INFO, response.getHospital());
                            mRootView.goMainPage();
                            mRootView.killMyself();
                        }
                    }
                });
    }
}
