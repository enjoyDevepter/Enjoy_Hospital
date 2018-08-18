package cn.ehanmy.hospital.mvp.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import java.util.List;

import cn.ehanmy.hospital.mvp.model.entity.Order;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.RelatedListContract;


@ActivityScope
public class RelatedListPresenter extends BasePresenter<RelatedListContract.Model, RelatedListContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public RelatedListPresenter(RelatedListContract.Model model, RelatedListContract.View rootView) {
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

    public void requestRelatedList() {
        List<Order> orders = mModel.requestRelatedList();
        mRootView.updateList(orders);
    }
}
