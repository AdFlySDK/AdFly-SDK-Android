package com.adfly.sdk.demo;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.adfly.sdk.demo.interactivead.InteractiveAdActivity;
import com.adfly.sdk.demo.rewardedvideo.RewardedVideoActivity;

/**
 * Created by yejinbing on 2021/12/7.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.rewarded_video).setOnClickListener(v -> {
            startActivity(new Intent(v.getContext(), RewardedVideoActivity.class));
        });
        findViewById(R.id.interactive_ad).setOnClickListener(v -> {
            startActivity(new Intent(v.getContext(), InteractiveAdActivity.class));
        });
    }

}
