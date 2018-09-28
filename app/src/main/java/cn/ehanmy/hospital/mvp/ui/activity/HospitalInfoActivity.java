package cn.ehanmy.hospital.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.integration.cache.Cache;
import com.jess.arms.utils.ArmsUtils;

import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.di.component.DaggerHospitalInfoComponent;
import cn.ehanmy.hospital.di.module.HospitalInfoModule;
import cn.ehanmy.hospital.mvp.contract.HospitalInfoContract;
import cn.ehanmy.hospital.mvp.model.entity.hospital.HospitaInfoBean;
import cn.ehanmy.hospital.mvp.model.entity.hospital.HospitalInfoResponse;
import cn.ehanmy.hospital.mvp.presenter.HospitalInfoPresenter;
import cn.ehanmy.hospital.mvp.ui.widget.CustomDialog;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class HospitalInfoActivity extends BaseActivity<HospitalInfoPresenter> implements HospitalInfoContract.View, View.OnClickListener {

    @BindView(R.id.back)
    View backV;
    @BindView(R.id.title)
    TextView titleTV;
    @BindView(R.id.modify_image)
    View imageV;
    @BindView(R.id.image)
    ImageView imageIV;
    @BindView(R.id.name)
    TextView nameTV;
    @BindView(R.id.phone)
    TextView phoneTV;
    @BindView(R.id.time)
    TextView timeTV;  // 早9:00-晚18:00
    @BindView(R.id.address)
    TextView addressTV;
    @BindView(R.id.modify_info)
    View infoV;
    @Inject
    ImageLoader mImageLoader;

    HospitaInfoBean hospitaInfoBean;

    CustomDialog dialog = null;

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
        titleTV.setText("医院信息");
        backV.setOnClickListener(this);
        imageV.setOnClickListener(this);
        infoV.setOnClickListener(this);
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

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public Cache getCache() {
        return provideCache();
    }

    @Override
    public void updateUI(HospitalInfoResponse response) {
        hospitaInfoBean = response.getHospital();
        nameTV.setText(hospitaInfoBean.getName());
        phoneTV.setText("联系电话：" + hospitaInfoBean.getTellphone());
        timeTV.setText("营业时间：早" + hospitaInfoBean.getStarTime() + "-晚" + hospitaInfoBean.getEndTime());
        addressTV.setText(hospitaInfoBean.getAddress());

        mImageLoader.loadImage(this,
                ImageConfigImpl
                        .builder()
                        .url(hospitaInfoBean.getImage())
                        .placeholder(R.drawable.place_holder_img)
                        .imageView(imageIV)
                        .build());
    }

    @Override
    public void changeOk() {
        if(dialog != null){
            dialog.dismiss();
            dialog = null;
        }

    }


    private void showChangeDialog() {
        dialog = CustomDialog.create(getSupportFragmentManager())
                .setViewListener(new CustomDialog.ViewListener() {
                    @Override
                    public void bindView(View view) {
                        ((TextView) view.findViewById(R.id.name)).setText("本店名称:" + hospitaInfoBean.getName());
                        ((TextView) view.findViewById(R.id.address)).setText("地址:" + hospitaInfoBean.getAddress());
                        EditText phone = view.findViewById(R.id.phone);
                        phone.setText(hospitaInfoBean.getTellphone());
                        TextView startTime = view.findViewById(R.id.start_time);
                        startTime.setText(hospitaInfoBean.getStarTime());
                        TextView endTime = view.findViewById(R.id.end_time);
                        endTime.setText(hospitaInfoBean.getEndTime());
                        startTime.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new TimePickerView.Builder(HospitalInfoActivity.this, new TimePickerView.OnTimeSelectListener() {

                                    @Override
                                    public void onTimeSelect(Date date, View v) {
                                        startTime.setText(date.getHours() + ":" + date.getMinutes());
                                        showMessage(date.getHours() + ":" + date.getMinutes());
                                    }
                                })
                                        .isDialog(true)
                                        .setType(new boolean[]{false, false, false, true, true, false})
                                        .build().show();

                            }
                        });
                        endTime.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new TimePickerView.Builder(HospitalInfoActivity.this, new TimePickerView.OnTimeSelectListener() {

                                    @Override
                                    public void onTimeSelect(Date date, View v) {
                                        endTime.setText(date.getHours() + ":" + date.getMinutes());
                                        showMessage(date.getHours() + ":" + date.getMinutes());
                                    }
                                })
                                        .isDialog(true)
                                        .setType(new boolean[]{false, false, false, true, true, false})
                                        .build().show();
                            }
                        });
                        view.findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (ArmsUtils.isEmpty(phone.getText().toString())) {
                                    showMessage("请输入联系电话");
                                    return;
                                }
                                if (ArmsUtils.isEmpty(startTime.getText().toString())) {
                                    showMessage("请输入开始营业时间");
                                    return;
                                }
                                if (ArmsUtils.isEmpty(endTime.getText().toString())) {
                                    showMessage("请输入结束营业时间");
                                    return;
                                }
                                provideCache().put("tellphone", phone.getText().toString());
                                provideCache().put("startTime", startTime.getText());
                                provideCache().put("endTime", endTime.getText());
                                mPresenter.changeHospitalInfo();
                            }
                        });

                    }
                })
                .setLayoutRes(R.layout.dialog_change_hospital_info)
                .setWidth(ArmsUtils.getDimens(this, R.dimen.modify_hospital_width))
                .setHeight(ArmsUtils.getDimens(this, R.dimen.modify_hospital_height))
                .setDimAmount(0.5f)
                .isCenter(true)
                .show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                killMyself();
                break;
            case R.id.modify_image:
                ArmsUtils.startActivity(ChangeHospitalImageActivity.class);
                break;
            case R.id.modify_info:
                showChangeDialog();
                break;
        }
    }
}
