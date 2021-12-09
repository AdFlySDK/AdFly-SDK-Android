# ADFLY SDK 集成文档 V0.3

## 使用方式

### 添加依赖库

* 引入 adfly aar包

```groovy
adfly-sdk-release-0.3.1.aar
```

* 基础依赖三方库

```groovy
implementation "androidx.browser:browser:1.4.0"

implementation 'com.google.android.gms:play-services-ads-identifier:17.1.0'

// rxjava
implementation 'io.reactivex.rxjava2:rxjava:2.1.14'
implementation 'io.reactivex.rxjava2:rxandroid:2.0.2'

// glid
implementation 'com.github.bumptech.glide:glide:4.11.0'

// gson
implementation 'com.google.code.gson:gson:2.8.6'
```

### 配置

**添加 http 支持**

* AndroidManifest.xml

```xml
	<application
        android:networkSecurityConfig="@xml/network_security_config"">

        <!-- Adfly 使用了apache http 下载视频 -->
        <uses-library android:name="org.apache.http.legacy" android:required="false" />
        
    </application>
```

* xml/network_security_config.xml 文件

```xml
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <base-config cleartextTrafficPermitted="true" />
</network-security-config>
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

| code | 含义 | 说明  |
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

* 布局InteractiveAdView控件
* 得到InteractiveAdView 对象后调用
* customView: adview 的 superView。
* onShowAction: 展示广告回调
* onClickAction: 点击广告回调
* onCloseAction: 用户关闭回调

``` swift
<?xml version="1.0" encoding="utf-8"?>
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


InteractiveAdView interactive = findViewById(R.id.tnteractive);
interactive.showAd(true, widgetId);



/**
     * @param width view width
     * @param height view height
     * @param showCloseIcon show close icon
     * @param widgetId widgetId
     */
interactive.showAd(width, height, true);


```


**FAQ:**

1.为什么打开网页链接会调到外部浏览器打开
答：打开网页链接使用的是chrome tabs的方式加速网页打开体验，在国内普遍没有安装chrome浏览器且未设置为默认浏览器所以会在外部浏览器打开，在国外用户使用时基本都支持内部浏览器打开。
