package com.keithsmyth.timetracker.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keithsmyth.timetracker.R;
import com.keithsmyth.timetracker.database.model.Task;

import java.util.Collections;
import java.util.List;

import rx.functions.Action1;

/**
 * @author keithsmyth
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> implements Action1<List<Task>> {

  private final Listener listener;
  private List<Task> taskList = Collections.emptyList();

  public TaskAdapter(Listener listener) {
    this.listener = listener;
  }

  @Override public void call(List<Task> taskList) {
    this.taskList = taskList;
    notifyDataSetChanged();
  }

  @Override public TaskViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.item_task, viewGroup, false);
    return new TaskViewHolder(view);
  }

  @Override public void onBindViewHolder(TaskViewHolder taskViewHolder, int i) {
    taskViewHolder.bind(taskList.get(i), listener);
  }

  @Override public int getItemCount() {
    return taskList.size();
  }

  public static class TaskViewHolder extends RecyclerView.ViewHolder {

    private TextView nameText;

    public TaskViewHolder(View itemView) {
      super(itemView);
      nameText = (TextView) itemView.findViewById(R.id.txt_name);
    }

    public void bind(final Task task, final Listener listener) {
      nameText.setText(task.name);
      nameText.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          listener.onTaskClicked(task);
        }
      });
    }
  }

  public static interface Listener {
    void onTaskClicked(Task task);
  }
}
