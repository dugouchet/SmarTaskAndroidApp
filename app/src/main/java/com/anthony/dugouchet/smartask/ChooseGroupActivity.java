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
 * Created by dugou on 22/11/2016.
 */

public class ChooseGroupActivity extends AppCompatActivity{
    private List<Group> groupList = new ArrayList<>();
    private RecyclerView recyclerView;
    private GroupAdapteur mAdapter;

    protected void onCreate() {
        setContentView(R.layout.listgroup);
        Button createGroupbutton = (Button) findViewById(R.id.create_group);
        createGroupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openCreateGroupActivity = new Intent(ChooseGroupActivity.this, AddGroupActivity.class);
                startActivity(openCreateGroupActivity);
            }
        });

        mAdapter = new GroupAdapteur(groupList);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_group);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                TextView tv = (TextView) view.findViewById(R.id.name_group);
                String name = tv.getText().toString();
                MySQLite mySQLite = MySQLite.getInstance(ChooseGroupActivity.this);
                Intent result = new Intent();
                Log.v("ChooseGroupActivity","groupId: "+mySQLite.getGroup(name).get_groupId());
                result.putExtra(AddTaskActivity.BUTTONS_gp, mySQLite.getGroup(name).get_groupId());
                setResult(RESULT_OK, result);
                finish();
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(ChooseGroupActivity.this, "Long press on position :"+position,
                        Toast.LENGTH_LONG).show();
            }
        }));
        prepareGroupData();
    }
    private void prepareGroupData(){
        mAdapter.clearData();
        MySQLite mySQLite = MySQLite.getInstance(this);
        List<Group> listgroup = mySQLite.getAllGroups();
        for(Group group : listgroup){
            groupList.add(group);
            Log.d(TAG,"group : " +group.get_groupName());
            Log.d(TAG,"group id: "+group.get_groupId());
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        onCreate();
    }
}
