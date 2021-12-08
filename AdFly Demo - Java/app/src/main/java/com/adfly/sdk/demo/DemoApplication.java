package com.adfly.sdk.demo;

import android.app.Application;
import com.adfly.sdk.core.AdFlySdk;
import com.adfly.sdk.core.SdkConfiguration;
import com.adfly.sdk.core.SdkInitializationListener;

/**
 * Created by yejinbing on 2021/11/23.
 */
public class DemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SdkConfiguration configuration = new SdkConfiguration.Builder()
                .appKey("YOUR_APP_KEY")
                .appSecret("YOUR_APP_SECRET")
                .build();
        AdFlySdk.initialize(this, configuration, new SdkInitializationListener() {
            @Override
            public void onInitializationFinished() {

            }
        });
    }

}
