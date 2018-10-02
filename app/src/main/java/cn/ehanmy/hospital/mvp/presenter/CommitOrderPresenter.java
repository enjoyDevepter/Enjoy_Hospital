package cn.ehanmy.hospital.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Intent;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.CommitOrderContract;
import cn.ehanmy.hospital.mvp.model.entity.UserBean;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.GoodsConfirmBean;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.GoodsListBean;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberBean;
import cn.ehanmy.hospital.mvp.model.entity.placeOrder.GoodsBuyRequest;
import cn.ehanmy.hospital.mvp.model.entity.placeOrder.GoodsBuyResponse;
import cn.ehanmy.hospital.mvp.model.entity.response.GoodsConfirmResponse;
import cn.ehanmy.hospital.util.CacheUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


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

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void placeGoodsOrder() {
        Intent intent = mRootView.getActivity().getIntent();
        GoodsConfirmResponse goodsConfirmResponse = (GoodsConfirmResponse) intent.getSerializableExtra("order_info");
        String remark = intent.getStringExtra("remark");
        MemberBean memberBean = CacheUtil.getConstant(CacheUtil.CACHE_KEY_MEMBER);
        UserBean userBean = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        GoodsBuyRequest request = new GoodsBuyRequest();
        GoodsListBean goods = goodsConfirmResponse.getGoods();
        GoodsConfirmBean goodsConfirmBean = new GoodsConfirmBean();
        goodsConfirmBean.setSalePrice(goods.getSalePrice());
        goodsConfirmBean.setNums(goods.getNums());
        goodsConfirmBean.setMerchId(goods.getMerchId());
        goodsConfirmBean.setGoodsId(goods.getGoodsId());
        request.setGoods(goodsConfirmBean);
        request.setMemberId(memberBean.getMemberId());
        request.setMoney(goodsConfirmResponse.getMoney());
        request.setPrice(goodsConfirmResponse.getPrice());
        request.setPayMoney(goodsConfirmResponse.getPayMoney());
        request.setTotalPrice(goodsConfirmResponse.getTotalPrice());
        request.setRemark(remark);
        request.setToken(userBean.getToken());

        mModel.placeGoodsOrder(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<GoodsBuyResponse>(mErrorHandler) {
                    @Override
                    public void onNext(GoodsBuyResponse response) {
                        if (response.isSuccess()) {
                            mRootView.showPaySuccess(response);
                        }
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
