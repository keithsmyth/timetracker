package com.keithsmyth.timetracker.database;

import android.database.Cursor;

import org.joda.time.DateTime;

public final class Db {
  public static final int BOOLEAN_FALSE = 0;
  public static final int BOOLEAN_TRUE = 1;

  public static String getString(Cursor cursor, String columnName) {
    return cursor.getString(cursor.getColumnIndexOrThrow(columnName));
  }

  public static boolean getBoolean(Cursor cursor, String columnName) {
    return getInt(cursor, columnName) == BOOLEAN_TRUE;
  }

  public static long getLong(Cursor cursor, String columnName) {
    return cursor.getLong(cursor.getColumnIndexOrThrow(columnName));
  }

  public static int getInt(Cursor cursor, String columnName) {
    return cursor.getInt(cursor.getColumnIndexOrThrow(columnName));
  }

  public static DateTime getDateTime(Cursor cursor, String columnName) {
    return DateTime.parse(getString(cursor, columnName));
  }

  private Db() {
    throw new AssertionError("No instances.");
  }
}
