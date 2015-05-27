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

/**
 * @author keithsmyth
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

  private final Listener listener;
  private List<Task> taskList = Collections.emptyList();

  public TaskAdapter(Listener listener) {
    this.listener = listener;
  }

  public void init(List<Task> taskList) {
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
      nameText.setText(task.getName());
      nameText.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          listener.onTaskClicked(task);
        }
      });
    }
  }

  public interface Listener {
    void onTaskClicked(Task task);
  }
}
