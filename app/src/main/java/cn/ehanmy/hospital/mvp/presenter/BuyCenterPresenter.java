package cn.ehanmy.hospital.mvp.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import cn.ehanmy.hospital.mvp.model.entity.UserBean;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberBean;
import cn.ehanmy.hospital.mvp.model.entity.request.MemberInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.response.MemberInfoResponse;
import cn.ehanmy.hospital.util.CacheUtil;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.BuyCenterContract;


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
        UserBean user = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        memberInfoRequest.setUsername(username);
        memberInfoRequest.setToken(user.getToken());
        mModel.requestMemberinfo(memberInfoRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                        if (response.isSuccess()) {
                            MemberBean member = response.getMember();
                            CacheUtil.saveConstant(CacheUtil.CACHE_KEY_MEMBER, member);
                            System.out.println("member = "+member);
                            mRootView.updateCodeisRight(true);
                        }else{
                            mRootView.updateCodeisRight(false);
                            mRootView.showMessage(response.getRetDesc());
                        }
                    }
                );;
    }
}