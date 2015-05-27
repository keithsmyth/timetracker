package com.keithsmyth.timetracker.database.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author keithsmyth
 */
public class Timesheet extends RealmObject {

  @PrimaryKey private String id;
  private Task task;
  private Date startTime;
  private Date stopTime;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Task getTask() {
    return task;
  }

  public void setTask(Task task) {
    this.task = task;
  }

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public Date getStopTime() {
    return stopTime;
  }

  public void setStopTime(Date stopTime) {
    this.stopTime = stopTime;
  }
}