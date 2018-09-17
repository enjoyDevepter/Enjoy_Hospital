package cn.ehanmy.hospital.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.cache.Cache;
import com.jess.arms.integration.cache.IntelligentCache;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import cn.ehanmy.hospital.mvp.model.entity.goods_list.Category;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.CategoryResponse;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.SimpleRequest;
import cn.ehanmy.hospital.mvp.ui.adapter.GoodsFilterSecondAdapter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import cn.ehanmy.hospital.mvp.model.entity.UserBean;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.GoodsListBean;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.OrderBy;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.GoodsPageRequest;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.GoodsPageResponse;
import cn.ehanmy.hospital.util.CacheUtil;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.GoodsListContract;


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
    List<Category> categories;
    private int preEndIndex;
    private int lastPageIndex = 1;

    @Inject
    GoodsFilterSecondAdapter secondAdapter;

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

    public void getCategory() {
        Cache<String, Object> cache = ArmsUtils.obtainAppComponentFromContext(mApplication).extras();
        if (cache.get("category") != null) {
            mRootView.refreshNaviTitle((List<Category>) cache.get("category"));
            return;
        }
        SimpleRequest request = new SimpleRequest();
        mModel.getCategory(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CategoryResponse>() {
                    @Override
                    public void accept(CategoryResponse response) throws Exception {
                        if (response.isSuccess()) {
//                            mRootView.refreshNaviTitle(response.getGoodsCategoryList());
                            categories.clear();
                            Category e = new Category();
                            e.setName("全部商品");
                            categories.add(e);
                            Category category = response.getGoodsCategoryList().get(0);
                            if(category != null){
                                categories.addAll(category.getGoodsCategoryList());
                            }
                            secondAdapter.notifyDataSetChanged();
                        } else {
                            mRootView.showMessage(response.getRetDesc());
                        }
                    }
                });
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void init(){
        getGoodsList(true);
    }

    public void getGoodsList(boolean pullToRefresh) {

        GoodsPageRequest request = new GoodsPageRequest();
        Cache<String, Object> cache = ArmsUtils.obtainAppComponentFromContext(mRootView.getActivity()).extras();
        Object secondCategoryId = mRootView.getCache().get("secondCategoryId");
        if(secondCategoryId != null){
            request.setSecondCategoryId((String) secondCategoryId);
            request.setCategoryId((String) mRootView.getCache().get("categoryId"));
        }
        request.setPageIndex(1);
        request.setPageSize(10);
        UserBean cacheUserBean = (UserBean) ArmsUtils.obtainAppComponentFromContext(ArmsUtils.getContext()).extras().get(IntelligentCache.KEY_KEEP+CacheUtil.CACHE_KEY_USER);
        request.setToken(cacheUserBean.getToken());

        if (!ArmsUtils.isEmpty(String.valueOf(mRootView.getCache().get("orderByField")))) {
            OrderBy orderBy = new OrderBy();
            orderBy.setField((String) mRootView.getCache().get("orderByField"));
            orderBy.setAsc((Boolean) mRootView.getCache().get("orderByAsc"));
            request.setOrderBy(orderBy);
        }

        if (pullToRefresh) lastPageIndex = 1;
        request.setPageIndex(lastPageIndex);//下拉刷新默认只请求第一页

        mModel.requestGoodsPage(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    if (pullToRefresh)
                        mRootView.showLoading();//显示下拉刷新的进度条
                    else
                        mRootView.startLoadMore();//显示上拉加载更多的进度条
                })
                .doFinally(() -> {
                    if (pullToRefresh)
                        mRootView.hideLoading();//隐藏下拉刷新的进度条
                    else
                        mRootView.endLoadMore();//隐藏上拉加载更多的进度条
                }).subscribe(new Consumer<GoodsPageResponse>() {
            @Override
            public void accept(GoodsPageResponse response) throws Exception {
                if (response.isSuccess()) {
                    if (pullToRefresh) {
                        GoodsList.clear();
                    }
                    mRootView.setLoadedAllItems(response.getNextPageIndex() == -1);
                    GoodsList.addAll(response.getGoodsList());
                    preEndIndex = GoodsList.size();//更新之前列表总长度,用于确定加载更多的起始位置
                    lastPageIndex = GoodsList.size() / 10;
                    if (pullToRefresh) {
                        mAdapter.notifyDataSetChanged();
                    } else {
                        mAdapter.notifyItemRangeInserted(preEndIndex, GoodsList.size());
                        mAdapter.notifyDataSetChanged();
                    }
                } else {
                    mRootView.showMessage(response.getRetDesc());
                }
            }
        });
    }
}
