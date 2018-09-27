package cn.ehanmy.hospital.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import javax.inject.Inject;

import butterknife.BindView;
import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.di.component.DaggerMainComponent;
import cn.ehanmy.hospital.di.module.MainModule;
import cn.ehanmy.hospital.mvp.contract.MainContract;
import cn.ehanmy.hospital.mvp.presenter.MainPresenter;
import cn.ehanmy.hospital.mvp.ui.adapter.MainAdapter;
import cn.ehanmy.hospital.mvp.ui.widget.SpacesItemDecoration;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View, View.OnClickListener, DefaultAdapter.OnRecyclerViewItemClickListener {

    @BindView(R.id.back)
    View backV;
    @BindView(R.id.title)
    TextView titleTV;
    @BindView(R.id.setting)
    View settingV;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    MainAdapter mAdapter;

    private void showResolution(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        StringBuilder sb = new StringBuilder();
        sb.append("Resolution: "+dm.widthPixels+","+dm.heightPixels);
        sb.append("\n");
        sb.append("valuesType = "+getResources().getString(R.string.values_type));
        sb.append("\n");
        sb.append("density = "+dm.density);
        sb.append("\n");

        Configuration mConfiguration = this.getResources().getConfiguration(); //获取设置的配置信息
        int ori = mConfiguration.orientation; //获取屏幕方向
        String orientationStr = "";
        if(ori == Configuration.ORIENTATION_LANDSCAPE){
            orientationStr = "land";
        }else if(ori == Configuration.ORIENTATION_PORTRAIT){
            orientationStr = "port";
        }else{
            orientationStr = ori + "";
        }
        sb.append("orientation = "+orientationStr);

        Toast.makeText(this, sb.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        showResolution();
        titleTV.setText("医院中心");
        settingV.setVisibility(View.VISIBLE);
        settingV.setOnClickListener(this);
        backV.setVisibility(View.GONE);
        recyclerView.addItemDecoration(new SpacesItemDecoration(0, ArmsUtils.getDimens(ArmsUtils.getContext(), R.dimen.main_item_space)));
        ArmsUtils.configRecyclerView(recyclerView, mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting:
                ArmsUtils.startActivity(SafeSettingActivity.class);
                break;
        }
    }

    @Override
    public void onItemClick(View view, int viewType, Object data, int position) {
        Class<? extends Activity> targetActivity = null;
        switch (position) {
            case 0:
                targetActivity = BuyCenterActivity.class;
                break;
            case 1:
                // 订单中心
                targetActivity = OrderFormCenterActivity.class;
                break;
            case 2:
                targetActivity = ShopAppointmentActivity.class;
                break;
            case 3:
                targetActivity = UserAppointmentActivity.class;
                break;
            case 4:
                targetActivity = ActivityInfoActivity.class;
                break;
            case 5:
                targetActivity = HospitalInfoActivity.class;
                break;
            case 6:
                targetActivity = ProjectSettingActivity.class;
                break;
        }

        if (targetActivity == null) {
            showMessage("功能尚未实现");
        } else {
            ArmsUtils.startActivity(targetActivity);
        }
    }
}
