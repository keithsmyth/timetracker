package com.keithsmyth.timetracker;

import android.app.Application;
import android.content.Context;

/**
 * @author keithsmyth
 */
public class App extends Application {

  private static App sInstance;

  @Override public void onCreate() {
    super.onCreate();
    sInstance = this;
  }

  public static Context getContext() {
    return sInstance.getApplicationContext();
  }
}
