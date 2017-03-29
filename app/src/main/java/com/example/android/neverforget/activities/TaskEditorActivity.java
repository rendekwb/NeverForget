package com.example.android.neverforget.activities;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.neverforget.R;
import com.example.android.neverforget.data.NeverForgetContract;
import com.example.android.neverforget.data.NeverForgetDbHelper;

/**
 * Created by rendekwb on 3/21/17.
 */

public class TaskEditorActivity extends AppCompatActivity{

    private NeverForgetDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_editor);

        mDbHelper = new NeverForgetDbHelper(this);

        Button addNewTaskButton = (Button) findViewById(R.id.add_task_button);
        addNewTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertTask();
                finish();
            }
        });

    }

    private void insertTask(){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        EditText descriptionEditText = (EditText) findViewById(R.id.new_task_description_edit_text);
        String description = descriptionEditText.getText().toString();

        ContentValues values = new ContentValues();
        values.put(NeverForgetContract.TaskEntry.COLUMN_TASK_DESCRIPTION, description);

        long newRowId = db.insert(NeverForgetContract.TaskEntry.TABLE_NAME, null, values);
        Log.v("TaskEditorActivity", "New Row ID: " + newRowId);
    }


}
