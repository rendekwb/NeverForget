package com.example.android.neverforget.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.neverforget.R;

/**
 * Created by rendekwb on 4/14/17.
 */

public class ContactCursorAdapter extends CursorAdapter {

    public ContactCursorAdapter(Context context, Cursor cursor){
        super(context,cursor, 0);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.contact_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView contactName = (TextView) view.findViewById(R.id.contact_name);
        TextView contactPhoneNumber = (TextView) view.findViewById(R.id.contact_phone_number);
        TextView contactEmail = (TextView) view.findViewById(R.id.contact_email);

        String name = cursor.getString(cursor.getColumnIndexOrThrow("firstName")) + " " + cursor.getString(cursor.getColumnIndexOrThrow("lastName"));
        String phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow("phoneNumber"));
        String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));

        contactName.setText(name);
        contactPhoneNumber.setText(phoneNumber);
        contactEmail.setText(email);
    }
}
