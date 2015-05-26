package com.keithsmyth.timetracker.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.keithsmyth.timetracker.R;
import com.keithsmyth.timetracker.database.model.Task;

import java.util.UUID;

import io.realm.Realm;

/**
 * @author keithsmyth
 */
public class AddTaskFragment extends DialogFragment {

  public static final String TAG = AddTaskFragment.class.getName();

  public static AddTaskFragment newInstance() {
    return new AddTaskFragment();
  }

  @NonNull @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
    final Context context = getActivity();
    View view = LayoutInflater.from(context).inflate(R.layout.fragment_task_add, null);

    final EditText nameEditText = (EditText) view.findViewById(android.R.id.input);

    return new AlertDialog.Builder(context)
        .setTitle(R.string.task_new_title)
        .setView(view)
        .setPositiveButton(R.string.task_create, new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialog, int which) {
            Realm realm = Realm.getInstance(getActivity());
            realm.beginTransaction();
            Task newTask = realm.createObject(Task.class);
            newTask.setId(UUID.randomUUID().toString());
            newTask.setName(nameEditText.getText().toString());
            realm.commitTransaction();
          }
        })
        .setNegativeButton(R.string.task_cancel, null)
        .create();
  }
}
