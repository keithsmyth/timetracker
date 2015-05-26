package com.keithsmyth.timetracker;

import android.app.Application;

import com.keithsmyth.timetracker.database.SqlHelper;
import com.squareup.sqlbrite.SqlBrite;

/**
 * @author keithsmyth
 */
public class App extends Application {

  private static App sInstance;
  private static SqlBrite sDb;

  @Override public void onCreate() {
    super.onCreate();
    sInstance = this;
  }

  public static SqlBrite getDb() {
    if (sDb ==  null) {
      sDb = SqlBrite.create(new SqlHelper(sInstance.getApplicationContext()));
    }
    return sDb;
  }
}
