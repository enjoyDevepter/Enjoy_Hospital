package cn.ehanmy.hospital.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.PermissionUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import cn.ehanmy.hospital.di.component.DaggerChangeHospitalImageComponent;
import cn.ehanmy.hospital.di.module.ChangeHospitalImageModule;
import cn.ehanmy.hospital.mvp.contract.ChangeHospitalImageContract;
import cn.ehanmy.hospital.mvp.presenter.ChangeHospitalImagePresenter;

import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.util.ImageUploadUtils;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;


import static com.jess.arms.utils.Preconditions.checkNotNull;


public class ChangeHospitalImageActivity extends BaseActivity<ChangeHospitalImagePresenter> implements ChangeHospitalImageContract.View {

    @BindView(R.id.go_to_gallry)
    View go_to_gallry;
    @BindView(R.id.image)
    ImageView image;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerChangeHospitalImageComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .changeHospitalImageModule(new ChangeHospitalImageModule(this))
                .build()
                .inject(this);
    }

    private BitmapFactory.Options getBitampOptions(String path) {
        BitmapFactory.Options mOptions = new BitmapFactory.Options();
        mOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, mOptions);
        return mOptions;
    }

    private String generateCropImgFilePath() {
        String mCameraFileDirPath = Environment.getExternalStorageDirectory() + File.separator + "camera";
        File mCameraFileDir = new File(mCameraFileDirPath);
        if (!mCameraFileDir.exists()) {
            mCameraFileDir.mkdirs();
        }
        mCropImgFilePath = mCameraFileDirPath + File.separator + System.currentTimeMillis() + ".jgp";
        return mCropImgFilePath;
    }

    private String mCropImgFilePath = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GALLERY_OPEN_REQUEST_CODE:
                    if (data != null) {
                        String mGalleryPath = ImageUploadUtils.parseGalleryPath(this, data.getData());

                        BitmapFactory.Options mOptions = getBitampOptions(mGalleryPath);
                        generateCropImgFilePath();
                        ImageUploadUtils.startCropImage(
                                this,
                                mGalleryPath,
                                mCropImgFilePath,
                                mOptions.outWidth,
                                mOptions.outHeight,
                                image.getWidth(),
                                image.getHeight(),
                                CROP_IMAGE_REQUEST_CODE);
                    }
                    break;
                case CROP_IMAGE_REQUEST_CODE:
                    mPresenter.uploadImage(new File(mCropImgFilePath));
                    image.setImageBitmap(BitmapFactory.decodeFile(mCropImgFilePath));
                    break;
            }
        }

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_change_hospital_image; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        go_to_gallry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlbum();
            }
        });
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


    private void openAlbum() {
        //请求外部存储权限用于适配android6.0的权限管理机制
        PermissionUtil.externalStorage(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                //request permission success, do something.
            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {
            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
            }
        }, mRxPermissions, mErrorHandler);

        ImageUploadUtils.startGallery(this, GALLERY_OPEN_REQUEST_CODE);

    }

    private static final int GALLERY_OPEN_REQUEST_CODE = 1;
    private static final int CROP_IMAGE_REQUEST_CODE = 2;
    private static final int CAMERA_OPEN_REQUEST_CODE = 3;

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    RxPermissions mRxPermissions;

    public Activity getActivity(){
        return this;
    }
}
