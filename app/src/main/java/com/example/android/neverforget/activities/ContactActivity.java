package com.example.android.neverforget.activities;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.neverforget.R;
import com.example.android.neverforget.data.NeverForgetContract;
import com.example.android.neverforget.data.NeverForgetDbHelper;

import static android.R.attr.id;

public class ContactActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    public static final int CONTACT_LOADER = 0;
    private Uri mCurrentContactUri;

    //OnCreate method called on start of activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        Intent intent = getIntent();

        mCurrentContactUri = intent.getParcelableExtra("uri");

        getLoaderManager().initLoader(CONTACT_LOADER, null, this);

        Button updateButton = (Button) findViewById(R.id.update_contact_button);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = ContentUris.withAppendedId(NeverForgetContract.ContactEntry.CONTENT_URI, ContentUris.parseId(mCurrentContactUri));
                Intent intent = new Intent(ContactActivity.this, ContactEditorActivity.class);
                intent.putExtra("uri", uri);
                startActivity(intent);
            }
        });

        Button deleteButton = (Button) findViewById(R.id.delete_contact_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Contact Deleted", Toast.LENGTH_LONG).show();
            }
        });

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
            TextView nameTextView = (TextView) findViewById(R.id.single_contact_name);
            nameTextView.setText(cursor.getString(cursor.getColumnIndex(NeverForgetContract.ContactEntry.COLUMN_CONTACT_FIRST_NAME)));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
