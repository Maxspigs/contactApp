package com.quoizz.max.contactapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;



public class PasswordProtection extends DialogFragment {
    IContactsSecret client;

    public void attach(IContactsSecret client) {
        this.client = client;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        final View v = LayoutInflater.from(getActivity()).inflate(R.layout.password_security, null);

        return new AlertDialog.Builder(getActivity()).setPositiveButton("Ok",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText password = (EditText) v.findViewById(R.id.DialogPasswordEdit);
                String passwordString = password.getText().toString();
                client.validatePassword(passwordString);
            }
        }).setView(v).create();
    }
}
