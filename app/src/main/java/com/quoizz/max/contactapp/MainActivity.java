package com.quoizz.max.contactapp;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IContactsSecret {

    private List<Contact> contacts;
    private int idx;
    private String query = "SELECT * FROM "+ DatabaseHandler.DATABASE_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.idx = 0;
        this.contacts = new ArrayList<>();

        DatabaseHandler dbHelper = new DatabaseHandler(this);
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(query, null);

        while (cursor.moveToNext()){
            String nom = cursor.getString(cursor.getColumnIndex("nom"));
            String tel = cursor.getString(cursor.getColumnIndex("tel"));
            this.contacts.add(new Contact(nom, tel));
        }

        passwordProtectionDialog();
        listContacts();

    }

    @Override
    public void validatePassword(String password) {
        if( !password.equals("toto") ){
            passwordProtectionDialog();
            Toast.makeText(MainActivity.this, "Le mot de passe n'est pas valide. MDP = toto", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void addContact(String nom, String numero) {
        saveContact(nom, numero);
        this.contacts.add(new Contact(nom, numero));
    }

    private void listContacts() {
        TextView textNom = (TextView) findViewById(R.id.textName_main);
        textNom.setText(contacts.get(idx).getNom());
        TextView TextTelephone = (TextView) findViewById(R.id.textTel_main);
        TextTelephone.setText(contacts.get(idx).getNumero());
    }

    private void passwordProtectionDialog() {
        PasswordProtection passwordProtection = new PasswordProtection();
        passwordProtection.attach(MainActivity.this);
        passwordProtection.setCancelable(false);
        passwordProtection.show(getSupportFragmentManager(), "PasswordProtectionDialog");
    }

    public void onNewContact(View view){
        AddContact newContact = new AddContact();
        newContact.attach(this);
        newContact.show(getSupportFragmentManager(), "AddContact");
    }

    private void saveContact(String nom, String numero){
        String statement = "INSERT INTO "+ DatabaseHandler.DATABASE_NAME+" VALUES('"+nom+"', '"+numero+"');";
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        databaseHandler.getWritableDatabase().execSQL(statement);
        if(nom.equals("") || numero.equals("")){
            Toast.makeText(this, "Erreur... vous devez entrer les deux 2 champs", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, nom + " a bien été rajouter", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_back:
                if (idx == 0) {
                    idx = contacts.size() - 1;
                }else {
                    idx--;
                }
                break;
            case R.id.action_next:
                if (idx == contacts.size()-1) {
                    idx = 0;
                }else {
                    idx++;
                }
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        listContacts();
        return true;
    }
}
