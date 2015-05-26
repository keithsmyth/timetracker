package com.keithsmyth.timetracker.database.model;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import org.joda.time.DateTime;

/**
 * @author keithsmyth
 */
public class Timesheet {

  public int id;
  public int taskId;
  public DateTime startTime;
  public DateTime stopTime;

  public static final String ID = "id";
  public static final String TASK_ID = "taskId";
  public static final String START_TIME = "startTime";
  public static final String STOP_TIME = "stopTime";

  public static final String TABLE = "Timesheet";



  public static final class Builder {
    private final ContentValues contentValues = new ContentValues();

    public Builder id(int id) {
      contentValues.put(ID, id);
      return this;
    }

    public Builder taskId(int taskId) {
      contentValues.put(TASK_ID, taskId);
      return this;
    }

    public Builder startTime(DateTime startTime) {
      contentValues.put(START_TIME, startTime.toString());
      return this;
    }

    public Builder stopTime(DateTime startTime) {
      contentValues.put(STOP_TIME, startTime.toString());
      return this;
    }

    public ContentValues build() {
      return contentValues;
    }
  }

  public static void onCreate(SQLiteDatabase db) {
    db.execSQL("create table " + TABLE + " (" +
        ID + " integer primary key autoincrement, " +
        TASK_ID + " integer not null, " +
        START_TIME + " text not null," +
        STOP_TIME + " text not null" +
        ")");
  }

  public static void onUpdate(SQLiteDatabase db, int oldVersion, int newVersion) {
    // do nothing
  }
}
