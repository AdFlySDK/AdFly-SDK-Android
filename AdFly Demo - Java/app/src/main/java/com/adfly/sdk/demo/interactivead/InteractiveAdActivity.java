package com.adfly.sdk.demo.interactivead;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.adfly.sdk.core.AdFlySdk;
import com.adfly.sdk.demo.R;
import com.adfly.sdk.interactive.InteractiveAdView;

/**
 * Created by yejinbing on 2021/12/7.
 */
public class InteractiveAdActivity extends AppCompatActivity {

    private TextView resultTv;
    Button requestAdBt;
    InteractiveAdView interactive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interactivead);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final EditText wEt = findViewById(R.id.widget_id_et);
        requestAdBt = findViewById(R.id.request_ad_bt);

        resultTv = findViewById(R.id.result_tv);
        interactive = findViewById(R.id.tnteractive);

        if (AdFlySdk.isInitialized()) {
            resultTv.setText("init success");
            requestAdBt.setVisibility(View.VISIBLE);
        } else {
            resultTv.setText("init failure");
        }

        requestAdBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interactive.showAd(wEt.getText().toString());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
