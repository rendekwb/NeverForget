package com.example.android.neverforget.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import static android.provider.Telephony.Mms.Addr.CONTACT_ID;

/**
 * Created by rendekwb on 4/12/17.
 */

public class NeverForgetProvider extends ContentProvider {

    private static final int CONTACTS = 100;

    private static final int CONTACT_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(NeverForgetContract.CONTENT_AUTHORITY, NeverForgetContract.PATH_CONTACTS, CONTACTS);
        sUriMatcher.addURI(NeverForgetContract.CONTENT_AUTHORITY, NeverForgetContract.PATH_CONTACTS + "/#", CONTACT_ID);

    }

    private NeverForgetDbHelper mDbHelper;


    public static final String LOG_TAG = NeverForgetProvider.class.getSimpleName();

    @Override
    public boolean onCreate() {
        mDbHelper = new NeverForgetDbHelper(getContext());
        return true;
    }

    /**
     * Perform the query for the given URI. Use the given projection, selection, selection arguments, and sort order.
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor;
        int match = sUriMatcher.match(uri);
        switch(match){
            case CONTACTS:
                cursor = db.query(NeverForgetContract.ContactEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case CONTACT_ID:
                selection = NeverForgetContract.ContactEntry._ID + "?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(NeverForgetContract.ContactEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        return cursor;
    }

    /**
     * Insert new data into the provider with the given ContentValues.
     */
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        int match = sUriMatcher.match(uri);
        switch (match){
            case CONTACTS:
                insertContact(uri, contentValues);
                break;
            default:
                throw new IllegalArgumentException("Insertion is not supported for this uri: " + uri);
        }

        return uri;
    }

    private Uri insertContact(Uri uri, ContentValues values) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        long newRowID = db.insert(NeverForgetContract.ContactEntry.TABLE_NAME, null, values);
        Log.v("ContactBookActivity", "New Row ID: " + newRowID);
        return ContentUris.withAppendedId(uri, newRowID);
    }

    /**
     * Updates the data at the given selection and selection arguments, with the new ContentValues.
     */
    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        int match = sUriMatcher.match(uri);
        switch (match){
            case CONTACTS:
                return updateContact(uri, contentValues, selection,selectionArgs);
            case CONTACT_ID:
                selection = NeverForgetContract.ContactEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return updateContact(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for this uri: " + uri);
        }
    }

    private int updateContact(Uri uri, ContentValues values, String selection, String[] selectionArgs){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int numRowsAffected = db.update(NeverForgetContract.ContactEntry.TABLE_NAME, values, selection, selectionArgs);
        return numRowsAffected;
    }

    /**
     * Delete the data at the given selection and selection arguments.
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int match = sUriMatcher.match(uri);
        switch(match){
            case CONTACTS:
                return deleteContact(uri, selection, selectionArgs);
            case CONTACT_ID:
                selection = NeverForgetContract.ContactEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return deleteContact(uri, selection, selectionArgs);
            default: throw new IllegalArgumentException("Delete is not supported for this uri: " + uri);
        }
    }

    private int deleteContact(Uri uri, String selection, String[] selectionArgs){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        return db.delete(NeverForgetContract.ContactEntry.TABLE_NAME, selection, selectionArgs);
    }

    /**
     * Returns the MIME type of data for the content URI.
     */
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case CONTACTS:
                return NeverForgetContract.ContactEntry.CONTENT_LIST_TYPE;
            case CONTACT_ID:
                return NeverForgetContract.ContactEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

}
