package com.zack.kongtv;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.dueeeke.videoplayer.ijk.IjkPlayerFactory;
import com.dueeeke.videoplayer.player.VideoViewConfig;
import com.dueeeke.videoplayer.player.VideoViewManager;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.yanbo.lib_screen.VApplication;
import com.zack.kongtv.bean.AppConfig;
import com.zackdk.Utils.LogUtil;
import com.zackdk.Utils.SPUtil;

import java.util.LinkedList;
import java.util.List;

import androidx.multidex.MultiDex;


public class App extends Application {
    private static Context context;
    private static List<Activity> activities = new LinkedList<>();
    private static AppConfig appConfig = new AppConfig();
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        initUM();
        initAppConfig();
//        QbSdk.initX5Environment(this,null);
        registerActivityLifecycleCallbacks(life);
        MultiDex.install(this);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                Log.d("TAG", "onInitializationComplete: ");
            }


        });
        VideoViewManager.setConfig(VideoViewConfig.newBuilder()
                //使用使用IjkPlayer解码
                .setPlayerFactory(IjkPlayerFactory.create())
                .build());
        VApplication.init(this);
    }

    private void initAppConfig() {
        AppConfig app  = (AppConfig) SPUtil.get(this,Const.APP_CONFIG);
        if(app!=null){
            appConfig = app;
        }
    }

    private void initUM() {
        UMConfigure.init(this, "5b460ddfa40fa35036000318", "NEW_UI", UMConfigure.DEVICE_TYPE_PHONE, "");
        if(BuildConfig.DEBUG){
            UMConfigure.setLogEnabled(true);
        }
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_DUM_NORMAL);
        //MobclickAgent.openActivityDurationTrack(false);
    }

    public static Context getContext() {
        return context;
    }

    public static AppConfig getAppConfig() {
        return appConfig;
    }

    public static void setAppConfig(AppConfig app) {
        appConfig = app;
    }
    private ActivityLifecycleCallbacks life = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            activities.add(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {
            //MobclickAgent.onPageStart(this.getClass().getName());
            MobclickAgent.onResume(activity);
            LogUtil.d(activity.getLocalClassName()+"dkdkonResume");
        }

        @Override
        public void onActivityPaused(Activity activity) {
            //MobclickAgent.onPageEnd(this.getClass().getName());
            MobclickAgent.onPause(activity);
            LogUtil.d(activity.getLocalClassName()+"dkdkonPause");
        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            activities.remove(activity);
        }
    };

    public static void finshAllActivity(){
        for (Activity a:activities) {
            a.finish();
        }
    }
}
