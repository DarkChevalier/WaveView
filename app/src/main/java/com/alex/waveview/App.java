package com.alex.waveview;

import android.app.Application;

import github.alex.helper.CrashHandler;
import github.alex.util.LogUtil;

/**
 * 作者：Alex
 * 时间：2016年08月19日    22:23
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        new CrashHandler(this, new CrashHandlerOnCrashListener());
    }

    private final class CrashHandlerOnCrashListener implements CrashHandler.OnCrashListener{

        @Override
        public void onCrash(String s) {
            LogUtil.e(s);
        }
    }
}
