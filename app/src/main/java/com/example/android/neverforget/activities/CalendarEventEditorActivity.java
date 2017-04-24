package com.example.android.neverforget.activities;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.android.neverforget.R;
import com.example.android.neverforget.data.NeverForgetContract;

import static android.R.attr.description;
import static android.R.attr.inset;
import static android.R.attr.start;

public class CalendarEventEditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int CONTACT_LOADER = 0;

    String date;
    EditText descriptionEditText, locationEditText;
    TextView eventErrorTextView;
    TimePicker startTimePicker, endTimePicker;
    private Uri mCurrentEventUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_event_editor);

        descriptionEditText = (EditText) findViewById(R.id.event_description_edit_text);
        locationEditText = (EditText) findViewById(R.id.event_location_edit_text);
        startTimePicker = (TimePicker) findViewById(R.id.event_start_time_picker);
        endTimePicker = (TimePicker) findViewById(R.id.event_end_time_picker);

        eventErrorTextView = (TextView) findViewById(R.id.calendar_event_error_text_view);
        eventErrorTextView.setVisibility(TextView.GONE);

        Intent intent = getIntent();
        date = intent.getStringExtra("date");

        Button addButton = (Button) findViewById(R.id.add_new_event_button);

        mCurrentEventUri = intent.getParcelableExtra("uri");

        if(mCurrentEventUri != null){
            setTitle("Edit Event");
            getLoaderManager().initLoader(CONTACT_LOADER, null, this);
            addButton.setText("Update Event");
            TextView header = (TextView) findViewById(R.id.event_editor_header);
            header.setText("Update Event");

        } else {
            setTitle("Add Event");
        }



        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manageEvent();
            }
        });

    }

    private String checkErrors(){
        if(descriptionEditText.getText().toString().equals("")){
            descriptionEditText.requestFocus();
            eventErrorTextView.setVisibility(TextView.VISIBLE);
            return "Description is required.";
        }

        return "";
    }

    private void manageEvent(){

        eventErrorTextView.setText(checkErrors());

        if(eventErrorTextView.getText().equals("")) {

            String startAMPM = "AM";
            String endAMPM = "AM";

            String description = descriptionEditText.getText().toString();
            String location = locationEditText.getText().toString();
            int startHour = startTimePicker.getCurrentHour();
            int startMinute = startTimePicker.getCurrentMinute();
            int endHour = endTimePicker.getCurrentHour();
            int endMinute = endTimePicker.getCurrentMinute();
            String startMinuteString = Integer.toString(startMinute);
            String endMinuteString = Integer.toString(endMinute);

            if (startHour > 12) {
                startHour -= 12;
                startAMPM = "PM";
            }

            if (endHour > 12) {
                endHour -= 12;
                endAMPM = "PM";
            }

            if (startMinute < 10) {
                startMinuteString = "0" + startMinuteString;
            }

            if (endMinute < 10) {
                endMinuteString = "0" + endMinuteString;
            }


            String startTime = startHour + ":" + startMinuteString + " " + startAMPM;
            String endTime = endHour + ":" + endMinuteString + " " + endAMPM;


            ContentValues values = new ContentValues();
            values.put(NeverForgetContract.CalendarEventEntry.COLUMN_EVENT_DESCRIPTION, description);
            values.put(NeverForgetContract.CalendarEventEntry.COLUMN_EVENT_LOCATION, location);
            values.put(NeverForgetContract.CalendarEventEntry.COLUMN_EVENT_START_TIME, startTime);
            values.put(NeverForgetContract.CalendarEventEntry.COLUMN_EVENT_END_TIME, endTime);
            values.put(NeverForgetContract.CalendarEventEntry.COLUMN_EVENT_DATE, date);

            if (mCurrentEventUri != null) {
                int numRowsUpdated = getContentResolver().update(
                        ContentUris.withAppendedId(NeverForgetContract.CalendarEventEntry.CONTENT_URI,
                        ContentUris.parseId(mCurrentEventUri)), values, null, null);

                if (numRowsUpdated == 1) {
                    Toast.makeText(this, "Event Updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
                }

            } else {

                Uri uri = getContentResolver().insert(NeverForgetContract.CalendarEventEntry.CONTENT_URI, values);

                if (uri != null) {
                    Toast.makeText(this, "Event Saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "An error occurred", Toast.LENGTH_LONG).show();
                }
            }

            finish();
        } else {
            ScrollView scrollView = (ScrollView) findViewById(R.id.calendar_event_editor_scroll_view);
            scrollView.smoothScrollTo(0,0);
        }


    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                NeverForgetContract.CalendarEventEntry.COLUMN_EVENT_DESCRIPTION,
                NeverForgetContract.CalendarEventEntry.COLUMN_EVENT_LOCATION,
                NeverForgetContract.CalendarEventEntry.COLUMN_EVENT_START_TIME,
                NeverForgetContract.CalendarEventEntry.COLUMN_EVENT_END_TIME
        };

        Loader<Cursor> c = new CursorLoader(this,
                mCurrentEventUri,         // Query the content URI for the current pet
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);

        return c;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        if(cursor.moveToFirst()){
            descriptionEditText.setText(cursor.getString(cursor.getColumnIndex(NeverForgetContract.CalendarEventEntry.COLUMN_EVENT_DESCRIPTION)));
            locationEditText.setText(cursor.getString(cursor.getColumnIndex(NeverForgetContract.CalendarEventEntry.COLUMN_EVENT_LOCATION)));

            String startTime = cursor.getString(cursor.getColumnIndex(NeverForgetContract.CalendarEventEntry.COLUMN_EVENT_START_TIME));
            String endTime = cursor.getString(cursor.getColumnIndex(NeverForgetContract.CalendarEventEntry.COLUMN_EVENT_END_TIME));

            String[] startTimes = startTime.split(":");
            String[] endTimes = endTime.split(":");

            if(startTimes[1].substring(3).equals("PM")){
                int st = Integer.valueOf(startTimes[0]) + 12;
                startTimes[0] = String.valueOf(st);
            }

            if(endTimes[1].substring(3).equals("PM")){
                int et = Integer.valueOf(endTimes[0]) + 12;
                endTimes[0] = String.valueOf(et);
            }

            startTimePicker.setCurrentHour(Integer.valueOf(startTimes[0]));
            startTimePicker.setCurrentMinute(Integer.valueOf(startTimes[1].substring(0,2)));
            endTimePicker.setCurrentHour(Integer.valueOf(endTimes[0]));
            endTimePicker.setCurrentMinute(Integer.valueOf(endTimes[1].substring(0,2)));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
