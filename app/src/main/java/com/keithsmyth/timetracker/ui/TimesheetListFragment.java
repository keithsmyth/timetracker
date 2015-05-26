package com.keithsmyth.timetracker.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.keithsmyth.timetracker.App;
import com.keithsmyth.timetracker.R;
import com.keithsmyth.timetracker.adapter.TimesheetAdapter;
import com.keithsmyth.timetracker.viewmodel.TimesheetViewModel;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author keithsmyth
 */
public class TimesheetListFragment extends Fragment {

  private TimesheetAdapter adapter;
  private Subscription subscription;

  public static TimesheetListFragment newInstance() {
    return new TimesheetListFragment();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_timesheet_list, container, false);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    RecyclerView timesheetRecyclerView = (RecyclerView) view.findViewById(R.id.lst_timesheets);
    timesheetRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    timesheetRecyclerView.setHasFixedSize(true);
    adapter = new TimesheetAdapter();
    timesheetRecyclerView.setAdapter(adapter);
  }

  @Override public void onResume() {
    super.onResume();
    subscription = App.getDb().createQuery(TimesheetViewModel.TABLES, TimesheetViewModel.QUERY)
        .map(TimesheetViewModel.MAP)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(adapter);
  }

  @Override public void onPause() {
    super.onPause();
    subscription.unsubscribe();
  }
}
