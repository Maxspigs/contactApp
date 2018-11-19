package com.quoizz.max.contactapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class AddContact extends DialogFragment {
    IContactsSecret client;

    public void attach(IContactsSecret client){
        this.client = client;
    }

    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        final View v = LayoutInflater.from(getActivity()).inflate(R.layout.new_contact, null);

        return new AlertDialog.Builder(getActivity()).setTitle("nouveau contact secret").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                EditText nom = (EditText) v.findViewById(R.id.name);
                EditText numero = (EditText) v.findViewById(R.id.telephone);

                client.addContact(nom.getText().toString(), numero.getText().toString());
            }
        }).setView(v).create();
    }
}
