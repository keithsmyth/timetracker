package com.keithsmyth.timetracker.database;

import com.keithsmyth.timetracker.App;
import com.keithsmyth.timetracker.database.model.Current;
import com.keithsmyth.timetracker.database.model.Task;
import com.keithsmyth.timetracker.database.model.Timesheet;
import com.keithsmyth.timetracker.viewmodel.TimesheetViewModel;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * @author keithsmyth
 */
public class Database {

  private Database() {
  }

  public static RealmResults<Task> tasks() {
    Realm realm = Realm.getInstance(App.getContext());
    return realm.allObjects(Task.class);
  }

  public static Current current() {
    Realm realm = Realm.getInstance(App.getContext());
    return realm.where(Current.class).findFirst();
  }

  public static List<TimesheetViewModel> timesheets() {
    Realm realm = Realm.getInstance(App.getContext());
    final RealmResults<Timesheet> results = realm.allObjects(Timesheet.class);
    List<TimesheetViewModel> timesheets = new ArrayList<>(results.size());
    for (int i = 0; i < results.size(); i++) {
      timesheets.add(map(results.get(i)));
    }
    return timesheets;
  }

  private static TimesheetViewModel map(Timesheet timesheet) {
    TimesheetViewModel vm = new TimesheetViewModel();
    vm.timesheetId = timesheet.getId();
    vm.taskId = timesheet.getTask().getId();
    vm.taskName = timesheet.getTask().getName();
    vm.startTime = new DateTime(timesheet.getStartTime());
    vm.stopTime = new DateTime(timesheet.getStopTime());
    return vm;
  }
}
