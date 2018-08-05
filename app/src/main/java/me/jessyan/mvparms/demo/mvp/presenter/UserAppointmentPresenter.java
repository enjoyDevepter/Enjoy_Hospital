package me.jessyan.mvparms.demo.mvp.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import java.util.List;

import me.jessyan.mvparms.demo.mvp.model.entity.ShopAppointment;
import me.jessyan.mvparms.demo.mvp.model.entity.UserAppointment;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import me.jessyan.mvparms.demo.mvp.contract.UserAppointmentContract;


@ActivityScope
public class UserAppointmentPresenter extends BasePresenter<UserAppointmentContract.Model, UserAppointmentContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public UserAppointmentPresenter(UserAppointmentContract.Model model, UserAppointmentContract.View rootView) {
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


    public void doSearch(String key,int searchType){
        List<UserAppointment> shopAppointments = mModel.doSearch(key, searchType);
        mRootView.updateList(shopAppointments);
    }
}
