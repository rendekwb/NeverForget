package com.example.android.neverforget.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.neverforget.models.Contact;
import com.example.android.neverforget.R;

import java.util.ArrayList;

/**
 * Created by rendekwb on 3/19/17.
 */

public class ContactAdapter extends ArrayAdapter<Contact> {

    public ContactAdapter(Activity context, int resource, ArrayList<Contact> contacts){
        super(context, resource, contacts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View contactView = convertView;
        if(contactView == null){
            contactView = LayoutInflater.from(getContext()).inflate(R.layout.contact_list_item, parent, false);
        }

        Contact currentContact = getItem(position);

        TextView contactName = (TextView) contactView.findViewById(R.id.contact_name);
        contactName.setText(currentContact.getFirstName() + " " + currentContact.getLastName());

        TextView contactPhoneNumber = (TextView) contactView.findViewById(R.id.contact_phone_number);
        contactPhoneNumber.setText(currentContact.getPhoneNumber());

        TextView contactEmail = (TextView) contactView.findViewById(R.id.contact_email);
        contactEmail.setText(currentContact.getEmailAddress());

        return contactView;

    }
}
