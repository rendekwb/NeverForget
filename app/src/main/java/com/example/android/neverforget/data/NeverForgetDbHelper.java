package com.example.android.neverforget.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.neverforget.models.Contact;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static com.example.android.neverforget.data.NeverForgetContract.*;

/**
 * Created by rendekwb on 3/21/17.
 */

public class NeverForgetDbHelper extends SQLiteOpenHelper {

    //Global variables for SQLite database
    private static final String DATABASE_NAME = "never_forget.db";
    private static final int DATABASE_VERSION = 1;

    //Constructor
    public NeverForgetDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    //Method called when no prior instance of the database is established on the device
    //Creates two tables in the never_forget db (contacts, tasks).
    @Override
    public void onCreate(SQLiteDatabase db){
        String SQL_CREATE_CONTACTS_TABLE =
                "CREATE TABLE " + ContactEntry.TABLE_NAME + "(" +
                ContactEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContactEntry.COLUMN_CONTACT_FIRST_NAME + " TEXT NOT NULL, " +
                ContactEntry.COLUMN_CONTACT_LAST_NAME + " TEXT NOT NULL, " +
                ContactEntry.COLUMN_CONTACT_PHONE_NUMBER + " TEXT, " +
                ContactEntry.COLUMN_CONTACT_ALTERNATIVE_PHONE_NUMBER + " TEXT, " +
                ContactEntry.COLUMN_CONTACT_EMAIL + " TEXT, " +
                ContactEntry.COLUMN_CONTACT_ALTERNATIVE_EMAIL + " TEXT, " +
                ContactEntry.COLUMN_CONTACT_ADDRESS + " TEXT, " +
                ContactEntry.COLUMN_CONTACT_CITY + " TEXT, " +
                ContactEntry.COLUMN_CONTACT_STATE + " TEXT, " +
                ContactEntry.COLUMN_CONTACT_ZIP + " TEXT, " +
                ContactEntry.COLUMN_CONTACT_FACEBOOK_URL + " TEXT, " +
                ContactEntry.COLUMN_CONTACT_TWITTER_URL + " TEXT, " +
                ContactEntry.COLUMN_CONTACT_INSTAGRAM_URL + " TEXT);";

        String SQL_CREATE_TASKS_TABLE =
                "CREATE TABLE " + TaskEntry.TABLE_NAME + "(" +
                TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskEntry.COLUMN_TASK_DESCRIPTION + " TEXT NOT NULL, " +
                TaskEntry.COLUMN_TASK_PRIORITY + " INTEGER NOT NULL DEFAULT 0, " +
                TaskEntry.COLUMN_TASK_IS_COMPLETED + " INTEGER NOT NULL DEFAULT 0, " +
                TaskEntry.COLUMN_TASK_CREATED_ON + " TEXT, " +
                TaskEntry.COLUMN_TASK_COMPLETED_ON + " TEXT);";

        String SQL_CREATE_CALENDAR_EVENTS_TABLE =
                "CREATE TABLE " + CalendarEventEntry.TABLE_NAME + "(" +
                CalendarEventEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CalendarEventEntry.COLUMN_EVENT_DATE + " TEXT NOT NULL, " +
                CalendarEventEntry.COLUMN_EVENT_DESCRIPTION + " TEXT NOT NULL, " +
                CalendarEventEntry.COLUMN_EVENT_LOCATION + " TEXT NOT NULL, " +
                CalendarEventEntry.COLUMN_EVENT_START_TIME + " TEXT NOT NULL, " +
                CalendarEventEntry.COLUMN_EVENT_END_TIME + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_CONTACTS_TABLE);
        db.execSQL(SQL_CREATE_TASKS_TABLE);
        db.execSQL(SQL_CREATE_CALENDAR_EVENTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
