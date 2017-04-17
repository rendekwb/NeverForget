package com.example.android.neverforget.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.android.neverforget.R;
import com.example.android.neverforget.data.NeverForgetContract;

import static android.R.attr.inset;

public class CalendarEventEditorActivity extends AppCompatActivity {

    String date;
    EditText descriptionEditText, locationEditText, startTimeEditText, endTimeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_event_editor);

        descriptionEditText = (EditText) findViewById(R.id.event_description_edit_text);
        locationEditText = (EditText) findViewById(R.id.event_location_edit_text);
        startTimeEditText = (EditText) findViewById(R.id.event_start_time_edit_text);
        endTimeEditText = (EditText) findViewById(R.id.event_end_time_edit_text);

        Intent intent = getIntent();
        date = intent.getStringExtra("date");


        Button addButton = (Button) findViewById(R.id.add_new_event_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertEvent(date);
                finish();
            }
        });
    }

    private void insertEvent(String date){

        String description = descriptionEditText.getText().toString();
        String location = locationEditText.getText().toString();
        String startTime = startTimeEditText.getText().toString();
        String endTime = endTimeEditText.getText().toString();

        ContentValues values = new ContentValues();
        values.put(NeverForgetContract.CalendarEventEntry.COLUMN_EVENT_DESCRIPTION, description);
        values.put(NeverForgetContract.CalendarEventEntry.COLUMN_EVENT_LOCATION, location);
        values.put(NeverForgetContract.CalendarEventEntry.COLUMN_EVENT_START_TIME, startTime);
        values.put(NeverForgetContract.CalendarEventEntry.COLUMN_EVENT_END_TIME, endTime);
        values.put(NeverForgetContract.CalendarEventEntry.COLUMN_EVENT_DATE, date);

        getContentResolver().insert(NeverForgetContract.CalendarEventEntry.CONTENT_URI, values);
    }
}
