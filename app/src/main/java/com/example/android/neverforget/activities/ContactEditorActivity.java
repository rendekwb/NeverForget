package com.example.android.neverforget.activities;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.neverforget.R;
import com.example.android.neverforget.data.NeverForgetContract;
import com.example.android.neverforget.data.NeverForgetDbHelper;

import org.w3c.dom.Text;

import static android.R.attr.id;

/**
 * Created by rendekwb on 3/26/17.
 */

public class ContactEditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    public static final int CONTACT_LOADER = 0;
    private Uri mCurrentContactUri;

    //EditText views in the ContactEditor layout
    EditText firstNameEditText;
    EditText lastNameEditText;
    EditText phoneNumberEditText;
    EditText emailEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_editor);

        Intent intent = getIntent();

        mCurrentContactUri = intent.getParcelableExtra("uri");

        if(mCurrentContactUri != null){
            setTitle("Edit Contact");
        } else {
            setTitle("Add Contact");
        }

        firstNameEditText = (EditText) findViewById(R.id.first_name_edit_text);
        lastNameEditText = (EditText) findViewById(R.id.last_name_edit_text);
        phoneNumberEditText = (EditText) findViewById(R.id.phone_number_edit_text);
        emailEditText = (EditText) findViewById(R.id.email_edit_text);

        stateSpinnerSetUp();

        //Sets listener for inserting a new contact
        Button addButton = (Button) findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                manageContact();

                finish();
            }
        });

        getLoaderManager().initLoader(CONTACT_LOADER, null, this);
    }

    //Sets R.array.states as spinner, located in strings.xml
    private void stateSpinnerSetUp(){

        Spinner stateSpinner = (Spinner) findViewById(R.id.states_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.states, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(adapter);
    }


    //Inserts new contact into database
    private void manageContact(){

        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();
        String phoneNumber = phoneNumberEditText.getText().toString();
        String email = emailEditText.getText().toString();

        ContentValues values = new ContentValues();
        values.put(NeverForgetContract.ContactEntry.COLUMN_CONTACT_FIRST_NAME, firstName);
        values.put(NeverForgetContract.ContactEntry.COLUMN_CONTACT_LAST_NAME, lastName);
        values.put(NeverForgetContract.ContactEntry.COLUMN_CONTACT_PHONE_NUMBER, phoneNumber);
        values.put(NeverForgetContract.ContactEntry.COLUMN_CONTACT_EMAIL, email);

        if(mCurrentContactUri != null){
            int updatedRows = getContentResolver().update(ContentUris.withAppendedId(NeverForgetContract.ContactEntry.CONTENT_URI, ContentUris.parseId(mCurrentContactUri)), values, null, null);

            if(updatedRows == 1){
                Toast.makeText(this, "Contact Updated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
            }
        } else {
            Uri uri = getContentResolver().insert(NeverForgetContract.ContactEntry.CONTENT_URI, values);

            if(uri != null){
                Toast.makeText(this, "Contact Saved", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "An error occurred", Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                NeverForgetContract.ContactEntry._ID,
                NeverForgetContract.ContactEntry.COLUMN_CONTACT_FIRST_NAME,
                NeverForgetContract.ContactEntry.COLUMN_CONTACT_LAST_NAME,
                NeverForgetContract.ContactEntry.COLUMN_CONTACT_PHONE_NUMBER,
                NeverForgetContract.ContactEntry.COLUMN_CONTACT_EMAIL
        };

        Loader<Cursor> c = new CursorLoader(this,   // Parent activity context
                mCurrentContactUri,         // Query the content URI for the current pet
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
            firstNameEditText.setText(cursor.getString(cursor.getColumnIndex(NeverForgetContract.ContactEntry.COLUMN_CONTACT_FIRST_NAME)));
            lastNameEditText.setText(cursor.getString(cursor.getColumnIndex(NeverForgetContract.ContactEntry.COLUMN_CONTACT_LAST_NAME)));
            phoneNumberEditText.setText(cursor.getString(cursor.getColumnIndex(NeverForgetContract.ContactEntry.COLUMN_CONTACT_PHONE_NUMBER)));
            emailEditText.setText(cursor.getString(cursor.getColumnIndex(NeverForgetContract.ContactEntry.COLUMN_CONTACT_EMAIL)));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        firstNameEditText.setText("");
        lastNameEditText.setText("");
        phoneNumberEditText.setText("");
        emailEditText.setText("");
    }
}
