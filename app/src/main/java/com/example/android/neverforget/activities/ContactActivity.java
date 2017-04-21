package com.example.android.neverforget.activities;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.neverforget.R;
import com.example.android.neverforget.data.NeverForgetContract;

import static android.R.attr.button;

public class ContactActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    public static final int CONTACT_LOADER = 0;
    private Uri mCurrentContactUri;

    //TextViews in ContactActivity
    TextView nameTextView, phoneNumberTextView, alternatePhoneNumberTextView, emailTextView, alternateEmailTextView, addressTextView;

    //OnCreate method called on start of activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        final Context context = this;

        nameTextView = (TextView) findViewById(R.id.single_contact_name);
        phoneNumberTextView = (TextView) findViewById(R.id.single_contact_phone_number);
        alternatePhoneNumberTextView = (TextView) findViewById(R.id.single_contact_alternate_phone_number);
        emailTextView = (TextView) findViewById(R.id.single_contact_email);
        alternateEmailTextView = (TextView) findViewById(R.id.single_contact_alternate_email);
        addressTextView = (TextView) findViewById(R.id.single_contact_address);

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
            public void onClick(View view){

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set title
                alertDialogBuilder.setTitle("Are you sure you want to delete this contact?");

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                getContentResolver().delete(ContentUris.withAppendedId(NeverForgetContract.ContactEntry.CONTENT_URI, ContentUris.parseId(mCurrentContactUri)), null, null);
                                Toast.makeText(getApplicationContext(), "Contact Deleted", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
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
                NeverForgetContract.ContactEntry.COLUMN_CONTACT_ALTERNATIVE_PHONE_NUMBER,
                NeverForgetContract.ContactEntry.COLUMN_CONTACT_EMAIL,
                NeverForgetContract.ContactEntry.COLUMN_CONTACT_ALTERNATIVE_EMAIL,
                NeverForgetContract.ContactEntry.COLUMN_CONTACT_ADDRESS,
                NeverForgetContract.ContactEntry.COLUMN_CONTACT_CITY,
                NeverForgetContract.ContactEntry.COLUMN_CONTACT_STATE,
                NeverForgetContract.ContactEntry.COLUMN_CONTACT_ZIP,
                NeverForgetContract.ContactEntry.COLUMN_CONTACT_FACEBOOK_URL,
                NeverForgetContract.ContactEntry.COLUMN_CONTACT_TWITTER_URL,
                NeverForgetContract.ContactEntry.COLUMN_CONTACT_INSTAGRAM_URL
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
    public void onLoadFinished(Loader<Cursor> loader, final Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }
        if(cursor.moveToFirst()){
            String name = cursor.getString(cursor.getColumnIndex(NeverForgetContract.ContactEntry.COLUMN_CONTACT_FIRST_NAME)) + " " +
                    cursor.getString(cursor.getColumnIndex(NeverForgetContract.ContactEntry.COLUMN_CONTACT_LAST_NAME));

            String alternatePhoneNumber = (cursor.getString(cursor.getColumnIndex(NeverForgetContract.ContactEntry.COLUMN_CONTACT_ALTERNATIVE_PHONE_NUMBER)).equals("") ? "No Entry" : cursor.getString(cursor.getColumnIndex(NeverForgetContract.ContactEntry.COLUMN_CONTACT_ALTERNATIVE_PHONE_NUMBER)));
            String alternateEmail = (cursor.getString(cursor.getColumnIndex(NeverForgetContract.ContactEntry.COLUMN_CONTACT_ALTERNATIVE_EMAIL)).equals("") ? "No Entry" : cursor.getString(cursor.getColumnIndex(NeverForgetContract.ContactEntry.COLUMN_CONTACT_ALTERNATIVE_EMAIL)));

            String address = (cursor.getString(cursor.getColumnIndex(NeverForgetContract.ContactEntry.COLUMN_CONTACT_ADDRESS)).equals("") ? "No Entry" : cursor.getString(cursor.getColumnIndex(NeverForgetContract.ContactEntry.COLUMN_CONTACT_ADDRESS)) + ", " +
                    cursor.getString(cursor.getColumnIndex(NeverForgetContract.ContactEntry.COLUMN_CONTACT_CITY)) + ", " +
                    cursor.getString(cursor.getColumnIndex(NeverForgetContract.ContactEntry.COLUMN_CONTACT_STATE)) + " " +
                    cursor.getString(cursor.getColumnIndex(NeverForgetContract.ContactEntry.COLUMN_CONTACT_ZIP)));


            nameTextView.setText(name);
            phoneNumberTextView.setText("Phone Number: " + cursor.getString(cursor.getColumnIndex(NeverForgetContract.ContactEntry.COLUMN_CONTACT_PHONE_NUMBER)));
            alternatePhoneNumberTextView.setText("Alternate Phone Number: " + alternatePhoneNumber);
            emailTextView.setText("Email: " + cursor.getString(cursor.getColumnIndex(NeverForgetContract.ContactEntry.COLUMN_CONTACT_EMAIL)));
            alternateEmailTextView.setText("Alternate Email: " + alternateEmail);
            addressTextView.setText("Address: " + address);


            Button facebookButton = (Button) findViewById(R.id.facebook_image_button);
            Button instagramButton = (Button) findViewById(R.id.instagram_image_button);
            Button twitterButton = (Button) findViewById(R.id.twitter_image_button);
            facebookButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    if(Uri.parse(cursor.getString(cursor.getColumnIndex(NeverForgetContract.ContactEntry.COLUMN_CONTACT_FACEBOOK_URL))).equals("")){
                        Toast.makeText(getApplicationContext(), "There is no Facebook account associated with this contact", Toast.LENGTH_LONG).show();
                    } else {
                        intent.setData(Uri.parse(cursor.getString(cursor.getColumnIndex(NeverForgetContract.ContactEntry.COLUMN_CONTACT_FACEBOOK_URL))));
                        try {
                            startActivity(intent);
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "There is no Facebook account associated with this contact", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });

            instagramButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(cursor.getString(cursor.getColumnIndex(NeverForgetContract.ContactEntry.COLUMN_CONTACT_INSTAGRAM_URL))));
                    try{
                        startActivity(intent);
                    } catch(Exception e){
                        Toast.makeText(getApplicationContext(), "There is no Instagram account associated with this contact", Toast.LENGTH_LONG).show();
                    }
                }
            });

            twitterButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(cursor.getString(cursor.getColumnIndex(NeverForgetContract.ContactEntry.COLUMN_CONTACT_TWITTER_URL))));
                    try{
                        startActivity(intent);
                    } catch(Exception e){
                        Toast.makeText(getApplicationContext(), "There is no Twitter account associated with this contact", Toast.LENGTH_LONG).show();
                    }

                }
            });
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


}


