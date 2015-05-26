package com.keithsmyth.timetracker.database.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author keithsmyth
 */
public class Current extends RealmObject {

  @PrimaryKey private String id;
  private String taskId;
  private String taskName;
  private Date startTime;

//  @Override public String toString() {
//    return String.format("%1$s from: %2$s", taskName, startTime.toString("HH:mm yyyy/MM/dd"));
//  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTaskId() {
    return taskId;
  }

  public void setTaskId(String taskId) {
    this.taskId = taskId;
  }

  public String getTaskName() {
    return this.taskName;
  }

  public void setTaskName(String taskName) {
    this.taskName = taskName;
  }

  public Date getStartTime() {
    return this.startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }
}
