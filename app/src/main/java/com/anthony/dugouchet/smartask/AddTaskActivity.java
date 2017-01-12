package com.anthony.dugouchet.smartask;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.IOException;

import static android.content.ContentValues.TAG;


/**
 * Created by dugou on 17/11/2016.
 */

public class AddTaskActivity extends AppCompatActivity {
    private final static int CHOOSE_BUTTON_REQUEST_gp = 1;
    public final static String BUTTONS_gp = "group_button";
    private final static int CHOOSE_BUTTON_REQUEST_resp = 2;
    public final static String BUTTONS_resp = "responsible_button";
    int groupid = 0;
    int contactid = 0;
    Button contact_button ;
    Button group_button ;
    Button back_button;
    String myGroupName;
    String myContactName;
    MySQLite mySQLite = MySQLite.getInstance(this);
    Boolean isGroupselected = false;
    Boolean isRespSelected = false ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_writter);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //on recupere les infos de la tache
        final EditText title = (EditText) findViewById(R.id.title);
        final EditText location = (EditText) findViewById(R.id.location);
        final EditText description = (EditText) findViewById(R.id.description);
        final DatePicker date = (DatePicker) findViewById(R.id.datePicker);
        final TimePicker time = (TimePicker) findViewById(R.id.timePicker);
        final Switch alarme = (Switch) findViewById(R.id.alarme_swich);

        contact_button = (Button) findViewById(R.id.button_responsible);
        contact_button.setText("Selection Responsable");
        contact_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGroupMenu = new Intent(AddTaskActivity.this, ChooseContactActivity.class);
                startActivityForResult(openGroupMenu, CHOOSE_BUTTON_REQUEST_resp);
            }
        });

        group_button = (Button) findViewById(R.id.button_group);
        group_button.setText("Selection Groupe");
        group_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGroupMenu = new Intent(AddTaskActivity.this, ChooseGroupActivity.class);
                startActivityForResult(openGroupMenu, CHOOSE_BUTTON_REQUEST_gp);
            }
        });

        Button sendtask = (Button) findViewById(R.id.send_task);
        sendtask.setText("Céer tache");
        sendtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title_string = title.getText().toString();
                if (isGroupselected & isRespSelected) {
                    if (mySQLite.isTaskNameExists(title_string)) {
                        Toast.makeText(AddTaskActivity.this, "Titre deja pris", Toast.LENGTH_SHORT).show();
                    }else {
                        String location_string = location.getText().toString();
                        String description_string = description.getText().toString();
                        //date
                        int day = date.getDayOfMonth();
                        int month = date.getMonth();
                        int year = date.getYear();
                        int hours = 0;
                        int minutes = 0;
                        int isAlarmeOn = 0;
                        if (alarme.isChecked()) {
                            isAlarmeOn = 1;
                        }
                        //time

                        if (Build.VERSION.SDK_INT >= 23) {
                            hours = time.getHour();
                            minutes = time.getMinute();
                        } else {
                            Toast.makeText(AddTaskActivity.this, "SDK <23", Toast.LENGTH_SHORT).show();
                        }
                        int is24hourViewINT = 0;
                        if (time.is24HourView()) {
                            is24hourViewINT = 1;
                        }
                        int manager_id = mySQLite.getuser().getUser_id();
                        Task t = new Task(0, title_string, location_string, day, month, year, hours, minutes, description_string, contactid,manager_id, groupid, isAlarmeOn);
                        long succes = mySQLite.addOrUpdateTask(t);
                        try {
                            String succes2 = mySQLite.posttask_api(t);
                            Log.d(TAG, "addtask :" + succes2);
                        } catch (IOException ie) {
                            ie.printStackTrace();
                        }
                        if (succes == -1) {
                            Toast.makeText(AddTaskActivity.this, "Tache NON crée", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddTaskActivity.this, "Tache crée", Toast.LENGTH_SHORT).show();
                        }
                        startActivity(new Intent(AddTaskActivity.this, TaskListActivity.class));
                        finish();
                    }
                }
                else{
                    Toast.makeText(AddTaskActivity.this, "Groupe ou Responsable non valide", Toast.LENGTH_SHORT).show();
                }
            }
        });
        back_button = (Button) findViewById(R.id.back);
        back_button.setText("Annuler");
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back_intent = new Intent(AddTaskActivity.this, TaskListActivity.class);
                startActivity(back_intent);
                finish();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // On vérifie tout d'abord à quel intent on fait référence ici à l'aide de notre identifiant
        switch (requestCode){
            case  CHOOSE_BUTTON_REQUEST_gp  :
            // On vérifie aussi que l'opération s'est bien déroulée
            if (resultCode == RESULT_OK) {
                // On affiche le bouton qui a été choisi;
                groupid = data.getIntExtra(BUTTONS_gp,0) ;
                Log.v("","AddTaskActivity groupid :" + groupid);
                myGroupName = mySQLite.getGroup(groupid).get_groupName();
                group_button.setText(myGroupName);
                isGroupselected = true ;
            }break;
            case  CHOOSE_BUTTON_REQUEST_resp  :
                // On vérifie aussi que l'opération s'est bien déroulée
                if (resultCode == RESULT_OK) {
                    // On affiche le bouton qui a été choisi;
                    contactid = data.getIntExtra(BUTTONS_resp,0) ;
                    Log.v("","AddTaskActivity contactid :" + contactid);
                    myContactName = mySQLite.getContact(contactid).getname();
                    contact_button.setText(myContactName);
                    isRespSelected=true;
                }break;
        }

    }
}


