package com.example.android.neverforget.activities;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.neverforget.R;
import com.example.android.neverforget.data.NeverForgetContract;
import com.example.android.neverforget.data.NeverForgetDbHelper;

/**
 * Created by rendekwb on 3/26/17.
 */

public class ContactEditorActivity extends AppCompatActivity {

    private NeverForgetDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_editor);

        mDbHelper = new NeverForgetDbHelper(this);

        stateSpinnerSetUp();

        //Sets listener for inserting a new contact
        Button addButton = (Button) findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertContact();
                finish();
            }
        });
    }

    //Sets R.array.states as spinner, located in strings.xml
    private void stateSpinnerSetUp(){

        Spinner stateSpinner = (Spinner) findViewById(R.id.states_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.states, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(adapter);
    }

    //Inserts new contact into database
    private void insertContact(){


        EditText firstNameEditText = (EditText) findViewById(R.id.first_name_edit_text);
        String firstName = firstNameEditText.getText().toString();

        EditText lastNameEditText = (EditText) findViewById(R.id.last_name_edit_text);
        String lastName = lastNameEditText.getText().toString();

        EditText phoneNumberEditText = (EditText) findViewById(R.id.phone_number_edit_text);
        String phoneNumber = phoneNumberEditText.getText().toString();

        EditText emailEditText = (EditText) findViewById(R.id.email_edit_text);
        String email = emailEditText.getText().toString();

        ContentValues values = new ContentValues();
        values.put(NeverForgetContract.ContactEntry.COLUMN_CONTACT_FIRST_NAME, firstName);
        values.put(NeverForgetContract.ContactEntry.COLUMN_CONTACT_LAST_NAME, lastName);
        values.put(NeverForgetContract.ContactEntry.COLUMN_CONTACT_PHONE_NUMBER, phoneNumber);
        values.put(NeverForgetContract.ContactEntry.COLUMN_CONTACT_EMAIL, email);

        Uri uri = getContentResolver().insert(NeverForgetContract.ContactEntry.CONTENT_URI, values);

        if(uri != null){
            Toast.makeText(this, "Contact Saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "An error occurred", Toast.LENGTH_LONG).show();
        }


    }

}
