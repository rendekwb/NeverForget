package com.example.android.neverforget.activities;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.android.neverforget.adapters.ContactAdapter;
import com.example.android.neverforget.adapters.ContactCursorAdapter;
import com.example.android.neverforget.data.NeverForgetContract;
import com.example.android.neverforget.data.NeverForgetDbHelper;
import com.example.android.neverforget.data.NeverForgetProvider;
import com.example.android.neverforget.models.Contact;
import com.example.android.neverforget.R;

import java.util.ArrayList;

import static android.content.ContentUris.withAppendedId;

public class ContactBookActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int CONTACT_LOADER = 0;

    ContactCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_book);

        Button editorViewButton = (Button) findViewById(R.id.add_contact_button);
        editorViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ContactBookActivity.this, ContactEditorActivity.class);
                startActivity(intent);

            }
        });

        ListView contactListView = (ListView) findViewById(R.id.contact_book_list_view);

        mCursorAdapter = new ContactCursorAdapter(this, null);
        contactListView.setAdapter(mCursorAdapter);

        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Uri uri = ContentUris.withAppendedId(NeverForgetContract.ContactEntry.CONTENT_URI, id);
                Intent intent = new Intent(ContactBookActivity.this, ContactActivity.class);
                intent.putExtra("uri", uri);
                startActivity(intent);

            }
        });

        getLoaderManager().initLoader(CONTACT_LOADER, null, this);

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

        return new CursorLoader(this, NeverForgetContract.ContactEntry.CONTENT_URI, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
}
