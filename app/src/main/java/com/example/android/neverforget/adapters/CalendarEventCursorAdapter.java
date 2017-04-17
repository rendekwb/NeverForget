package com.example.android.neverforget.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.neverforget.R;
import com.example.android.neverforget.models.Contact;

import static android.R.attr.description;

/**
 * Created by rendekwb on 4/16/17.
 */

public class CalendarEventCursorAdapter extends CursorAdapter {

    public CalendarEventCursorAdapter(Context context, Cursor cursor){
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.calendar_event_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView descriptionTextView = (TextView) view.findViewById(R.id.event_description);
        TextView locationTextView = (TextView) view.findViewById(R.id.event_location);
        TextView startTimeTextView = (TextView) view.findViewById(R.id.event_start_time);
        TextView endTimeTextView = (TextView) view.findViewById(R.id.event_end_time);

        String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
        String location = cursor.getString(cursor.getColumnIndexOrThrow("location"));
        String startTime = cursor.getString(cursor.getColumnIndexOrThrow("startTime"));
        String endTime = cursor.getString(cursor.getColumnIndexOrThrow("endTime"));

        descriptionTextView.setText(description);
        locationTextView.setText(location);
        startTimeTextView.setText("Start Time: " + startTime);
        endTimeTextView.setText("End Time: " + endTime);
    }
}
