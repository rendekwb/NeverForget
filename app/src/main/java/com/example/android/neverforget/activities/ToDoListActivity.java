package com.example.android.neverforget.activities;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.android.neverforget.adapters.ContactCursorAdapter;
import com.example.android.neverforget.adapters.TaskCursorAdapter;
import com.example.android.neverforget.adapters.ToDoTaskAdapter;
import com.example.android.neverforget.data.NeverForgetContract;
import com.example.android.neverforget.data.NeverForgetDbHelper;
import com.example.android.neverforget.models.ToDoTask;
import com.example.android.neverforget.R;

import java.util.ArrayList;

import static android.os.Build.VERSION_CODES.M;

public class ToDoListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int TASK_LOADER = 0;

    TaskCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        ListView todoListView = (ListView) findViewById(R.id.to_do_list_view);

        mCursorAdapter = new TaskCursorAdapter(this, null);
        todoListView.setAdapter(mCursorAdapter);

        getLoaderManager().initLoader(TASK_LOADER, null, this);

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

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
            NeverForgetContract.TaskEntry._ID,
            NeverForgetContract.TaskEntry.COLUMN_TASK_DESCRIPTION,
            NeverForgetContract.TaskEntry.COLUMN_TASK_PRIORITY
        };

        return new CursorLoader(this, NeverForgetContract.TaskEntry.CONTENT_URI, projection, null, null, null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Button updateTasksButton = (Button) findViewById(R.id.update_tasks_button);
        if(cursor.getCount() == 0){
            updateTasksButton.setVisibility(View.GONE);
        } else {
            updateTasksButton.setVisibility(View.VISIBLE);
        }

        mCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
}
