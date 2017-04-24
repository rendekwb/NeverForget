package com.example.android.neverforget.adapters;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.neverforget.activities.ToDoListActivity;
import com.example.android.neverforget.data.NeverForgetContract;
import com.example.android.neverforget.models.ToDoTask;
import com.example.android.neverforget.R;

import java.util.ArrayList;

/**
 * Created by rendekwb on 3/19/17.
 */

public class ToDoTaskAdapter extends ArrayAdapter<ToDoTask> {

    public ToDoTaskAdapter(Activity context, int resource, ArrayList<ToDoTask> tasks){
        super(context, resource, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View taskView = convertView;
        if(taskView == null){
            taskView = LayoutInflater.from(getContext()).inflate(R.layout.todo_task, parent, false);
        }

        ToDoTask currentTask = getItem(position);

        TextView taskDescriptionView = (TextView) taskView.findViewById(R.id.todo_task_description);
        taskDescriptionView.setText(currentTask.getTaskDescription());

        TextView taskUrgencyLevelView = (TextView) taskView.findViewById(R.id.todo_task_priority);
        taskUrgencyLevelView.setText("Priority: " + currentTask.getUrgency());

        return taskView;
    }


}
