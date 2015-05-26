package com.keithsmyth.timetracker.viewmodel;

import org.joda.time.DateTime;

/**
 * @author keithsmyth
 */
public class TimesheetViewModel {

  public int timesheetId;
  public int taskId;
  public String taskName;
  public DateTime startTime;
  public DateTime stopTime;
}
