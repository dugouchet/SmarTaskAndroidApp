package com.anthony.dugouchet.smartask;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import static android.content.ContentValues.TAG;
import java.io.IOException;

/**
 * Created by dugou on 21/11/2016.
 */

public class AddContactActivity extends AppCompatActivity {
    Contact c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_writer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final MySQLite mySQLite = MySQLite.getInstance(AddContactActivity.this);
        //on recupere les infos de la tache

        final EditText name_of_contact = (EditText) findViewById(R.id.name_contact);
        final EditText email = (EditText) findViewById(R.id.email_contact);


        Button sendtask = (Button) findViewById(R.id.send_contact);
        sendtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name_of_contact_string = name_of_contact.getText().toString();
                String email_string = email.getText().toString();
                if ( name_of_contact_string.isEmpty() || email_string.isEmpty())
                {
                    Toast.makeText(AddContactActivity.this, "nom ou email non valide", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (mySQLite.isContactNameExists(name_of_contact_string))
                    {
                        Toast.makeText(AddContactActivity.this, "nom deja pris", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        c = new Contact(0, name_of_contact_string, email_string);
                        long succes = mySQLite.addOrUpdateContact(c);
                        if (succes == -1) {
                            Toast.makeText(AddContactActivity.this, "Contact PAS ajouté", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddContactActivity.this, "Contact ajouté", Toast.LENGTH_SHORT).show();
                        }
                        try {
                            String succes2 = mySQLite.postcontact_api(c);
                            Log.d(TAG, "addContact :" + succes2);
                        } catch (IOException ie) {
                            ie.printStackTrace();
                        }
                        finish();
                    }
                }
            }
        });
    }
}
