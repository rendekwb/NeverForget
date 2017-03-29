package com.example.android.neverforget.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.android.neverforget.adapters.ContactAdapter;
import com.example.android.neverforget.data.NeverForgetContract;
import com.example.android.neverforget.data.NeverForgetDbHelper;
import com.example.android.neverforget.models.Contact;
import com.example.android.neverforget.R;

import java.util.ArrayList;

public class ContactBookActivity extends AppCompatActivity {

    private NeverForgetDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_book);

        mDbHelper = new NeverForgetDbHelper(this);

        displayContacts();

        Button editorViewButton = (Button) findViewById(R.id.add_contact_button);
        editorViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ContactBookActivity.this, ContactEditorActivity.class);
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();
        displayContacts();
    }


    private void displayContacts(){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                NeverForgetContract.ContactEntry._ID,
                NeverForgetContract.ContactEntry.COLUMN_CONTACT_FIRST_NAME,
                NeverForgetContract.ContactEntry.COLUMN_CONTACT_LAST_NAME
        };

        Cursor cursor = db.query(
                NeverForgetContract.ContactEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        ArrayList <Contact> contactList = new ArrayList<Contact>();

        for(int i = 0; i < cursor.getCount(); i++){

            cursor.moveToPosition(i);

            int firstNameColumnIndex = cursor.getColumnIndex(NeverForgetContract.ContactEntry.COLUMN_CONTACT_FIRST_NAME);
            String firstName = cursor.getString(firstNameColumnIndex);

            int lastNameColumnIndex = cursor.getColumnIndex(NeverForgetContract.ContactEntry.COLUMN_CONTACT_LAST_NAME);
            String lastName = cursor.getString(lastNameColumnIndex);


            contactList.add(new Contact(firstName, lastName, "232-234-4923", "bcrystal@gmail.com"));

        }


        ContactAdapter contactsAdapter = new ContactAdapter(this, R.layout.contact_list_item, contactList);

        ListView contactListView = (ListView) findViewById(R.id.contact_book_list_view);

        contactListView.setAdapter(contactsAdapter);

        cursor.close();
    }


}
