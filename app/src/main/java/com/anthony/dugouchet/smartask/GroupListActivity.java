package com.anthony.dugouchet.smartask;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static android.webkit.ConsoleMessage.MessageLevel.LOG;
import static com.anthony.dugouchet.smartask.R.color.color_delete;

/**
 * Created by dugou on 21/11/2016.
 */

public class GroupListActivity extends AppCompatActivity{

    private List<Group> groupList = new ArrayList<>();
    private RecyclerView recyclerView;
    private GroupAdapteur mAdapter;
    private Paint p = new Paint();

    protected void onCreate() {
        setContentView(R.layout.listgroup);
        setNavigationBar();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Button createGroupbutton = (Button) findViewById(R.id.create_group);
        createGroupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openCreateGroupActivity = new Intent(GroupListActivity.this, AddGroupActivity.class);
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
                Intent openTask = new Intent(GroupListActivity.this, GroupDetailActivity.class);
                TextView tv = (TextView) view.findViewById(R.id.name_group);
                String name = tv.getText().toString();
                MySQLite mySQLite = MySQLite.getInstance(GroupListActivity.this);
                // On rajoute un extra
                openTask.putExtra("Group_ID",mySQLite.getGroup(name).get_groupId());

                startActivity(openTask);
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(GroupListActivity.this, "Long press on position :"+position,
                        Toast.LENGTH_LONG).show();
            }
        }));
        prepareGroupData();
        initSwipe();

    }
    private void initSwipe(){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT){
                    MySQLite mySQLite = MySQLite.getInstance(GroupListActivity.this);
                    Log.d(TAG, "group id :"+mAdapter.getGroup(position).get_groupId());
                    int id =mAdapter.getGroup(position).get_groupId();
                    mySQLite.deleteGroup(id);
                    try {
                        String succes2 = mySQLite.deleteGroup_api(id);
                        Log.d(TAG, "deleteContact_api :" + succes2);
                    } catch (IOException ie) {
                        ie.printStackTrace();
                    }
                    mAdapter.removeItem(position);

                } else {
                    // nothing to do yet
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if(dX < 0){
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_menu_send);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
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

    private void setNavigationBar() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);


        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.action_amis:
                                startActivity(new Intent(GroupListActivity.this, ContactListActivity.class));
                                finish();

                                break;
                            case R.id.action_group:
                                startActivity(new Intent(GroupListActivity.this, GroupListActivity.class));
                                finish();
                                break;
                            case R.id.action_task:
                                startActivity(new Intent(GroupListActivity.this, AddTaskActivity.class));
                                finish();

                                break;
                            case R.id.action_activity:
                                startActivity(new Intent(GroupListActivity.this, TaskListActivity.class));
                                finish();

                                break;
                        }
                        return false;
                    }


                });
    }
}
