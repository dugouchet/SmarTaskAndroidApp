package com.anthony.dugouchet.smartask;

import android.content.Intent;
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

public class GroupDetailActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_writter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent_received = getIntent();
        int group_id = intent_received.getIntExtra("Group_ID",0);
        final MySQLite mySQLite = MySQLite.getInstance(this);
        final Group myGroup = mySQLite.getGroup(group_id);

        //name
        final EditText name = (EditText )findViewById(R.id.group_name);
        name.setText(myGroup.get_groupName());
        //description
        final EditText description = (EditText )findViewById(R.id.description_of_group);
        description.setText(myGroup.get_description());


        Button sendgroup = (Button) findViewById(R.id.send_group);
        sendgroup.setText("Modifier groupe");
        sendgroup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                myGroup.set_groupName(name.getText().toString());
                myGroup.set_description(description.getText().toString());
                long succes = mySQLite.addOrUpdateGroup(myGroup);
                try {
                    MySQLite mySQLite = MySQLite.getInstance(GroupDetailActivity.this);
                    String nice = mySQLite.postgroup_api(myGroup);
                    Log.d(TAG,"addGroup :" +nice );
                }
                catch(IOException ie) {
                    ie.printStackTrace();
                }
                if (succes == -1) {
                    Toast.makeText(GroupDetailActivity.this, "Groupe NON modifié", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(GroupDetailActivity.this, "Group modifié", Toast.LENGTH_SHORT).show();
                }
                finish();

            }
        });


    }
}
