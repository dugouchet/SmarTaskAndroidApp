package com.anthony.dugouchet.smartask;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.anthony.dugouchet.smartask.R.color.color_delete;


/**
 * Created by dugou on 20/11/2016.
 */

public class TaskListActivity extends AppCompatActivity  {

    private List<TaskForAdapteur> taskList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TaskAdapteur mAdapter;
    private Paint p = new Paint();

    protected void onCreate() {
        setContentView(R.layout.list_task);
        setNavigationBar();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAdapter = new TaskAdapteur(taskList);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_task);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                Intent openTask = new Intent(TaskListActivity.this, TaskDetailActivity.class);
                TextView tv = (TextView) view.findViewById(R.id.name_task);
                String name = tv.getText().toString();
                MySQLite mySQLite = MySQLite.getInstance(TaskListActivity.this);
                // On rajoute un extra
                openTask.putExtra("TASK_ID",mySQLite.getTask(name).get_taskId());

                startActivity(openTask);
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(TaskListActivity.this, "Long press on position :"+position,
                        Toast.LENGTH_LONG).show();
            }
        }));
        prepareTaskData();
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
                    MySQLite mySQLite = MySQLite.getInstance(TaskListActivity.this);
                    int id =mySQLite.getTask(mAdapter.getTaskForAdapteur(position).getName()).get_taskId();
                    Log.d(TAG, "task id :"+id);
                    mySQLite.deleteTask(id);
                    try {
                        String succes2 = mySQLite.deleteTask_api(id);
                        Log.d(TAG, "deleteTask_api:" + succes2);
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

    private void prepareTaskData(){
        mAdapter.clearData();
        MySQLite mySQLite = MySQLite.getInstance(this);
        List<TaskForAdapteur> listtask = mySQLite.getAllTaskForAdapteur();
        for(TaskForAdapteur tfa : listtask){
            taskList.add(tfa);
            Log.d(TAG,"tfa name : " +tfa.getName());
        }
        mAdapter.notifyDataSetChanged();
        }

    @Override
    protected void onResume() {
        super.onResume();
        onCreate();
    }

    private void setNavigationBar() {
        final BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);


        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.action_amis:
                                startActivity(new Intent(TaskListActivity.this, ContactListActivity.class));
                                finish();

                                break;
                            case R.id.action_group:
                                startActivity(new Intent(TaskListActivity.this, GroupListActivity.class));
                                finish();
                                break;
                            case R.id.action_task:
                                startActivity(new Intent(TaskListActivity.this, AddTaskActivity.class));
                                finish();

                                break;
                            case R.id.action_activity:
                                startActivity(new Intent(TaskListActivity.this, TaskListActivity.class));
                                finish();

                                break;
                        }
                        return false;
                    }


                });
    }
}
