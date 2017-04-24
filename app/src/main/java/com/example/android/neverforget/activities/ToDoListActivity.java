package com.example.android.neverforget.activities;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.neverforget.adapters.ContactCursorAdapter;
import com.example.android.neverforget.adapters.TaskCursorAdapter;
import com.example.android.neverforget.adapters.ToDoTaskAdapter;
import com.example.android.neverforget.data.NeverForgetContract;
import com.example.android.neverforget.data.NeverForgetDbHelper;
import com.example.android.neverforget.models.Contact;
import com.example.android.neverforget.models.ToDoTask;
import com.example.android.neverforget.R;

import java.util.ArrayList;

import static android.R.attr.dialogIcon;
import static android.R.attr.id;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static android.os.Build.VERSION_CODES.M;
import static android.os.Build.VERSION_CODES.N;

public class ToDoListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int COMPLETED_TASK_LOADER = 0;
    private static final int INCOMPLETED_TASK_LOADER = 1;
    long clickedItem;

    TaskCursorAdapter mCursorAdapterCompleted, mCursorAdapterIncompleted;

    ListView completedTodoListView, incompletedTodoListView;

    CheckBox checkbox;
    int currentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        completedTodoListView = (ListView) findViewById(R.id.completed_to_do_list_view);
        incompletedTodoListView = (ListView) findViewById(R.id.incomplete_to_do_list_view);

        mCursorAdapterCompleted = new TaskCursorAdapter(this, null);
        mCursorAdapterIncompleted = new TaskCursorAdapter(this, null);
        completedTodoListView.setAdapter(mCursorAdapterCompleted);
        incompletedTodoListView.setAdapter(mCursorAdapterIncompleted);

        incompletedTodoListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, int i, long l) {
                Vibrator x = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                x.vibrate(50);

                clickedItem = l;

                CharSequence options[] = new CharSequence[]{"Mark Complete", "Delete", "Cancel"};
                final AlertDialog.Builder builder = new AlertDialog.Builder(ToDoListActivity.this);
                builder.setTitle("Do you want to: ");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch(i){
                            case 0:

                                ContentValues values = new ContentValues();
                                values.put(NeverForgetContract.TaskEntry.COLUMN_TASK_IS_COMPLETED, 1);

                                int updatedRows = getContentResolver().update(ContentUris.withAppendedId(NeverForgetContract.TaskEntry.CONTENT_URI, clickedItem), values, null, null);
                                if (updatedRows == 1) {
                                    Toast.makeText(ToDoListActivity.this, "Task Completed", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ToDoListActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
                                }

                                break;
                            case 1:
                                deleteTask(clickedItem);
                                break;
                            case 3:
                                dialogInterface.cancel();
                                break;

                        }
                    }
                });

                builder.show();

                return false;
            }
        });

        completedTodoListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, int i, long l) {
                Vibrator x = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                x.vibrate(50);

                clickedItem = l;

                CharSequence options[] = new CharSequence[]{"Mark Incomplete", "Delete", "Cancel"};
                final AlertDialog.Builder builder = new AlertDialog.Builder(ToDoListActivity.this);
                builder.setTitle("Do you want to: ");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch(i){
                            case 0:

                                ContentValues values = new ContentValues();
                                values.put(NeverForgetContract.TaskEntry.COLUMN_TASK_IS_COMPLETED, 0);

                                int updatedRows = getContentResolver().update(ContentUris.withAppendedId(NeverForgetContract.TaskEntry.CONTENT_URI, clickedItem), values, null, null);
                                if (updatedRows == 1) {
                                    Toast.makeText(ToDoListActivity.this, "Task Changed", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ToDoListActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
                                }

                                break;
                            case 1:
                                deleteTask(clickedItem);
                                break;
                            case 3:
                                dialogInterface.cancel();
                                break;

                        }
                    }
                });

                builder.show();

                return false;
            }
        });


        getLoaderManager().initLoader(COMPLETED_TASK_LOADER, null, this);
        getLoaderManager().initLoader(INCOMPLETED_TASK_LOADER, null, this);


    }

    private void deleteTask(long id){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                ToDoListActivity.this);

        final long thing = id;

        alertDialogBuilder.setTitle("Are you sure you want to delete this task?");

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getContentResolver().delete(ContentUris.withAppendedId(NeverForgetContract.TaskEntry.CONTENT_URI, thing), null, null);
                Toast.makeText(getApplicationContext(), "Task Deleted", Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
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
                NeverForgetContract.TaskEntry.COLUMN_TASK_PRIORITY,
                NeverForgetContract.TaskEntry.COLUMN_TASK_IS_COMPLETED
        };

        if(i==0){

            String selection = NeverForgetContract.TaskEntry.COLUMN_TASK_IS_COMPLETED + "=?";
            String[] selectionArgs = {String.valueOf(NeverForgetContract.TaskEntry.IS_COMPLETED_TRUE)};

            return new CursorLoader(this, NeverForgetContract.TaskEntry.CONTENT_URI, projection, selection, selectionArgs, null);

        } else {

            String selection = NeverForgetContract.TaskEntry.COLUMN_TASK_IS_COMPLETED + "=?";
            String[] selectionArgs = {String.valueOf(NeverForgetContract.TaskEntry.IS_COMPLETED_FALSE)};

            return new CursorLoader(this, NeverForgetContract.TaskEntry.CONTENT_URI, projection, selection, selectionArgs, null);

        }


    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {


        switch(loader.getId()){
            case 0:
                mCursorAdapterCompleted.swapCursor(cursor);
                break;
            case 1:
                mCursorAdapterIncompleted.swapCursor(cursor);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        switch(loader.getId()){
            case 0:
                mCursorAdapterCompleted.swapCursor(null);
                break;
            case 1:
                mCursorAdapterIncompleted.swapCursor(null);
                break;
        }
    }
}
