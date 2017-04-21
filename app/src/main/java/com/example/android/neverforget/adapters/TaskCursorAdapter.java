package com.example.android.neverforget.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.neverforget.R;

/**
 * Created by rendekwb on 4/17/17.
 */

public class TaskCursorAdapter extends CursorAdapter {

    public TaskCursorAdapter(Context context, Cursor cursor){
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.todo_task, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView taskDescriptionView = (TextView) view.findViewById(R.id.todo_task_description);
        TextView taskUrgencyLevelView = (TextView) view.findViewById(R.id.todo_task_priority);

        String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
        int priority = cursor.getInt(cursor.getColumnIndexOrThrow("priority"));

        String priorityString;

        if(priority == 0){
            priorityString = "Low";
        } else if(priority == 1){
            priorityString = "Medium";
        } else {
            priorityString = "High";
        }


        taskDescriptionView.setText(description);
        taskUrgencyLevelView.setText(priorityString);

    }
}
