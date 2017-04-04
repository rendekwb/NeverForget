package com.example.android.neverforget.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.android.neverforget.adapters.ToDoTaskAdapter;
import com.example.android.neverforget.data.NeverForgetContract;
import com.example.android.neverforget.data.NeverForgetDbHelper;
import com.example.android.neverforget.models.ToDoTask;
import com.example.android.neverforget.R;

import java.util.ArrayList;

import static android.os.Build.VERSION_CODES.M;

public class ToDoListActivity extends AppCompatActivity {

    private NeverForgetDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        mDbHelper = new NeverForgetDbHelper(this);

        displayTasks();

    }

    private void displayTasks(){

            SQLiteDatabase db = mDbHelper.getReadableDatabase();

            Cursor cursor = db.query(
                    NeverForgetContract.TaskEntry.TABLE_NAME,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            ArrayList<ToDoTask> toDoItems = new ArrayList<ToDoTask>();
            String priorityString;

            for (int i = 0; i < cursor.getCount(); i++) {

                cursor.moveToPosition(i);

                int descriptionColumnIndex = cursor.getColumnIndex(NeverForgetContract.TaskEntry.COLUMN_TASK_DESCRIPTION);
                String description = cursor.getString(descriptionColumnIndex);

                int priorityColumnIndex = cursor.getColumnIndex(NeverForgetContract.TaskEntry.COLUMN_TASK_PRIORITY);
                int priority = cursor.getInt(priorityColumnIndex);

                if(priority == 0){
                    priorityString = "Low";
                } else if(priority == 1){
                    priorityString = "Medium";
                } else {
                    priorityString = "High";
                }



                toDoItems.add(new ToDoTask(description, priorityString));

            }

            ToDoTaskAdapter taskAdaptor = new ToDoTaskAdapter(this, R.layout.todo_task, toDoItems);

            ListView listView = (ListView) findViewById(R.id.to_do_list_view);

            listView.setAdapter(taskAdaptor);

            cursor.close();

    }

    @Override
    protected void onStart(){
        super.onStart();
        displayTasks();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu m){
        getMenuInflater().inflate(R.menu.menu_todo, m);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_add:
                Intent intent = new Intent(ToDoListActivity.this, TaskEditorActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
