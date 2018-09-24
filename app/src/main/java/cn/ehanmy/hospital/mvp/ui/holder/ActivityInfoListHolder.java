package cn.ehanmy.hospital.mvp.ui.holder;


import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.mvp.model.entity.activity.ActivityInfoBean;

public class ActivityInfoListHolder extends BaseHolder<ActivityInfoBean> {

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.content)
    WebView content;

    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;


    public ActivityInfoListHolder(View itemView) {
        super(itemView);
        mAppComponent = ArmsUtils.obtainAppComponentFromContext(itemView.getContext());
        mImageLoader = mAppComponent.imageLoader();
    }

    @Override
    public void setData(ActivityInfoBean data, int position) {

        name.setText(data.getTitle());
        content.loadData(data.getContent(),"text/html","utf-8");

        mImageLoader.loadImage(itemView.getContext(),
                ImageConfigImpl
                        .builder()
                        .url(data.getImage())
                        .imageView(image)
                        .build());
    }

    @Override
    protected void onRelease() {
        this.name = null;
        this.content = null;
        this.image = null;
    }
}

