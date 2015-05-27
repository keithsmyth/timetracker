package com.keithsmyth.timetracker.viewmodel;

import org.joda.time.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author keithsmyth
 */
public class TimeSummaryViewModel {

  public String taskId;
  public String taskName;

  private final List<TimesheetViewModel> timesheetViewModelList;

  public TimeSummaryViewModel(String taskId, String taskName) {
    this.taskId = taskId;
    this.taskName = taskName;
    timesheetViewModelList = new ArrayList<>();
  }

  public void add(TimesheetViewModel timesheetViewModel) {
    if (!timesheetViewModel.taskId.equals(taskId)) {
      throw new IllegalArgumentException("Summary must be of same task");
    }
    timesheetViewModelList.add(timesheetViewModel);
  }

  public int getMinutes() {
    int minutes = 0;
    for (TimesheetViewModel vm : timesheetViewModelList) {
      minutes += new Duration(vm.startTime, vm.stopTime).getStandardMinutes();
    }
    return minutes;
  }
}
