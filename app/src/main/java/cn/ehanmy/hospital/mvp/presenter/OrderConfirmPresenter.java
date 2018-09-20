package cn.ehanmy.hospital.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.RxLifecycleUtils;

import cn.ehanmy.hospital.mvp.model.entity.goods_list.GoodsConfirmWithSpecBean;
import cn.ehanmy.hospital.mvp.model.entity.request.GoodsConfirmWithSpecRequest;
import cn.ehanmy.hospital.mvp.model.entity.response.GoodsConfirmResponse;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import cn.ehanmy.hospital.mvp.model.entity.UserBean;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.GoodsConfirmBean;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.GoodsListBean;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberBean;
import cn.ehanmy.hospital.mvp.model.entity.request.GoodsConfirmRequest;
import cn.ehanmy.hospital.mvp.ui.activity.OrderConfirmActivity;
import cn.ehanmy.hospital.util.CacheUtil;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.OrderConfirmContract;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


@ActivityScope
public class OrderConfirmPresenter extends BasePresenter<OrderConfirmContract.Model, OrderConfirmContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public OrderConfirmPresenter(OrderConfirmContract.Model model, OrderConfirmContract.View rootView) {
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
    public void init() {
        requestConfirmInfo(0);
    }

    public void requestConfirmInfo(long money) {
        GoodsListBean goodsListBean = (GoodsListBean) mRootView.getActivity().getIntent().getSerializableExtra(OrderConfirmActivity.KEY_FOR_GOODS_INFO);
        GoodsConfirmRequest goodsConfirmRequest = new GoodsConfirmRequest();
        MemberBean memberBean = CacheUtil.getConstant(CacheUtil.CACHE_KEY_MEMBER);
        goodsConfirmRequest.setMemberId(memberBean.getMemberId());
        GoodsConfirmBean goodsConfirmBean = new GoodsConfirmBean();
        goodsConfirmBean.setGoodsId(goodsListBean.getGoodsId());
        goodsConfirmBean.setMerchId(goodsListBean.getMerchId());
        goodsConfirmBean.setNums(1);
        goodsConfirmBean.setSalePrice(goodsListBean.getSalePrice());
        goodsConfirmRequest.setGoods(goodsConfirmBean);
        goodsConfirmRequest.setMoney(money);
        goodsConfirmRequest.setToken(((UserBean) CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER)).getToken());

        mModel.confirmGoods(goodsConfirmRequest).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    if (s.isSuccess()) {
                        mRootView.update(s);
                    } else {
                        mRootView.showMessage(s.getRetDesc());
                        mRootView.killMyself();
                    }
                });
    }

    public void requestConfirmInfoWithSpec(long money, String specValueId) {
        GoodsListBean goodsListBean = (GoodsListBean) mRootView.getActivity().getIntent().getSerializableExtra(OrderConfirmActivity.KEY_FOR_GOODS_INFO);
        GoodsConfirmWithSpecRequest goodsConfirmRequest = new GoodsConfirmWithSpecRequest();
        MemberBean memberBean = CacheUtil.getConstant(CacheUtil.CACHE_KEY_MEMBER);
        goodsConfirmRequest.setMemberId(memberBean.getMemberId());
        GoodsConfirmWithSpecBean goodsConfirmBean = new GoodsConfirmWithSpecBean();
        goodsConfirmBean.setSpecValueId(specValueId);
        goodsConfirmBean.setGoodsId(goodsListBean.getGoodsId());
        goodsConfirmBean.setMerchId(goodsListBean.getMerchId());
        goodsConfirmBean.setNums(1);
        goodsConfirmBean.setSalePrice(goodsListBean.getSalePrice());
        goodsConfirmRequest.setGoods(goodsConfirmBean);
        goodsConfirmRequest.setMoney(money);
        goodsConfirmRequest.setToken(((UserBean) CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER)).getToken());

        mModel.confirmGoodsWithSpec(goodsConfirmRequest).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<GoodsConfirmResponse>(mErrorHandler) {
                    @Override
                    public void onNext(GoodsConfirmResponse response) {

                        if (response.isSuccess()) {
                            mRootView.update(response);
                        } else {
                            mRootView.showMessage(response.getRetDesc());
                            mRootView.killMyself();
                        }
                    }
                });
    }
}
