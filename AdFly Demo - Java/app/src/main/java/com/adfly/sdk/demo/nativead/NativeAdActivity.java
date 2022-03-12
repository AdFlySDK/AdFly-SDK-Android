package com.adfly.sdk.demo.nativead;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.adfly.sdk.core.AdError;
import com.adfly.sdk.core.AdflyAd;
import com.adfly.sdk.demo.R;
import com.adfly.sdk.nativead.MediaView;
import com.adfly.sdk.nativead.NativeAd;
import com.adfly.sdk.nativead.NativeAdListener;
import com.adfly.sdk.nativead.NativeAdView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yejinbing on 2022/2/15.
 */
public class NativeAdActivity extends AppCompatActivity {

    private Button btnLoad;

    private NativeAd mNativeAd;

    private FrameLayout adContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_nativead);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnLoad = findViewById(R.id.button_load);
        adContainer = findViewById(R.id.ad_container);

        btnLoad.setOnClickListener(v -> {
            btnLoad.setEnabled(false);
            loadNativeAd();
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mNativeAd != null) {
            mNativeAd.destroy();
        }
    }

    private void loadNativeAd() {
        mNativeAd = new NativeAd("YOUR_UNIT_ID");
        mNativeAd.setAdListener(new NativeAdListener() {
            @Override
            public void onMediaDownloaded(AdflyAd ad) {
                System.out.println("onMediaDownloaded");
            }

            @Override
            public void onError(AdflyAd ad, AdError error) {
                System.out.println("onError: " + error);
            }

            @Override
            public void onAdLoaded(AdflyAd ad) {
                System.out.println("onAdLoaded");
                runOnUiThread(() -> {
                    Toast.makeText(NativeAdActivity.this, "onAdLoaded", Toast.LENGTH_SHORT).show();
                    btnLoad.setEnabled(true);

                    showNativeAd(mNativeAd);
                });
            }

            @Override
            public void onAdLoadFailure(AdflyAd ad, AdError adError) {
                System.out.println("onAdLoadFailure: " + adError);
                runOnUiThread(() -> {
                    Toast.makeText(NativeAdActivity.this, "onAdLoadFailure: " + adError, Toast.LENGTH_SHORT).show();
                    btnLoad.setEnabled(true);
                });
            }

            @Override
            public void onAdClicked(AdflyAd ad) {
                System.out.println("onAdClicked");
            }

            @Override
            public void onAdImpression(AdflyAd ad) {
                System.out.println("onAdImpression");
            }
        });

        mNativeAd.loadAd();
    }

    private void showNativeAd(NativeAd nativeAd) {
        adContainer.removeAllViews();

        View layout = getLayoutInflater().inflate(R.layout.layout_nativead_view, adContainer, false);
        adContainer.addView(layout, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        NativeAdView adView = layout.findViewById(R.id.adview);

        TextView tvTitle = adView.findViewById(R.id.tv_title);
        TextView tvBody = adView.findViewById(R.id.tv_body);
        TextView tvSponsor = adView.findViewById(R.id.tv_sponsor);
        TextView tvTag = adView.findViewById(R.id.tv_tag);
        Button btnAction = adView.findViewById(R.id.btn_action);
        MediaView mediaView = adView.findViewById(R.id.mediaview);

        tvTitle.setText(nativeAd.getTitle());
        tvBody.setText(nativeAd.getBody());
        tvSponsor.setText(nativeAd.getSponsor());
        if (!TextUtils.isEmpty(nativeAd.getTag())) {
            tvTag.setText(nativeAd.getTag());
            tvTag.setVisibility(View.VISIBLE);
        } else {
            tvTag.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(nativeAd.getCallToAction())) {
            btnAction.setText(nativeAd.getCallToAction());
            btnAction.setVisibility(View.VISIBLE);
        } else {
            btnAction.setVisibility(View.GONE);
        }

        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(layout);
        clickableViews.add(mediaView);
        clickableViews.add(btnAction);
        nativeAd.showView(adView, mediaView, clickableViews);
    }

}
