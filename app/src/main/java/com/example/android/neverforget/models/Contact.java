package com.example.android.neverforget.models;

/**
 * Created by rendekwb on 3/19/17.
 */

public class Contact {

    // Declaration of private variables
    private int mId;
    private String mFirstName;
    private String mLastName;
    private String mPhoneNumber;
    private String mEmailAddress;

    // Contact Constructor
    public Contact(String firstName, String lastName, String phoneNumber, String emailAddress){
        mFirstName = firstName;
        mLastName = lastName;
        mPhoneNumber = phoneNumber;
        mEmailAddress = emailAddress;

    }

    // Getter Methods
    public int getId(){ return mId; }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public String getEmailAddress() {
        return mEmailAddress;
    }





}
