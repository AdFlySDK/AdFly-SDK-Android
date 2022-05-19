package com.adfly.sdk.demo.splash;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import androidx.annotation.Nullable;
import com.adfly.sdk.core.AdError;
import com.adfly.sdk.core.AdflyAd;
import com.adfly.sdk.demo.R;
import com.adfly.sdk.splash.SplashAd;
import com.adfly.sdk.splash.SplashAdListener;

/**
 * Created by yejinbing on 2022/2/15.
 */
public class SplashAdActivity extends Activity {

    private FrameLayout adContainer;
    private SplashAd splashAd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode =
                    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }

        adContainer = findViewById(R.id.ad_container);

        splashAd = SplashAd.getInstance("YOUR_UNIT_ID");
        splashAd.setAdListener(new SplashAdListener() {

            @Override
            public void onError(AdflyAd ad, AdError error) {
                System.out.println("onError");
            }

            @Override
            public void onAdLoaded(AdflyAd ad) {
                System.out.println("onAdLoaded");
                if (splashAd.isReady()) {
                    splashAd.show(SplashAdActivity.this, adContainer);
                }
            }

            @Override
            public void onAdLoadFailure(AdflyAd ad, AdError adError) {
                System.out.println("onAdLoadFailure: " + adError);
            }

            @Override
            public void onAdClicked(AdflyAd ad) {
                System.out.println("onAdClicked");
            }

            @Override
            public void onAdImpression(AdflyAd ad) {
                System.out.println("onAdImpression");
            }

            @Override
            public void onAdClosed(AdflyAd ad) {
                System.out.println("onAdClosed");
                finish();
            }
        });
        splashAd.loadAd();
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
        splashAd.destroy();
    }

}
