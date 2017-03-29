package com.example.android.neverforget.activities;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

        Button addButton = (Button) findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertContact();
                finish();
            }
        });
    }

    private void insertContact(){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        EditText firstNameEditText = (EditText) findViewById(R.id.first_name_edit_text);
        String firstName = firstNameEditText.getText().toString();

        EditText lastNameEditText = (EditText) findViewById(R.id.last_name_edit_text);
        String lastName = lastNameEditText.getText().toString();

        ContentValues values = new ContentValues();
        values.put(NeverForgetContract.ContactEntry.COLUMN_CONTACT_FIRST_NAME, firstName);
        values.put(NeverForgetContract.ContactEntry.COLUMN_CONTACT_LAST_NAME, lastName);

        long newRowId = db.insert(NeverForgetContract.ContactEntry.TABLE_NAME, null, values);

        Log.v("ContactBookActivity", "New Row ID: " + newRowId);
    }

}
