package com.example.android.neverforget.activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

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

        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                id = 0;
            } else {
                id = extras.getInt("ID");
                //displayContact();
            }
        } else {
            id = (int) savedInstanceState.getSerializable("ID");
            //displayContact();
        }
    }



    //displays contact with appropriate ID
    private void displayContact(){

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                NeverForgetContract.ContactEntry.COLUMN_CONTACT_FIRST_NAME,
                NeverForgetContract.ContactEntry.COLUMN_CONTACT_LAST_NAME,
        };

        String selection = NeverForgetContract.ContactEntry._ID + " =?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query(
                NeverForgetContract.ContactEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        int firstNameIndex = cursor.getColumnIndex(NeverForgetContract.ContactEntry.COLUMN_CONTACT_FIRST_NAME);
        String firstName = cursor.getString(firstNameIndex);

        TextView firstNameTextView = (TextView) findViewById(R.id.single_contact_name);
        firstNameTextView.setText(firstName);

    }
}
