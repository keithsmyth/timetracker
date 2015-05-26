package com.keithsmyth.timetracker.viewmodel;

import android.database.Cursor;

import com.keithsmyth.timetracker.database.Db;
import com.keithsmyth.timetracker.database.model.Task;
import com.keithsmyth.timetracker.database.model.Timesheet;
import com.squareup.sqlbrite.SqlBrite;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.functions.Func1;

/**
 * @author keithsmyth
 */
public class TimesheetViewModel {

  public int timesheetId;
  public int taskId;
  public String taskName;
  public DateTime startTime;
  public DateTime stopTime;

  public static final String TIMESHEET_ID = "timesheetId";
  public static final String TASK_ID = "taskId";
  public static final String TASK_NAME = "taskName";
  public static final String START_TIME = "startTime";
  public static final String STOP_TIME = "stopTime";

  public static final Iterable<String> TABLES = Arrays.asList(Timesheet.TABLE, Task.TABLE);

  public static final String QUERY = "Select " +
      "s." + Timesheet.ID + " as " + TIMESHEET_ID + ", " +
      "t." + Task.ID + " as " + TASK_ID + ", " +
      "t." + Task.NAME + " as " + TASK_NAME + ", " +
      "s." + Timesheet.START_TIME + " as " + START_TIME + ", " +
      "s." + Timesheet.STOP_TIME + " as " + STOP_TIME + " " +
      "From " + Timesheet.TABLE + " s " +
      "Inner Join " + Task.TABLE + " t " +
      "On s." + Timesheet.TASK_ID + " = t." + Task.ID;

  public static final Func1<SqlBrite.Query, List<TimesheetViewModel>> MAP = new Func1<SqlBrite
      .Query, List<TimesheetViewModel>>() {
    @Override public List<TimesheetViewModel> call(SqlBrite.Query query) {
      try (Cursor cursor = query.run()) {
        List<TimesheetViewModel> taskList = new ArrayList<>();
        while (cursor.moveToNext()) {
          taskList.add(TimesheetViewModel.fromCursor(cursor));
        }
        return taskList;
      }
    }
  };

  public static TimesheetViewModel fromCursor(Cursor cursor) {
    TimesheetViewModel timesheetViewModel = new TimesheetViewModel();
    timesheetViewModel.timesheetId = Db.getInt(cursor, TIMESHEET_ID);
    timesheetViewModel.taskId = Db.getInt(cursor, TASK_ID);
    timesheetViewModel.taskName = Db.getString(cursor, TASK_NAME);
    timesheetViewModel.startTime = Db.getDateTime(cursor, START_TIME);
    timesheetViewModel.stopTime = Db.getDateTime(cursor, STOP_TIME);
    return timesheetViewModel;
  }
}
