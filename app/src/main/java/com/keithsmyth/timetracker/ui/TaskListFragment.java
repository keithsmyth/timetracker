package com.keithsmyth.timetracker.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keithsmyth.timetracker.R;
import com.keithsmyth.timetracker.adapter.TaskAdapter;
import com.keithsmyth.timetracker.database.Database;
import com.keithsmyth.timetracker.database.model.Current;
import com.keithsmyth.timetracker.database.model.Task;
import com.keithsmyth.timetracker.database.model.Timesheet;
import com.melnykov.fab.FloatingActionButton;

import org.joda.time.DateTime;

import java.util.UUID;

import io.realm.Realm;

/**
 * @author keithsmyth
 */
public class TaskListFragment extends Fragment implements TaskAdapter.Listener {

  private TaskAdapter adapter;
  private Listener listener;
  private TextView currentTextView;
  private Current current;

  public static TaskListFragment newInstance() {
    return new TaskListFragment();
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_task_list, container, false);
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    currentTextView = (TextView) view.findViewById(R.id.txt_current);

    view.findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        listener.onOpenAddTaskFragment();
      }
    });

    RecyclerView tasksListView = (RecyclerView) view.findViewById(R.id.lst_tasks);
    tasksListView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    adapter = new TaskAdapter(this);
    tasksListView.setAdapter(adapter);
    FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.btn_add);
    fab.attachToRecyclerView(tasksListView);
  }

  @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.menu_task_list, menu);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.action_timesheet_list) {
      listener.onOpenTimesheetListFragment();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (activity instanceof Listener) {
      listener = (Listener) activity;
    } else {
      throw new RuntimeException("Activity must implement Listener");
    }
  }

  @Override public void onResume() {
    super.onResume();
    populateCurrent();
    adapter.init(Database.tasks());
  }

  private void populateCurrent() {
    this.current = Database.current();
    currentTextView.setText(current != null ?
        formatCurrent(current)
        : getString(R.string.empty_current));
  }

  private String formatCurrent(Current current) {
    return String.format("%1$s from: %2$s",
        current.getTask().getName(),
        current.getStartTime().toString());
  }

  @Override public void onTaskClicked(final Task task) {
    Realm realm = Realm.getInstance(getActivity());
    realm.executeTransaction(new Realm.Transaction() {
      @Override public void execute(Realm realm) {
        if (current != null) {
          // add timesheet
          Timesheet newTimesheet = realm.createObject(Timesheet.class);
          newTimesheet.setId(UUID.randomUUID().toString());
          newTimesheet.setTask(current.getTask());
          newTimesheet.setStartTime(current.getStartTime());
          newTimesheet.setStopTime(DateTime.now().toDate());

          // stop current
          String currentTaskId = current.getTask().getId();
          realm.allObjects(Current.class).clear();

          if (currentTaskId.equals(task.getId())) {
            // only turning off old value
            return;
          }
        }

        // start new current
        Current newCurrent = realm.createObject(Current.class);
        newCurrent.setId(UUID.randomUUID().toString());
        newCurrent.setTask(task);
        newCurrent.setStartTime(DateTime.now().toDate());
      }
    });
    populateCurrent();
  }

  public static interface Listener {
    void onOpenAddTaskFragment();

    void onOpenTimesheetListFragment();
  }
}
