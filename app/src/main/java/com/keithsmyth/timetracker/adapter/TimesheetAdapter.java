package com.keithsmyth.timetracker.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keithsmyth.timetracker.R;
import com.keithsmyth.timetracker.viewmodel.TimesheetViewModel;

import java.util.Collections;
import java.util.List;

import rx.functions.Action1;

/**
 * @author keithsmyth
 */
public class TimesheetAdapter extends RecyclerView.Adapter<TimesheetAdapter.TimesheetViewHolder> {

  private List<TimesheetViewModel> timesheetViewModelList = Collections.emptyList();

  public void init(List<TimesheetViewModel> timesheetViewModelList) {
    this.timesheetViewModelList = timesheetViewModelList;
    notifyDataSetChanged();
  }

  @Override public TimesheetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_timesheet, parent, false);
    return new TimesheetViewHolder(view);
  }

  @Override public void onBindViewHolder(TimesheetViewHolder holder, int position) {
    holder.bind(timesheetViewModelList.get(position));
  }

  @Override public int getItemCount() {
    return timesheetViewModelList.size();
  }

  public static class TimesheetViewHolder extends RecyclerView.ViewHolder {

    private final TextView taskNameText;
    private final TextView startTimeText;
    private final TextView stopTimeText;

    public TimesheetViewHolder(View itemView) {
      super(itemView);
      taskNameText = (TextView) itemView.findViewById(R.id.txt_task_name);
      startTimeText = (TextView) itemView.findViewById(R.id.txt_start_time);
      stopTimeText = (TextView) itemView.findViewById(R.id.txt_stop_time);
    }

    public void bind(TimesheetViewModel timesheetViewModel) {
      taskNameText.setText(timesheetViewModel.taskName);
      startTimeText.setText(timesheetViewModel.startTime.toString());
      stopTimeText.setText(timesheetViewModel.stopTime.toString());
    }
  }
}
