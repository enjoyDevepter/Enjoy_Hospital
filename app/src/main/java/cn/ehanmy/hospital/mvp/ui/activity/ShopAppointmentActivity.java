package cn.ehanmy.hospital.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.List;

import butterknife.BindView;
import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.di.component.DaggerShopAppointmentComponent;
import cn.ehanmy.hospital.di.module.ShopAppointmentModule;
import cn.ehanmy.hospital.mvp.contract.ShopAppointmentContract;
import cn.ehanmy.hospital.mvp.model.ShopAppointmentModel;
import cn.ehanmy.hospital.mvp.model.entity.ShopAppointment;
import cn.ehanmy.hospital.mvp.presenter.ShopAppointmentPresenter;
import cn.ehanmy.hospital.mvp.ui.adapter.ShopAppointmentListAdapter;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class ShopAppointmentActivity extends BaseActivity<ShopAppointmentPresenter> implements ShopAppointmentContract.View {

    @BindView(R.id.title_Layout)
    View title;

    @BindView(R.id.appointmentint)
    TextView appointment;
    @BindView(R.id.over)
    TextView over;
    @BindView(R.id.cancel)
    TextView cancel;
    @BindView(R.id.all)
    TextView all;
    @BindView(R.id.search_layout)
    View search_layout;
    @BindView(R.id.code)
    View code;
    @BindView(R.id.search_btn)
    View search;
    @BindView(R.id.clear_btn)
    View clear;
    @BindView(R.id.search_key)
    EditText searchKey;
    @BindView(R.id.contentList)
    RecyclerView contentList;
    private TextView currTextView;
    private int currType;
    private int normalColor = Color.parseColor("#333333");
    private int currColor = Color.parseColor("#3DBFE8");
    private View.OnClickListener onTypeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == currTextView.getId()) {
                return;
            }
            currTextView.setTextColor(normalColor);
            switch (v.getId()) {
                case R.id.appointmentint:
                    currTextView = appointment;
                    currType = ShopAppointmentModel.SEARCH_TYPE_APPOINTMENT;
                    break;
                case R.id.over:
                    currType = ShopAppointmentModel.SEARCH_TYPE_OVER;
                    currTextView = over;
                    break;
                case R.id.cancel:
                    currTextView = cancel;
                    currType = ShopAppointmentModel.SEARCH_TYPE_CANCEL;
                    break;
                case R.id.all:
                    currType = ShopAppointmentModel.SEARCH_TYPE_ALL;
                    currTextView = all;
                    break;
            }

            currTextView.setTextColor(currColor);
        }
    };
    private View.OnClickListener onSearchClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.search_btn:
                    doSearch();
                    break;
                case R.id.clear_btn:
                    searchKey.setText("");
                    contentList.setAdapter(null);
                    break;
            }
            hideImm();
        }
    };

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerShopAppointmentComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .shopAppointmentModule(new ShopAppointmentModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_shop_appointment; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        new TitleUtil(title, this, "店铺预约");
        currTextView = appointment;
        appointment.setTextColor(currColor);

        appointment.setOnClickListener(onTypeClickListener);
        over.setOnClickListener(onTypeClickListener);
        all.setOnClickListener(onTypeClickListener);
        cancel.setOnClickListener(onTypeClickListener);

        code.setVisibility(View.GONE);
        search.setOnClickListener(onSearchClickListener);
        clear.setOnClickListener(onSearchClickListener);
        contentList.setLayoutManager(new LinearLayoutManager(this));
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

    private void doSearch() {
        String s = searchKey.getText().toString();
        if (TextUtils.isEmpty(s)) {
            showMessage("请输入搜索关键字后重试");
            return;
        }

        mPresenter.doSearch(s, currType);
    }

    public void updateList(List<ShopAppointment> orderList) {
        ShopAppointmentListAdapter adapter = new ShopAppointmentListAdapter(orderList);
        adapter.setOnChildItemClickLinstener(new OnChildItemClickLinstener() {
            @Override
            public void onChildItemClick(View v, ViewName viewname, int position) {
                if (position == 0) {
                    return;
                }
                Intent intent;
                switch (viewname) {
                    case DETAIL:
                        intent = new Intent(ShopAppointmentActivity.this, ShopAppointmentInfoActivity.class);
                        intent.putExtra(ShopAppointmentInfoActivity.KEY_FOR_DATA, adapter.getItem(position));
                        launchActivity(intent);
                        break;
                    case RELATED:
                        intent = new Intent(ShopAppointmentActivity.this, RelatedListActivity.class);
                        launchActivity(intent);
                        break;
                    case CANCEL:
                        break;
                }
            }
        });
        contentList.setAdapter(adapter);
    }
}
