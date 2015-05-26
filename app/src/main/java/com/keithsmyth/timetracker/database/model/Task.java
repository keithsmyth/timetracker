package com.keithsmyth.timetracker.database.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.keithsmyth.timetracker.database.Db;
import com.squareup.sqlbrite.SqlBrite.Query;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

/**
 * @author keithsmyth
 */
public class Task {

  public int id;
  public String name;

  public static final String ID = "id";
  public static final String NAME = "name";

  public static final String TABLE = "Task";
  public static final String QUERY = "Select * From " + TABLE;

  public static final Func1<Query, List<Task>> MAP = new Func1<Query, List<Task>>() {
    @Override public List<Task> call(Query query) {
      try (Cursor cursor = query.run()) {
        List<Task> taskList = new ArrayList<>();
        while (cursor.moveToNext()) {
          taskList.add(Task.fromCursor(cursor));
        }
        return taskList;
      }
    }
  };

  public static Task fromCursor(Cursor cursor) {
    Task task = new Task();
    task.id = Db.getInt(cursor, ID);
    task.name = Db.getString(cursor, NAME);
    return task;
  }

  public static final class Builder {
    private final ContentValues contentValues = new ContentValues();

    public Builder id(int id) {
      contentValues.put(ID, id);
      return this;
    }

    public Builder name(String name) {
      contentValues.put(NAME, name);
      return this;
    }

    public ContentValues build() {
      return contentValues;
    }

  }

  public static void onCreate(SQLiteDatabase db) {
    db.execSQL("create table " + TABLE + " (" +
        ID + " integer primary key autoincrement, " +
        NAME + " text not null" +
        ")");
  }

  public static void onUpdate(SQLiteDatabase db, int oldVersion, int newVersion) {
    // do nothing
  }
}
