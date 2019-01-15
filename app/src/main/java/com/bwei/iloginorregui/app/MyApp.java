package com.bwei.iloginorregui.app;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.mob.MobSDK;

public class MyApp extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        MobSDK.init(this);
        Fresco.initialize(this);
    }
    public static Context getContext(){
        return context;
    }
}
