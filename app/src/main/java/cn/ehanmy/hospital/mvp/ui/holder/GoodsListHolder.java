package cn.ehanmy.hospital.mvp.ui.holder;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;

import io.reactivex.Observable;

import butterknife.BindView;
import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.GoodsListBean;
import cn.ehanmy.hospital.mvp.ui.activity.OrderConfirmActivity;

public class GoodsListHolder extends BaseHolder<GoodsListBean> {

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title2)
    TextView title2;
    @BindView(R.id.count)
    TextView count;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.buy)
    TextView buy;

    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用 Glide,使用策略模式,可替换框架


    @Override
    protected void onRelease() {
        image = null;
        title = null;
        title2 = null;
        count = null;
        price = null;
        buy = null;
    }

    public GoodsListHolder(View itemView) {
        super(itemView);
        mAppComponent = ArmsUtils.obtainAppComponentFromContext(itemView.getContext());
        mImageLoader = mAppComponent.imageLoader();

    }

    @Override
    public void setData(GoodsListBean data, int position){
        mImageLoader.loadImage(itemView.getContext(),
                ImageConfigImpl
                        .builder()
                        .url(data.getImage())
                        .imageView(image)
                        .build());
        Observable.just(data.getName())
                .subscribe(s -> title.setText(s));
        Observable.just(data.getGoodsSpecValue().getSpecValueName())
                .subscribe(s -> title2.setText(s));
        Observable.just(data.getSales())
                .subscribe(s -> count.setText(""+s));
        Observable.just(data.getSalePrice())
                .subscribe(s -> price.setText(""+s));

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArmsUtils.getContext(), OrderConfirmActivity.class);
                intent.putExtra(OrderConfirmActivity.KEY_FOR_GOODS_INFO,data);
                ArmsUtils.startActivity(intent);
            }
        });
    }
}
