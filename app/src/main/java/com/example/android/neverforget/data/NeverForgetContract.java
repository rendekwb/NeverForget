package com.example.android.neverforget.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

import java.sql.Date;

/**
 * Created by rendekwb on 3/26/17.
 */

/**
 * Create Contract for never_forget.db
 * Includes two tables and their corresponding fields
 */


public final class NeverForgetContract {

    public static final String CONTENT_AUTHORITY = "com.example.android.neverforget";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_CONTACTS = "contacts";

    public static final String PATH_CALENDAR_EVENTS = "events";

    public static class ContactEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_CONTACTS);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CONTACTS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CONTACTS;

        public static final String TABLE_NAME = "contacts";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_CONTACT_FIRST_NAME = "firstName";
        public static final String COLUMN_CONTACT_LAST_NAME = "lastName";
        public static final String COLUMN_CONTACT_PHONE_NUMBER = "phoneNumber";
        public static final String COLUMN_CONTACT_ALTERNATIVE_PHONE_NUMBER = "alternativePhoneNumber";
        public static final String COLUMN_CONTACT_EMAIL = "email";
        public static final String COLUMN_CONTACT_ALTERNATIVE_EMAIL = "alternativeEmail";
        public static final String COLUMN_CONTACT_ADDRESS = "address";
        public static final String COLUMN_CONTACT_CITY = "city";
        public static final String COLUMN_CONTACT_STATE = "state";
        public static final String COLUMN_CONTACT_ZIP = "zip";
        public static final String COLUMN_CONTACT_FACEBOOK_URL = "facebookURL";
        public static final String COLUMN_CONTACT_TWITTER_URL = "twitterURL";
        public static final String COLUMN_CONTACT_INSTAGRAM_URL = "instagramURL";
    }

    public static class TaskEntry implements BaseColumns{

        public static final String TABLE_NAME = "tasks";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_TASK_DESCRIPTION = "description";
        public static final String COLUMN_TASK_PRIORITY = "priority";
        public static final String COLUMN_TASK_IS_COMPLETED = "isCompleted";
        public static final String COLUMN_TASK_CREATED_ON = "createdOn";
        public static final String COLUMN_TASK_COMPLETED_ON = "completedOn";

        public static final int PRIORITY_LOW = 0;
        public static final int PRIORITY_MEDIUM = 1;
        public static final int PRIORITY_HIGH = 2;

        public static final int IS_COMPLETED_FALSE = 0;
        public static final int IS_COMPLETED_TRUE = 1;

    }

    public static class CalendarEventEntry implements BaseColumns{

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_CALENDAR_EVENTS);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CALENDAR_EVENTS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CALENDAR_EVENTS;

        public static final String TABLE_NAME = "calendar_events";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_EVENT_DATE = "date";
        public static final String COLUMN_EVENT_LOCATION = "location";
        public static final String COLUMN_EVENT_DESCRIPTION = "description";
        public static final String COLUMN_EVENT_START_TIME = "startTime";
        public static final String COLUMN_EVENT_END_TIME = "endTime";


    }


}
