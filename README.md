# ADFLY SDK 集成文档 V0.14

---

## 其他语言
*  [English](README_en.md)

## 使用方式

### 添加依赖库

1. 在 project 根目录下的 `build.gradle` 文件中添加 mavenCentral() 作为依赖库的源

```groovy
allprojects {
    repositories {
       // ... other repositories
    
        mavenCentral()
    }
}
```

2. 在 app 模块的 `build.gradle` 中添加 adfly 依赖

```groovy
dependencies {
    // ... other project dependencies
    
    implementation 'pub.adfly:adfly-sdk:0.14.+'
}
```

## 初始化

在Application中进行初始化

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

## 激励视频

**注意**

1. 调用 `RewardedVideoAd.loadAd()` 可以触发一次广告的加载和缓存

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

### 错误码

| code | message | 说明  |
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


## 互动广告

### 修改 Activity Layout
在页面的 xml 中添加 InteractiveAdView.

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

### 显示广告

1. 调用 `showAd("YOU UNIT ID")` 即可以开始显示。

```
InteractiveAdView interactive = findViewById(R.id.tnteractive);
interactive.showAd(widgetId);
```

2. 如果想显示自定义大小，可以使用方式： `showAd(width, height, "YOU UNIT ID")`

```java
InteractiveAdView interactive = findViewById(R.id.tnteractive);
interactive.showAd(120, 120, widgetId);
```

3. 如果不想显示关闭按钮，可以在调用 `showAd` 方式时设置更多参数。

```java
InteractiveAdView interactive = findViewById(R.id.tnteractive);

/**
 * @param width view width
 * @param height view height
 * @param showCloseIcon show close icon
 */
interactive.showAd(120, 120, false, widgetId);
```

### 清除广告

```java
if (interactive != null) {
    interactive.setVisibility(View.GONE);
}
```

## 原生广告
### 加载广告
1. 在 ADFly 控制后台中创建原生广告 UnitId
2. 创建 NativeAd 对象
3. 调用 NativeAd.loadAd() 请求加载广告

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

### 显示原生广告

在收到 `onAdLoaded` 回调后可以开始显示广告

1. 创建一个 xml 布局，使用 `NativeAdView` 作为 rootView

**NativeAdView**：用于广告曝光统计

**MediaView**： 用于显示广告的图片、视频等

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

2. 从 `NativeAd` 中可以获取广告的 **标题**、**描述**、**赞助商**、**Ad标签**、**按钮**
3. 调用 `NativeAd.showView(NativeAdView adView, MediaView mediaView, List<View> clickableViews)` 来显示广告

**clickableViews**：用户可以用于点击的 View 列表

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

**View #1: 标题**

**View #2: MediaView**

**View #3: Ad标签**

**View #4: 赞助商**

**View #5: 按钮**

## 隐私

SDK 会收集 Language、 Manufacturer、GAID 这些信息并上报这些数据，用于确定用户ID。如果应用需要上架到 GooglePlay，需要在 GooglePlay 开发者控制台上和隐私政策协议中声明。