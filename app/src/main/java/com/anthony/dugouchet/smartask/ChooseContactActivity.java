package com.anthony.dugouchet.smartask;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by dugou on 29/11/2016.
 */

public class ChooseContactActivity extends AppCompatActivity {
    private List<Contact> contactList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ContactAdapteur mAdapter;

    protected void onCreate() {
        setContentView(R.layout.list_contact);

        Button createContactbutton = (Button) findViewById(R.id.button_create_contact);
        createContactbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openCreateContactActivity = new Intent(ChooseContactActivity.this, AddContactActivity.class);
                startActivity(openCreateContactActivity);
            }
        });

        mAdapter = new ContactAdapteur(contactList);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_contact);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                TextView tv = (TextView) view.findViewById(R.id.name_contact);
                String name = tv.getText().toString();
                MySQLite mySQLite = MySQLite.getInstance(ChooseContactActivity.this);
                Intent result = new Intent();
                Log.v("ChooseContactActivity","contactId: "+mySQLite.getContact(name).getid());
                result.putExtra(AddTaskActivity.BUTTONS_resp, mySQLite.getContact(name).getid());
                setResult(RESULT_OK, result);
                finish();
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(ChooseContactActivity.this, "Long press on position :"+position,
                        Toast.LENGTH_LONG).show();
            }
        }));
        prepareContactData();
    }
    private void prepareContactData(){
        mAdapter.clearData();
        MySQLite mySQLite = MySQLite.getInstance(this);
        List<Contact> listcontact = mySQLite.getAllContacts();
        for(Contact contact : listcontact){
            contactList.add(contact);
            Log.d(TAG,"contacts : " +contact.getname());
            Log.d(TAG,"contacts id: "+contact.getid());
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        onCreate();
    }
}
