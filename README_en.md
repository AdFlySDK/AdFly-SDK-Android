# Integrate the ADFLY SDK V0.4

## Language
* ch [中文](README.md)

## Usage

### Dependency

1. Open your project and update the project’s `build.gradle` to include the following repositories.

```groovy
allprojects {
    repositories {
       // ... other repositories
    
        mavenCentral()
    }
}
```

2. Open your app module's `build.gradle` to include the following dependencies.

```groovy
dependencies {
    // ... other project dependencies
    
    implementation 'pub.adfly:adfly-sdk:0.4.+'
}
```

### Configuration

**Add http support**

* AndroidManifest.xml

```xml
	<application
        android:networkSecurityConfig="@xml/network_security_config"">

        <!-- Adfly uses apache http to download the video -->
        <uses-library android:name="org.apache.http.legacy" android:required="false" />
        
    </application>
```

* xml/network_security_config.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <base-config cleartextTrafficPermitted="true" />
</network-security-config>
```

## Initialization

Initialize in Application

```java

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
    
```

## RewardedVideo

**Notice**

1. Calling `RewardedVideoAd.loadAd()` can trigger the loading and caching of ads once

```java
RewardedVideoAd rewardedVideoAd = RewardedVideoAd.getInstance("YOUR_UNIT_ID");
rewardedVideoAd.setRewardedVideoListener(new RewardedVideoListener() {
    @Override
    public void onRewardedAdLoadSuccess(AdflyAd ad) {
        System.out.println("onRewardedAdLoadSuccess");
        if (rewardedVideoAd.isReady()) {
            rewardedVideoAd.show();
        }
    }

    @Override
    public void onRewardedAdLoadFailure(AdflyAd ad, AdError adError) {
        System.out.println("onRewardedAdLoadFailure: " + adError);
        // It is recommended to do retry processing here, but the retry interval should not be too short
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 60 * 1000);
    }

    @Override
    public void onRewardedAdShowed(AdflyAd ad) {
        System.out.println("onRewardedAdShowed");
    }

    @Override
    public void onRewardedAdShowError(AdflyAd ad, AdError adError) {
        System.out.println("onRewardedAdShowError: " + adError);
    }

    @Override
    public void onRewardedAdCompleted(AdflyAd ad) {
        System.out.println("onRewardedAdCompleted");
    }

    @Override
    public void onRewardedAdClosed(AdflyAd ad) {
        System.out.println("onRewardedAdClosed");
    }

    @Override
    public void onRewardedAdClick(AdflyAd ad) {
        System.out.println("onRewardedAdClick");
    }
});
rewardedVideoAd.loadAd();
```

### ErrorCode

| code | message | Description  |
| :--: | :--: |:--: |
| 5001 | TIMEOUT | Load timeout |
| 5002 | INVALID AD | |
| 5003 | NO_FILL |  No ads found. |
| 5004 | VIDEO_PLAYBACK_ERROR | Error playing a video. |
| 5005 | LOAD_FAILED |  Load failed |
| 5006 | NO_CONNECTION | No internet connection detected. |
| 5007 | VIDEO_CACHE_ERROR |  Error creating a cache to store downloaded videos. |
| 5008 | VIDEO_DOWNLOAD_ERROR | Error downloading video. |
| 5009 | VIDEO_IS_SHOWING_ERROR | Ad video is already showing. |


## InteractiveAd

### Update Your Activity Layout
Configure you `activity_main.xml` to add InteractiveAdView.

```xml
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">
    <RelativeLayout android:id="@+id/linearlayout"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
        <com.xb.interactivelibrary.InteractiveAdView
            android:id="@+id/tnteractive"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="20dp"/>
    </RelativeLayout>
</androidx.constraintlayout.widget.ConstraintLayout>
```

### Show Interactive

Interactive ad allows users play games and get kinds of coupons advertisers offered.

1. To show inertacive ad, you need to call `showAd("YOU UNIT ID")` as belows.

```
InteractiveAdView interactive = findViewById(R.id.tnteractive);
interactive.showAd(widgetId);
```

2. If you want adjust the size of interacive ad view, you can call `showAd(width, height, "YOU UNIT ID")`.

```java
InteractiveAdView interactive = findViewById(R.id.tnteractive);
interactive.showAd(120, 120, widgetId);
```

3. Sometimes you need hide the close button, you can call showAd with more params.

```java
InteractiveAdView interactive = findViewById(R.id.tnteractive);

/**
     * @param width view width
     * @param height view height
     * @param showCloseIcon show close icon
     */
interactive.showAd(120, 120, false, widgetId);
```

### Clear Interactive

If you want to clear ad, not closed by users, you can call this method:

```java
if (interactive != null) {
    interactive.setVisibility(View.GONE);
}
```