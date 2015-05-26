package com.keithsmyth.timetracker.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;

import com.keithsmyth.timetracker.R;

public class MainActivity extends ActionBarActivity implements TaskListFragment.Listener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    if (savedInstanceState == null) {
      getSupportFragmentManager().beginTransaction()
          .add(R.id.container, TaskListFragment.newInstance())
          .commit();
    }
  }

  @Override public void onOpenAddTaskFragment() {
    AddTaskFragment.newInstance().show(getSupportFragmentManager(), AddTaskFragment.TAG);
  }

  @Override public void onOpenTimesheetListFragment() {
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.container, TimesheetListFragment.newInstance())
        .addToBackStack(TimesheetListFragment.class.getSimpleName())
        .commit();
  }
}
