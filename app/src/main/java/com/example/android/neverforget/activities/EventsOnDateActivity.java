package com.example.android.neverforget.activities;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.neverforget.R;
import com.example.android.neverforget.adapters.CalendarEventCursorAdapter;
import com.example.android.neverforget.adapters.ContactCursorAdapter;
import com.example.android.neverforget.data.NeverForgetContract;

public class EventsOnDateActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int EVENTS_LOADER = 0;

    String date;

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
