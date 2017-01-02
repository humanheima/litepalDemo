package com.hm.litepaldemo;

import android.app.Application;

import com.facebook.stetho.Stetho;

import org.litepal.LitePal;

/**
 * Created by Administrator on 2017/1/2.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        Stetho.initializeWithDefaults(this);
    }
}
