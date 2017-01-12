package com.anthony.dugouchet.smartask;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import static android.content.ContentValues.TAG;

/**
 * Created by dugou on 21/11/2016.
 */

public class AddGroupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_writter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final MySQLite mySQLite = MySQLite.getInstance(AddGroupActivity.this);
        //on recupere les infos de la tache

        final EditText name_of_group = (EditText) findViewById(R.id.group_name);
        final EditText description = (EditText) findViewById(R.id.description_of_group);


        Button sendtask = (Button) findViewById(R.id.send_group);
        sendtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name_string = name_of_group.getText().toString();
                String description_string = description.getText().toString();

                if (name_string.isEmpty()){
                    Toast.makeText(AddGroupActivity.this, "Nom requis", Toast.LENGTH_SHORT).show();
                }else {
                    if (mySQLite.isGroupNameExists(name_string))
                    {
                        Toast.makeText(AddGroupActivity.this, "Nom deja pris", Toast.LENGTH_SHORT).show();
                    }else {
                        Group g = new Group(0, name_string, description_string);
                        long succes = mySQLite.addOrUpdateGroup(g);
                        if (succes == -1) {
                            Toast.makeText(AddGroupActivity.this, "Groupe NON crée", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddGroupActivity.this, "Groupe crée", Toast.LENGTH_SHORT).show();
                        }
                        try {
                            String succes2 = mySQLite.postgroup_api(g);
                            Log.d(TAG, "addGroup :" + succes2);
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
