package com.example.android.neverforget.activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.neverforget.R;
import com.example.android.neverforget.data.NeverForgetContract;
import com.example.android.neverforget.data.NeverForgetDbHelper;

public class ContactActivity extends AppCompatActivity {

    //Globals
    int id;
    private NeverForgetDbHelper mDbHelper;

    //OnCreate method called on start of activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        mDbHelper = new NeverForgetDbHelper(this);

        /*if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                id = 0;
                displayContact(id);
            } else {
                id = extras.getInt("ID");
                displayContact(id);
            }
        } else {
            id = (int) savedInstanceState.getSerializable("ID");
            displayContact(id);
        }*/

        displayContact(1);
    }



    //displays contact with appropriate ID
    private void displayContact(int id){
        Toast.makeText(this, "" + id, Toast.LENGTH_LONG);
    }
}
