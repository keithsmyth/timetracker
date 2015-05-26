package com.keithsmyth.timetracker.database;

import com.keithsmyth.timetracker.App;
import com.keithsmyth.timetracker.database.model.Current;
import com.keithsmyth.timetracker.database.model.Task;

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
}
