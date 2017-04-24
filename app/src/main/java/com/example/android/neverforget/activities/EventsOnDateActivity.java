package com.example.android.neverforget.activities;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.neverforget.R;
import com.example.android.neverforget.adapters.CalendarEventCursorAdapter;
import com.example.android.neverforget.adapters.ContactCursorAdapter;
import com.example.android.neverforget.data.NeverForgetContract;

import static android.R.attr.x;

public class EventsOnDateActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int EVENTS_LOADER = 0;

    String date;

    long clickedItem;


    CalendarEventCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_on_date);

        Intent intent = getIntent();
        date = intent.getStringExtra("date");
        TextView header = (TextView) findViewById(R.id.events_on_date_header);
        header.setText(date);

        ListView eventListView = (ListView) findViewById(R.id.event_list_view);
        mCursorAdapter = new CalendarEventCursorAdapter(this, null);
        eventListView.setAdapter(mCursorAdapter);



        eventListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Vibrator x = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                x.vibrate(50);

                clickedItem = l;

                CharSequence options[] = new CharSequence[]{"Update", "Delete", "Cancel"};
                final AlertDialog.Builder builder = new AlertDialog.Builder(EventsOnDateActivity.this);
                builder.setTitle("Do you want to: ");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch(i){
                            case 0:
                                Uri uri = ContentUris.withAppendedId(NeverForgetContract.CalendarEventEntry.CONTENT_URI, clickedItem);
                                Intent intent = new Intent(EventsOnDateActivity.this, CalendarEventEditorActivity.class);
                                intent.putExtra("uri", uri);
                                intent.putExtra("date", date);
                                startActivity(intent);
                                break;
                            case 1:
                                deleteEvent(clickedItem);
                                break;
                            case 2:
                                dialogInterface.cancel();
                                break;
                            default:

                        }
                    }
                });

                builder.show();

                return false;
            }

        });

        Button addEventButton = (Button) findViewById(R.id.add_event_button);
        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventsOnDateActivity.this, CalendarEventEditorActivity.class);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });

        getLoaderManager().initLoader(EVENTS_LOADER, null, this);
    }

    private void deleteEvent(long item){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                EventsOnDateActivity.this);

        final long thing = item;

        // set title
        alertDialogBuilder.setTitle("Are you sure you want to delete this event?");

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        getContentResolver().delete(ContentUris.withAppendedId(NeverForgetContract.CalendarEventEntry.CONTENT_URI, thing), null, null);
                        Toast.makeText(getApplicationContext(), "Event Deleted", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        String[] projection = {
                NeverForgetContract.CalendarEventEntry._ID,
                NeverForgetContract.CalendarEventEntry.COLUMN_EVENT_DATE,
                NeverForgetContract.CalendarEventEntry.COLUMN_EVENT_DESCRIPTION,
                NeverForgetContract.CalendarEventEntry.COLUMN_EVENT_LOCATION,
                NeverForgetContract.CalendarEventEntry.COLUMN_EVENT_START_TIME,
                NeverForgetContract.CalendarEventEntry.COLUMN_EVENT_END_TIME
        };

        String selection = NeverForgetContract.CalendarEventEntry.COLUMN_EVENT_DATE + "=?";
        String[] selectionArgs = {date};

        return new CursorLoader(this, NeverForgetContract.CalendarEventEntry.CONTENT_URI, projection, selection, selectionArgs, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mCursorAdapter.swapCursor(cursor);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
