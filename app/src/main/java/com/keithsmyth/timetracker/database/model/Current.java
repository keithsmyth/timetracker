package com.keithsmyth.timetracker.database.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.keithsmyth.timetracker.database.Db;
import com.squareup.sqlbrite.SqlBrite;

import org.joda.time.DateTime;

import rx.functions.Func1;

/**
 * @author keithsmyth
 */
public class Current {

  public int id;
  public int taskId;
  public String taskName;
  public DateTime startTime;

  public static final String ALIAS_CURRENT = "c";
  public static final String ALIAS_TASK = "t";

  public static final String ID = "id";
  public static final String TASK_ID = "taskId";
  public static final String START_TIME = "startTime";

  public static final String ALIAS_ID = ALIAS_CURRENT + "." + ID;
  public static final String ALIAS_TASK_ID = ALIAS_CURRENT + "." + TASK_ID;
  public static final String ALIAS_START_TIME = ALIAS_CURRENT + "." + START_TIME;

  public static final String TASK_ALIAS_ID = ALIAS_TASK + "." + Task.ID;
  public static final String TASK_ALIAS_NAME = ALIAS_TASK + "." + Task.NAME;

  public static final String TABLE = "Current";
  public static final String QUERY = "Select " +
      ALIAS_ID + "," +
      ALIAS_TASK_ID + "," +
      ALIAS_START_TIME + "," +
      TASK_ALIAS_NAME + " AS " + Task.NAME +
      " From " + TABLE + " AS " + ALIAS_CURRENT +
      " Inner join " + Task.TABLE + " AS " + ALIAS_TASK +
      " On " + TASK_ALIAS_ID + " = " + ALIAS_TASK_ID;

  public static final Func1<SqlBrite.Query, Current> MAP = new Func1<SqlBrite.Query, Current>() {
    @Override public Current call(SqlBrite.Query query) {
      Cursor cursor = query.run();
      try {
        Current current = null;
        if (cursor.moveToFirst()) {
          current = Current.fromCursor(cursor);
        }
        return current;
      } finally {
        cursor.close();
      }
    }
  };

  public static Current fromCursor(Cursor cursor) {
    Current current = new Current();
    current.id = Db.getInt(cursor, ID);
    current.taskId = Db.getInt(cursor, TASK_ID);
    current.taskName = Db.getString(cursor, Task.NAME);
    current.startTime = Db.getDateTime(cursor, START_TIME);
    return current;
  }

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

    public ContentValues build() {
      return contentValues;
    }
  }

  public static void onCreate(SQLiteDatabase db) {
    db.execSQL("create table " + TABLE + " (" +
        ID + " integer primary key autoincrement, " +
        TASK_ID + " integer not null, " +
        START_TIME + " text not null" +
        ")");
  }

  public static void onUpdate(SQLiteDatabase db, int oldVersion, int newVersion) {
    // do nothing
  }

  @Override public String toString() {
    return String.format("%1$s from: %2$s", taskName, startTime.toString("HH:mm yyyy/MM/dd"));
  }
}
