# Integrate the ADFLY SDK V0.20

---

## Other Language
* [中文](README.md)

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
    
    implementation 'pub.adfly:adfly-sdk:0.20.+'
}
```

## Initialization

Initialize sdk at application startup

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

## InterstitialAd

**Notice**

1. Calling `InterstitialAd.loadAd()` can trigger the loading and caching of ads once

```java
InterstitialAd interstitialAd = InterstitialAd.getInstance("YOUR_UNIT_ID");
interstitialAd.setAdListener(new InterstitialAdListener() {
    @Override
    public void onAdLoadSuccess(AdflyAd ad) {
        if (interstitialAd.isReady()) {
            interstitialAd.show();
        }
    }

    @Override
    public void onAdLoadFailure(AdflyAd ad, AdError adError) {
        // It is recommended to do retry processing here, but the retry interval should not be too short
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 60 * 1000);
    }

    @Override
    public void onAdShowed(AdflyAd ad) {

    }

    @Override
    public void onAdShowError(AdflyAd ad, AdError adError) {

    }

    @Override
    public void onAdClosed(AdflyAd ad) {

    }

    @Override
    public void onAdClick(AdflyAd ad) {

    }
});
interstitialAd.loadAd();
```

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
        <com.adfly.sdk.interactive.InteractiveAdView
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
## Native Ads
### Load Ad
1. Create a native ad UnitId in the ADFly dashboard
2. Create a NativeAd object
3. Call NativeAd.loadAd() to request to load an ad

```java
NativeAd nativeAd = new NativeAd("YOUR_UNIT_ID");
nativeAd.setAdListener(new NativeAdListener() {
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
});
nativeAd.loadAd();
```

### Show Ad

Ads can be displayed after receiving the `onAdLoaded` callback

1. Create an xml layout using `NativeAdView` as rootView

**NativeAdView**：Used for ad impression

**MediaView**： Images, videos, etc. used to display ads

```xml
<com.adfly.sdk.nativead.NativeAdView>

    <TextView android:id="@+id/tv_title">
    
    <com.adfly.sdk.nativead.MediaView android:id="@+id/mediaview"/>
    
    <TextView android:id="@+id/tv_tag"/>

    <TextView  android:id="@+id/tv_sponsor"/>

    <TextView android:id="@+id/tv_body" />

    <Button android:id="@+id/btn_action"/>

</com.adfly.sdk.nativead.NativeAdView>
```

2. From `NativeAd`, you can get the **title**, **body**, **sponsor**, **Ad tag**, **Call to Action button** of the ad.
3. Call `NativeAd.showView(NativeAdView adView, MediaView mediaView, List<View> clickableViews)` to show the ad.

**clickableViews**：A list of Views that the user can use for clicks

```java
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
```
![](native_layout.png)

**View #1: title**

**View #2: MediaView**

**View #3: Ad tag**

**View #4: sponsor**

**View #5: Call to Action button**

## Banners

1. Create a Banner ad UnitId in the ADFly dashboard
2. Create a AdView object
3. show AdView to parent widget
4. Call AdView loadAd() to request to load an ad


```
AdView adView = new AdView(this, "YOUR_UNIT_ID", AdType.BANNER);

container.addView(mAdView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

adView.setAdListener(new AdListener() {
    @Override
    public void onError(AdflyAd ad, AdError error) {
    }

    @Override
    public void onAdLoaded(AdflyAd ad) {
    }

    @Override
    public void onAdLoadFailure(AdflyAd ad, AdError adError) {
    }

    @Override
    public void onAdClicked(AdflyAd ad) {
    }

    @Override
    public void onAdImpression(AdflyAd ad) {
    }
});
adView.loadAd()
```

## MRECs

1. Create a MREC ad UnitId in the ADFly dashboard
2. Create a AdView object
3. show AdView to parent widget
4. Call AdView loadAd() to request to load an ad

```
AdView adView = new AdView(this, "YOUR_UNIT_ID", AdType.MREC);

container.addView(mAdView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

adView.setAdListener(new AdListener() {
    @Override
    public void onError(AdflyAd ad, AdError error) {
    }

    @Override
    public void onAdLoaded(AdflyAd ad) {
    }

    @Override
    public void onAdLoadFailure(AdflyAd ad, AdError adError) {
    }

    @Override
    public void onAdClicked(AdflyAd ad) {
    }

    @Override
    public void onAdImpression(AdflyAd ad) {
    }
});
adView.loadAd()
```

## Privacy

SDK will collect client setting like Language、 Manufacturer、GAID, and report these to the server, We use these data to identify users. If you release APP to GooglePlay, Should post a privacy policy in both the designated field in the Play Developer Console and from within the Play distributed app itself.