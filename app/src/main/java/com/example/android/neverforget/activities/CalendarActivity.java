package com.example.android.neverforget.activities;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;
import android.text.format.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;

import com.example.android.neverforget.R;
import com.example.android.neverforget.data.NeverForgetContract;


public class CalendarActivity extends AppCompatActivity {

    TextView dateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        Button viewEventsButton = (Button) findViewById(R.id.view_events_button);
        CalendarView calendar = (CalendarView) findViewById(R.id.calendar);
        calendar.setFirstDayOfWeek(1);

        dateTextView = (TextView) findViewById(R.id.date_text_view);
        Date todaysDate = new Date();
        final String[] date = {new SimpleDateFormat("M/dd/yyyy").format(todaysDate)};
        dateTextView.setText("Date: " + date[0]);


        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {

                date[0] = (month + 1) + "/" + day + "/" + year;
                dateTextView.setText("Date: "  + date[0]);
            }
        });

        viewEventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalendarActivity.this, EventsOnDateActivity.class);
                intent.putExtra("date", date[0]);
                startActivity(intent);
            }
        });

    }





}
