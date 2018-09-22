package cn.ehanmy.hospital.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.integration.cache.Cache;
import com.jess.arms.utils.ArmsUtils;

import org.w3c.dom.Text;

import javax.inject.Inject;

import butterknife.BindView;
import cn.ehanmy.hospital.di.component.DaggerHospitalInfoComponent;
import cn.ehanmy.hospital.di.module.HospitalInfoModule;
import cn.ehanmy.hospital.mvp.contract.HospitalInfoContract;
import cn.ehanmy.hospital.mvp.model.entity.hospital.HospitaInfoBean;
import cn.ehanmy.hospital.mvp.presenter.HospitalInfoPresenter;

import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.util.CacheUtil;


import static com.jess.arms.utils.Preconditions.checkNotNull;


public class HospitalInfoActivity extends BaseActivity<HospitalInfoPresenter> implements HospitalInfoContract.View {

    @BindView(R.id.title_Layout)
    View title;

    @Inject
    ImageLoader mImageLoader;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerHospitalInfoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .hospitalInfoModule(new HospitalInfoModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_hospital_info; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        new TitleUtil(title,this,"医院信息");
        HospitaInfoBean hospitaInfoBean = (HospitaInfoBean) CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER_HOSPITAL_INFO);

//
//        mImageLoader.loadImage(this,
//                ImageConfigImpl
//                        .builder()
//                        .url(goodsOrderBean.getImage())
//                        .imageView(image)
//                        .build());

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }
}
