package com.example.android.neverforget.activities;

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
        dateTextView.setText("Date: " + new SimpleDateFormat("M/dd/yyyy").format(todaysDate));


        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {

                dateTextView.setText("Date: "  + (month + 1) + "/" + day + "/" + year);
            }
        });

        viewEventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
