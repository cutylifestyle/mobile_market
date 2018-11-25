package com.sixin.police.market.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.sixin.police.market.R;
import com.sixin.police.market.base.BaseActivity;
import com.sixin.police.market.widget.banner.Banner;
import com.sixin.police.market.widget.banner.transformers.Transformer;

import java.util.List;

import butterknife.BindView;

/**
 * app预览图的activity
* */
public class AppPreImagesActivity extends BaseActivity {

    private static final String TAG = "AppPreImagesActTAG";
    /**
     * Bundle中的key值
     * */
    public static final String KEY_PRE_IMGS = "imgs";
    public static final String KEY_POSITION = "position";

    @Nullable
    @BindView(R.id.banner_pre_imgs)
    Banner mBannerPreImgs;

    @Nullable
    @BindView(R.id.img_back_detail)
    ImageView mImgBackDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_app_pre_images);
        mImgBackDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getBundle();
    }

    private void getBundle() {
        Intent intent = getIntent();
        List<String> imgs = (List<String>) intent.getSerializableExtra(KEY_PRE_IMGS);
        Log.d(TAG, imgs.toString());
        int position = intent.getIntExtra(KEY_POSITION,-1);
        setData(imgs, position);
    }

    private void setData(List<String> imgs, int position) {
        if (imgs != null && imgs.size() > 0 && position >=0 && position < imgs.size()) {
            mBannerPreImgs.setData(R.layout.item_pre_img,imgs,null);
            mBannerPreImgs.setPageTransformer(Transformer.Zoom);
            mBannerPreImgs.loadImage(new Banner.XBannerAdapter() {
                @Override
                public void loadBanner(Banner banner, Object model, View view, int position) {
                    if (model instanceof String) {
                        SimpleDraweeView sdvPreImg = view.findViewById(R.id.sdv_pre_img);
                        sdvPreImg.setImageURI((String) model);
                    }
                }
            });
            mBannerPreImgs.getViewPager().setCurrentItem(position);
        }
    }

    @Override
    protected void setStatusBarColor() {
    }
}
