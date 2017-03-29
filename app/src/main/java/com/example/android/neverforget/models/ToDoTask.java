package com.example.android.neverforget.models;

/**
 * Created by rendekwb on 3/19/17.
 */

public class ToDoTask {

    // Declaration of private variables
    private int mId;
    private String mTaskDescription;
    private String mUrgency;
    private Boolean mIsCompleted;

    // Constructors
    public ToDoTask(String description, String urgency, Boolean isCompleted) {
        mTaskDescription = description;
        mUrgency = urgency;
        mIsCompleted = isCompleted;
    }

    public ToDoTask(String description, String urgency) {
        mTaskDescription = description;
        mUrgency = urgency;
        mIsCompleted = false;
    }

    // Getter Methods
    public int getId() { return mId; }

    public String getTaskDescription() {
        return mTaskDescription;
    }

    public String getUrgency() {
        return mUrgency;
    }

    public Boolean getIsCompleted() {
        return mIsCompleted;
    }
}
