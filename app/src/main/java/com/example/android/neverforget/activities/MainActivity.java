package com.example.android.neverforget.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.android.neverforget.R;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Defining ImageView Objects from activity_main.xml
        ImageView toDoListImageView = (ImageView) findViewById(R.id.list_icon);
        ImageView calendarImageView = (ImageView) findViewById(R.id.calendar_icon);
        ImageView contactBookImageView = (ImageView) findViewById(R.id.contact_book_icon);

        // Setting click listeners for each ImageView

        // ToDoList Listener
        toDoListImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ToDoListActivity.class);
                startActivity(intent);
            }
        });

        // Calendar Listener
        calendarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });

        // Contact Book Listener
        contactBookImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ContactBookActivity.class);
                startActivity(intent);
            }
        });


    }
}
