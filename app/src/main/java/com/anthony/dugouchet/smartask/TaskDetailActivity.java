package com.anthony.dugouchet.smartask;

import android.content.Intent;
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

/**
 * Created by dugou on 20/11/2016.
 */

public class TaskDetailActivity extends AppCompatActivity {
    private int groupid ;
    private int contactid ;
    public String myGroupeName;
    Button back_button;
    public String myContactName;

    private final static int CHOOSE_BUTTON_REQUEST_gp = 1;
    public final static String BUTTONS_gp = "group_button";
    private final static int CHOOSE_BUTTON_REQUEST_resp = 2;
    public final static String BUTTONS_resp = "responsible_button";
    Button groupButton;
    Button contactButton;
    Task mytask;

     MySQLite mySQLite = MySQLite.getInstance(this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_writter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent intent_received = getIntent();
        int task_id = intent_received.getIntExtra("TASK_ID",0);
        mytask = mySQLite.getTask(task_id);
       //title
        final EditText title = (EditText )findViewById(R.id.title);
        title.setText(mytask.get_title_task());
        //localisation
        final EditText location = (EditText )findViewById(R.id.location);
        location.setText(mytask.get_localisation_task());

        //date
        final DatePicker date = (DatePicker ) findViewById(R.id.datePicker);
        date.updateDate(mytask.get_year_task(),mytask.get_month_task(),mytask.get_day_task());

        //time
        final TimePicker time = (TimePicker )findViewById(R.id.timePicker);
        time.setHour(mytask.get_hours_task());
        time.setMinute(mytask.get_minutes_task());

        //description
        final EditText description = (EditText )findViewById(R.id.description);
        description.setText(mytask.get_description());

        //isAlarmeOn
        final Switch isAlarmeOn = (Switch) findViewById(R.id.alarme_swich);
        if (mytask.get_isAlarmeOn()==1 ) {
            isAlarmeOn.setChecked(true);
        }else{
            isAlarmeOn.setChecked(false);
        }

        contactButton = (Button) findViewById(R.id.button_responsible);
        contactButton.setText(mySQLite.getContact(mytask.get_responsible()).getname());
        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGroupMenu = new Intent(TaskDetailActivity.this, ChooseContactActivity.class);
                startActivityForResult(openGroupMenu, CHOOSE_BUTTON_REQUEST_resp);
            }
        });



        groupButton = (Button) findViewById(R.id.button_group);
        groupButton.setText(mySQLite.getGroup(mytask.get_groupId()).get_groupName());
        groupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGroupMenu = new Intent(TaskDetailActivity.this, ChooseGroupActivity.class);
                startActivityForResult(openGroupMenu, CHOOSE_BUTTON_REQUEST_gp);
            }
        });

        Button sendtask = (Button) findViewById(R.id.send_task);
        sendtask.setText("Modifier Tache");

        sendtask.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                mytask.set_title_task(title.getText().toString());
                mytask.set_localisation_task(location.getText().toString());
                mytask.set_hours_task(time.getHour());
                mytask.set_minutes_task(time.getMinute());
                mytask.set_day_task(date.getDayOfMonth());
                mytask.set_month_task(date.getMonth());
                mytask.set_year_task(date.getYear());
                if (isAlarmeOn.isChecked()) {
                    mytask.set_isAlarmeOn(1);
                }else{
                    mytask.set_isAlarmeOn(0);
                }
                mytask.set_description(description.getText().toString());

                    long succes = mySQLite.addOrUpdateTask(mytask);
                    if (succes == -1) {
                        Toast.makeText(TaskDetailActivity.this, "Task NOT modify", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(TaskDetailActivity.this, "Task modify", Toast.LENGTH_SHORT).show();
                    }
                finish();

                }
        });
        back_button = (Button) findViewById(R.id.back);
        back_button.setText("Annuler");
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back_intent = new Intent(TaskDetailActivity.this, TaskListActivity.class);
                startActivity(back_intent);
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // On vérifie tout d'abord à quel intent on fait référence ici à l'aide de notre identifiant

        switch (requestCode) {


            case CHOOSE_BUTTON_REQUEST_gp:
            // On vérifie aussi que l'opération s'est bien déroulée
            if (resultCode == RESULT_OK) {
                // On affiche le bouton qui a été choisi

                groupid = data.getIntExtra(BUTTONS_gp, 0);
                Log.v("", "AddTaskActivity groupid :" + groupid);
                myGroupeName = mySQLite.getGroup(groupid).get_groupName();
                groupButton.setText(myGroupeName);
                mytask.set_groupId(groupid);
                Toast.makeText(this, "Vous avez choisi le bouton " + myGroupeName, Toast.LENGTH_SHORT).show();
            }break;
            case CHOOSE_BUTTON_REQUEST_resp:
                // On vérifie aussi que l'opération s'est bien déroulée
                if (resultCode == RESULT_OK) {
                    // On affiche le bouton qui a été choisi
                    contactid = data.getIntExtra(BUTTONS_resp, 0);
                    Log.v("", "AddTaskActivity groupid :" + contactid);
                    myContactName = mySQLite.getContact(contactid).getname();
                    contactButton.setText(myContactName);
                    mytask.set_groupId(contactid);
                    Toast.makeText(this, "Vous avez choisi le bouton " + myContactName, Toast.LENGTH_SHORT).show();
            }break;
        }
    }
}

