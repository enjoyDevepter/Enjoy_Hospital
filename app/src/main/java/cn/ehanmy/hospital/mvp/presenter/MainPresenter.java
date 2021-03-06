package cn.ehanmy.hospital.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.List;

import javax.inject.Inject;

import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.mvp.contract.MainContract;
import cn.ehanmy.hospital.mvp.model.entity.MainItem;
import cn.ehanmy.hospital.mvp.model.entity.UpdateRequest;
import cn.ehanmy.hospital.mvp.model.entity.UpdateResponse;
import cn.ehanmy.hospital.mvp.ui.adapter.MainAdapter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class MainPresenter extends BasePresenter<MainContract.Model, MainContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    MainAdapter mAdapter;
    @Inject
    List<MainItem> mainItems;
    int[] images = new int[]{R.mipmap.place_order, R.mipmap.order, R.mipmap.shop_reservation, R.mipmap.user_reservation, R.mipmap.activity, R.mipmap.hospital, R.mipmap.setting};

    @Inject
    public MainPresenter(MainContract.Model model, MainContract.View rootView) {
        super(model, rootView);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
        checkUpdateForApp();
        // 初始化数据
        String[] titles = mApplication.getResources().getStringArray(R.array.main_title);
        for (int i = 0; i < titles.length; i++) {
            MainItem item = new MainItem();
            item.setName(titles[i]);
            item.setImageId(images[i]);
            mainItems.add(item);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }


    private void checkUpdateForApp() {
        UpdateRequest request = new UpdateRequest();
        request.setType("1");
        mModel.checkUpdate(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<UpdateResponse>(mErrorHandler) {
                    @Override
                    public void onNext(UpdateResponse response) {
                        if (response.isSuccess()) {
                            mRootView.showUpdateInfo(response);
                        }
                    }
                });
    }
}
