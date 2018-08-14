package me.jessyan.mvparms.demo.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.mvp.model.entity.UserBean;
import me.jessyan.mvparms.demo.mvp.model.entity.goods_list.GoodsConfirmBean;
import me.jessyan.mvparms.demo.mvp.model.entity.goods_list.GoodsListBean;
import me.jessyan.mvparms.demo.mvp.model.entity.goods_list.GoodsOrderBean;
import me.jessyan.mvparms.demo.mvp.model.entity.member_info.MemberBean;
import me.jessyan.mvparms.demo.mvp.model.entity.request.GoodsBuyRequest;
import me.jessyan.mvparms.demo.mvp.model.entity.response.GoodsConfirmResponse;
import me.jessyan.mvparms.demo.mvp.ui.activity.CommitOrderActivity;
import me.jessyan.mvparms.demo.mvp.ui.widget.CustomDialog;
import me.jessyan.mvparms.demo.util.CacheUtil;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import me.jessyan.mvparms.demo.mvp.contract.CommitOrderContract;


@ActivityScope
public class CommitOrderPresenter extends BasePresenter<CommitOrderContract.Model, CommitOrderContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public CommitOrderPresenter(CommitOrderContract.Model model, CommitOrderContract.View rootView) {
        super(model, rootView);
    }

    public void makeSureOrder(){
        mRootView.makeSure(true);
//        mModel.makeSureOrder(null);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void commitOrder(){
        Intent intent = mRootView.getActivity().getIntent();
        GoodsConfirmResponse goodsConfirmResponse = (GoodsConfirmResponse) intent.getSerializableExtra(CommitOrderActivity.KEY_FOR_ORDER_INDO);
        String remark = intent.getStringExtra(CommitOrderActivity.KEY_FOR_REMARK);
        int money = intent.getIntExtra(CommitOrderActivity.KEY_FOR_MONEY,0);
        MemberBean memberBean = CacheUtil.getConstant(CacheUtil.CACHE_KEY_MEMBER);
        UserBean userBean = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);

        GoodsBuyRequest goodsBuyRequest = new GoodsBuyRequest();
        GoodsListBean goods = goodsConfirmResponse.getGoods();
        GoodsConfirmBean goodsConfirmBean = new GoodsConfirmBean();
        goodsConfirmBean.setSalePrice(goods.getSalePrice());
        goodsConfirmBean.setNums(goods.getNums());
        goodsConfirmBean.setMerchId(goods.getMerchId());
        goodsConfirmBean.setGoodsId(goods.getGoodsId());
        goodsBuyRequest.setGoods(goodsConfirmBean);
        goodsBuyRequest.setMemberId(memberBean.getMemberId());
        goodsBuyRequest.setMoney(money);
        goodsBuyRequest.setPayMoney(goodsConfirmResponse.getPayMoney());
        goodsBuyRequest.setPrice(goodsConfirmResponse.getPrice());
        goodsBuyRequest.setRemark(remark);
        goodsBuyRequest.setToken(userBean.getToken());
        goodsBuyRequest.setTotalPrice(goodsConfirmResponse.getTotalPrice());

        mModel.goodsBuy(goodsBuyRequest).subscribeOn(Schedulers.io())
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
