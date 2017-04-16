package com.example.android.neverforget.activities;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.android.neverforget.R;
import com.example.android.neverforget.data.NeverForgetContract;
import com.example.android.neverforget.data.NeverForgetDbHelper;

import java.util.Date;

/**
 * Created by rendekwb on 3/21/17.
 */

public class TaskEditorActivity extends AppCompatActivity{

    private NeverForgetDbHelper mDbHelper;
    private int mPriority = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_editor);

        mDbHelper = new NeverForgetDbHelper(this);

        spinnerSetUp();

        Button addNewTaskButton = (Button) findViewById(R.id.add_task_button);
        addNewTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertTask();
                finish();
            }
        });

    }

    private void spinnerSetUp(){
        Spinner prioritySpinner = (Spinner) findViewById(R.id.priority_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.priority_array, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prioritySpinner.setAdapter(adapter);

        prioritySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id){
                String priority = (String) parent.getItemAtPosition(position);
                if(!TextUtils.isEmpty(priority)){
                    if(priority.equals("Low")){
                        mPriority = NeverForgetContract.TaskEntry.PRIORITY_LOW;
                    } else if(priority.equals("Medium")){
                        mPriority = NeverForgetContract.TaskEntry.PRIORITY_MEDIUM;
                    } else {
                        mPriority = NeverForgetContract.TaskEntry.PRIORITY_HIGH;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){
                mPriority = 0;
            }
        });
    }

    private void insertTask(){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        EditText descriptionEditText = (EditText) findViewById(R.id.new_task_description_edit_text);
        String description = descriptionEditText.getText().toString();

        Date createdOn = new Date();
        Log.v("created on: ", createdOn.toString());


        ContentValues values = new ContentValues();
        values.put(NeverForgetContract.TaskEntry.COLUMN_TASK_DESCRIPTION, description);
        values.put(NeverForgetContract.TaskEntry.COLUMN_TASK_PRIORITY, mPriority);
        values.put(NeverForgetContract.TaskEntry.COLUMN_TASK_CREATED_ON, createdOn.toString());

        long newRowId = db.insert(NeverForgetContract.TaskEntry.TABLE_NAME, null, values);
        Log.v("TaskEditorActivity", "New Row ID: " + newRowId);
    }


}
