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

import com.keithsmyth.timetracker.App;
import com.keithsmyth.timetracker.R;
import com.keithsmyth.timetracker.adapter.TaskAdapter;
import com.keithsmyth.timetracker.database.model.Current;
import com.keithsmyth.timetracker.database.model.Task;
import com.keithsmyth.timetracker.database.model.Timesheet;
import com.squareup.sqlbrite.SqlBrite;

import org.joda.time.DateTime;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @author keithsmyth
 */
public class TaskListFragment extends Fragment implements TaskAdapter.Listener {

  private TaskAdapter adapter;
  private Subscription currentSubscription;
  private Subscription taskSubscription;
  private Listener listener;
  private TextView currentTextView;

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
    currentSubscription = App.getDb().createQuery(Current.TABLE, Current.QUERY)
        .map(Current.MAP)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<Current>() {
          @Override public void call(Current current) {
            populateCurrent(current);
          }
        });
    taskSubscription = App.getDb().createQuery(Task.TABLE, Task.QUERY)
        .map(Task.MAP)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(adapter);
  }

  @Override public void onPause() {
    super.onPause();
    currentSubscription.unsubscribe();
    taskSubscription.unsubscribe();
  }

  private Current current;

  private void populateCurrent(Current current) {
    this.current = current;
    currentTextView.setText(current != null ? current.toString()
        : getString(R.string.empty_current));
  }

  @Override public void onTaskClicked(Task task) {
    SqlBrite db = App.getDb();
    try {
      db.beginTransaction();
      if (current != null) {
        // stop current
        db.delete(Current.TABLE, null);
        // add timesheet
        db.insert(Timesheet.TABLE,
            new Timesheet.Builder()
                .taskId(current.taskId)
                .startTime(current.startTime)
                .stopTime(DateTime.now())
                .build());

        if (current.taskId == task.id) {
          // don't start any new items
          db.setTransactionSuccessful();
          return;
        }
      }

      // start new
      db.insert(Current.TABLE,
          new Current.Builder()
              .taskId(task.id)
              .startTime(DateTime.now())
              .build());
      db.setTransactionSuccessful();
    } finally {
      db.endTransaction();
    }
  }

  public static interface Listener {
    void onOpenAddTaskFragment();
    void onOpenTimesheetListFragment();
  }
}
