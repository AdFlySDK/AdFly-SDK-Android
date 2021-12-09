package com.adfly.sdk.demo.rewardedvideo;

import android.os.Bundle;
import com.adfly.sdk.core.AdError;
import com.adfly.sdk.core.AdflyAd;
import com.adfly.sdk.demo.R;
import com.adfly.sdk.rewardedvideo.RewardedVideoAd;
import com.adfly.sdk.rewardedvideo.RewardedVideoListener;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class RewardedVideoActivity extends AppCompatActivity {

    private RewardedVideoAd rewardedVideoAd;

    private Button btnLoad;
    private Button btnShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewardedvideo);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnLoad = findViewById(R.id.button_load);
        btnShow = findViewById(R.id.button_show);

        btnLoad.setOnClickListener(v -> {
            btnLoad.setEnabled(false);
            btnShow.setEnabled(false);

            loadVideo();
        });
        btnShow.setOnClickListener(v -> {
            new Thread(() -> {
                if (rewardedVideoAd != null && rewardedVideoAd.isReady()) {
                    rewardedVideoAd.show();
                }
            }).start();
        });

        btnShow.setEnabled(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (rewardedVideoAd != null) {
            rewardedVideoAd.setRewardedVideoListener(null);
        }
    }

    private void loadVideo() {
        rewardedVideoAd = RewardedVideoAd.getInstance("YOUR_UNIT_ID");
        rewardedVideoAd.setRewardedVideoListener(new RewardedVideoListener() {
            @Override
            public void onRewardedAdLoadSuccess(AdflyAd ad) {
                System.out.println("onRewardedAdLoadSuccess");
                runOnUiThread(() -> {
                    Toast.makeText(RewardedVideoActivity.this, "onRewardedAdLoadSuccess", Toast.LENGTH_SHORT).show();
                    btnLoad.setEnabled(true);
                    btnShow.setEnabled(true);
                });
            }

            @Override
            public void onRewardedAdLoadFailure(AdflyAd ad, AdError adError) {
                System.out.println("onRewardedAdLoadFailure: " + adError);
                runOnUiThread(() -> {
                    Toast.makeText(RewardedVideoActivity.this, "onRewardedAdLoadFailure: " + adError, Toast.LENGTH_SHORT).show();
                    btnLoad.setEnabled(true);
                    btnShow.setEnabled(false);
                });
                // 建议在这里做重试处理，但重试时间间隔不要太短了
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                }, 60 * 1000);
            }

            @Override
            public void onRewardedAdShowed(AdflyAd ad) {
                System.out.println("onRewardedAdShowed");
                runOnUiThread(() -> {
                    Toast.makeText(RewardedVideoActivity.this, "onRewardedAdShowed", Toast.LENGTH_SHORT).show();
                    btnShow.setEnabled(false);
                });
            }

            @Override
            public void onRewardedAdShowError(AdflyAd ad, AdError adError) {
                System.out.println("onRewardedAdShowError: " + adError);
                runOnUiThread(() -> {
                    Toast.makeText(RewardedVideoActivity.this, "onRewardedAdShowError: " + adError, Toast.LENGTH_SHORT).show();
                    btnShow.setEnabled(false);
                });
            }

            @Override
            public void onRewardedAdCompleted(AdflyAd ad) {
                System.out.println("onRewardedAdCompleted");
                runOnUiThread(() -> {
                    Toast.makeText(RewardedVideoActivity.this, "onRewardedAdCompleted", Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onRewardedAdClosed(AdflyAd ad) {
                System.out.println("onRewardedAdClosed");
                runOnUiThread(() -> {
                    Toast.makeText(RewardedVideoActivity.this, "onRewardedAdClosed", Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onRewardedAdClick(AdflyAd ad) {
                System.out.println("onRewardedAdClick");
                runOnUiThread(() -> {
                    Toast.makeText(RewardedVideoActivity.this, "onRewardedAdClick", Toast.LENGTH_SHORT).show();
                });
            }
        });
        System.out.println("hasRewardedVideo: " + rewardedVideoAd.hasRewardedVideo());
        rewardedVideoAd.loadAd();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}