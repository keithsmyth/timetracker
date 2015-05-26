package com.keithsmyth.timetracker.viewmodel;

/**
 * @author keithsmyth
 */
public class TaskViewModel {

  private int id;
  private String name;

  public TaskViewModel(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
