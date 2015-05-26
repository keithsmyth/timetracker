package com.keithsmyth.timetracker.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.keithsmyth.timetracker.database.model.Current;
import com.keithsmyth.timetracker.database.model.Task;
import com.keithsmyth.timetracker.database.model.Timesheet;

/**
 * @author keithsmyth
 */
public class SqlHelper extends SQLiteOpenHelper {

  private static final String NAME = "time-tracker";
  private static final int VERSION = 1;

  public SqlHelper(Context context) {
    super(context, NAME, null, VERSION);
  }

  @Override public void onCreate(SQLiteDatabase db) {
    Task.onCreate(db);
    Current.onCreate(db);
    Timesheet.onCreate(db);
  }

  @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Task.onUpdate(db, oldVersion, newVersion);
    Current.onUpdate(db, oldVersion, newVersion);
    Timesheet.onUpdate(db, oldVersion, newVersion);
  }
}
