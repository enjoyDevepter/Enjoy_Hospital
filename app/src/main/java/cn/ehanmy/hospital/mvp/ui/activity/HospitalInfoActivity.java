package cn.ehanmy.hospital.mvp.ui.activity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.integration.cache.Cache;
import com.jess.arms.utils.ArmsUtils;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.inject.Inject;

import butterknife.BindView;
import cn.ehanmy.hospital.di.component.DaggerHospitalInfoComponent;
import cn.ehanmy.hospital.di.module.HospitalInfoModule;
import cn.ehanmy.hospital.mvp.contract.HospitalInfoContract;
import cn.ehanmy.hospital.mvp.model.entity.hospital.HospitaInfoBean;
import cn.ehanmy.hospital.mvp.presenter.HospitalInfoPresenter;

import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.util.CacheUtil;
import cn.ehanmy.hospital.util.EdittextUtil;


import static com.jess.arms.utils.Preconditions.checkNotNull;


public class HospitalInfoActivity extends BaseActivity<HospitalInfoPresenter> implements HospitalInfoContract.View {

    @BindView(R.id.title_Layout)
    View title;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.time)
    TextView time;  // 早9:00-晚18:00
    private static final String time_format = "早%s-晚%s";
    @BindView(R.id.addr)
    TextView addr;
    @Inject
    ImageLoader mImageLoader;

    @BindView(R.id.edit_parent)
    View edit_parent;
    @BindView(R.id.name_double)
    TextView name_double;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.addr_content)
    TextView addr_content;

    @BindView(R.id.et_time_start)
    TextView et_time_start;
    @BindView(R.id.et_time_end)
    TextView et_time_end;

    @BindView(R.id.change2)
    View change2;
    @BindView(R.id.change1)
    View change1;
    @BindView(R.id.save)
    View save;

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
        change1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArmsUtils.startActivity(ChangeHospitalImageActivity.class);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EdittextUtil.isEmpty(et_phone)) {
                    ArmsUtils.makeText(ArmsUtils.getContext(),"请输入电话号码");
                    return;
                }

                if(TextUtils.isEmpty(et_time_start.getText())){
                    ArmsUtils.makeText(ArmsUtils.getContext(),"请设置开始营业时间");
                    return;
                }

                if(TextUtils.isEmpty(et_time_end.getText())){
                    ArmsUtils.makeText(ArmsUtils.getContext(),"请设置结束营业时间");
                    return;
                }

                mPresenter.changeHospitalInfo(et_phone.getText()+"",et_time_start.getText()+"",et_time_end.getText()+"");

            }
        });
        edit_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_parent.setVisibility(View.GONE);
            }
        });
        HospitaInfoBean h = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER_HOSPITAL_INFO);
        updateHosptial(h);

        change2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_parent.setVisibility(View.VISIBLE);
            }
        });
        et_time_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog time=new TimePickerDialog(HospitalInfoActivity.this, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        DecimalFormat decimalFormat = new DecimalFormat("00");
                        et_time_start.setText(
                                String.format("%s:%s",decimalFormat.format(hourOfDay),decimalFormat.format(minute)));
                    }
                }, 0,0, true);
                time.show();
            }
        });

        et_time_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog time=new TimePickerDialog(HospitalInfoActivity.this, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        DecimalFormat decimalFormat = new DecimalFormat("00");
                        et_time_end.setText(
                                String.format("%2s:%2s",decimalFormat.format(hourOfDay),decimalFormat.format(minute)));
                    }
                }, 0,0, true);
                time.show();
            }
        });

//        mImageLoader.loadImage(this,
//                ImageConfigImpl
//                        .builder()
//                        .url(hospitaInfoBean.get)
//                        .imageView(image)
//                        .build());

    }

    public void updateHosptial(HospitaInfoBean hospitaInfoBean){
        name.setText(hospitaInfoBean.getName());
        name_double.setText(hospitaInfoBean.getName());
        phone.setText(hospitaInfoBean.getTellphone());
        et_phone.setText(hospitaInfoBean.getTellphone());
        time.setText(String.format(time_format,hospitaInfoBean.getStarTime(),hospitaInfoBean.getEndTime()));
        addr.setText(hospitaInfoBean.getAddress());
        addr_content.setText(hospitaInfoBean.getAddress());
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

    public void changeHospitalInfoOk(boolean isOk){
        if(isOk){
            ArmsUtils.makeText(this,"修改成功");
            HospitaInfoBean h = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER_HOSPITAL_INFO);
            updateHosptial(h);
        }else{

        }
        edit_parent.setVisibility(View.GONE);

    }


}
