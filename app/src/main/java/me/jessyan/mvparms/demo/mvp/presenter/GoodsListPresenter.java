package me.jessyan.mvparms.demo.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.cache.IntelligentCache;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.mvparms.demo.mvp.model.entity.UserBean;
import me.jessyan.mvparms.demo.mvp.model.entity.goods_list.GoodsListBean;
import me.jessyan.mvparms.demo.mvp.model.entity.goods_list.OrderBy;
import me.jessyan.mvparms.demo.mvp.model.entity.request.GoodsPageRequest;
import me.jessyan.mvparms.demo.mvp.model.entity.response.GoodsPageResponse;
import me.jessyan.mvparms.demo.util.CacheUtil;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import me.jessyan.mvparms.demo.mvp.contract.GoodsListContract;


@ActivityScope
public class GoodsListPresenter extends BasePresenter<GoodsListContract.Model, GoodsListContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    RecyclerView.Adapter mAdapter;
    @Inject
    List<GoodsListBean> GoodsList;

    @Inject
    public GoodsListPresenter(GoodsListContract.Model model, GoodsListContract.View rootView) {
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


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
        getStores();
    }

    public void getStores() {
        GoodsPageRequest request = new GoodsPageRequest();
        request.setPageIndex(1);
        request.setPageSize(10);
        UserBean cacheUserBean = (UserBean) ArmsUtils.obtainAppComponentFromContext(ArmsUtils.getContext()).extras().get(IntelligentCache.KEY_KEEP+CacheUtil.CACHE_KEY_USER);
        System.out.println("user -> cacheUserBean = "+cacheUserBean);
        request.setToken(cacheUserBean.getToken());

        OrderBy orderBy = new OrderBy();
        orderBy.setField("sales");
        orderBy.setAsc(true);
        request.setOrderBy(orderBy);

        mModel.requestGoodsPage(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GoodsPageResponse>() {
                    @Override
                    public void accept(GoodsPageResponse response) throws Exception {
                        if (response.isSuccess() && response.getGoodsList() != null) {
                            GoodsList.clear();
                            GoodsList.addAll(response.getGoodsList());
                            mAdapter.notifyDataSetChanged();
                        } else {
                            mRootView.showMessage(response.getRetDesc());
                        }
                    }
                });
    }
}
