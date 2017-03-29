package com.example.android.neverforget.data;

import android.provider.BaseColumns;

/**
 * Created by rendekwb on 3/26/17.
 */

public final class NeverForgetContract {

    public static class ContactEntry implements BaseColumns {

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
        public static final String COLUMN_CONTACT_SNAPCHAT_HANDLE = "snapchatHandle";
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


}
