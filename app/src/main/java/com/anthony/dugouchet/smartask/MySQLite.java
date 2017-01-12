package com.anthony.dugouchet.smartask;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.StrictMode;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;


/**
 * Created by dugou on 19/11/2016.
 */

public class MySQLite extends SQLiteOpenHelper {

    // Database Info
    private static final String DATABASE_NAME = "Database1";
    private static  int DATABASE_VERSION = 17;
    private  int USER_ID ;

    // Table Names
    private static final String TABLE_GROUP= "groupList";
    private static final String TABLE_CONTACT="contactList";
    private static final String TABLE_TASK= "taskList";
    private static final String TABLE_USER= "user";

    // User Table Columns
    private static final String KEY_ID_USER="user_id";
    private static final String KEY_USERNAME_USER="username";
    private static final String KEY_EMAIL_USER="email";
    private static final String KEY_PASSWORD_USER="password";

    // Group Table Columns
    private static final String KEY_ID_GROUP="id_group";
    private static final String KEY_TITLE_GROUP="title_group";
    private static final String KEY_DESCRIPTION_GROUP="description_group";

    // Contact Table Columns
    private static final String KEY_ID_CONTACT="id_contact";
    private static final String KEY_NAME_CONTACT="name_contact";
    private static final String KEY_EMAIL_CONTACT="email_contact";

    // Task Table Columns
    private static final String KEY_ID_TASK="id_task";
    private static final String KEY_TITLE_TASK="title_task";
    private static final String KEY_LOCALISATION_TASK="localisation_task";
    private static final String KEY_DAY_TASK="day_task";
    private static final String KEY_MONTH_TASK="month_task";
    private static final String KEY_YEAR_TASK="year_task";
    private static final String KEY_MINUTES_TASK="minutes_task";
    private static final String KEY_HOURS_TASK="hours_task";
    private static final String KEY_DESCRIPTION_TASK="description_task";
    private static final String KEY_RESPONSIBLE_TASK_FK="responsible_task";
    private static final String KEY_MANAGER_TASK="manager_task";
    private static final String KEY_GROUP_TASK_FK="group_task";
    private static final String KEY_ISALARMEON_TASK="isAlarmeOn_task";


    private static MySQLite sInstance;


    /****************************************************************
     * */
    public static final String URL = "http://sample-env-2.jn3ta3wjpd.eu-central-1.elasticbeanstalk.com/web/";
    public static final String getcontacts_api_URL = URL+"getcontacts_api";
    public static final String getgroups_api_URL = URL+"getgroups_api";
    public static final String gettasks_api_URL = URL+"gettasks_api";
    public static final String postgroup_api_URL = URL+"postgroup_api";
    public static final String posttask_api_URL = URL+"posttask_api";
    public static final String postcontact_api_URL = URL+"postcontact_api";
    public static final String deletetask_api_URL = URL+"deletetask_api";
    public static final String deletegroup_api_URL = URL+"deletegroup_api";
    public static final String deletecontact_api_URL = URL+"deletecontact_api";
    public static final String getuser_api_URL = URL+"getuser_api";


    public String deleteContact_api(int id)throws IOException{
        String result="";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(deletecontact_api_URL+"?resp_id="+id)
                .build();
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            try {
                Response response = client.newCall(request).execute();
                result = response.body().string();
            } finally {

            }
        }
        return result;
    }
    public String deleteGroup_api(int id)throws IOException{
        String result="";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(deletegroup_api_URL+"?group_id="+id)
                .build();
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            try {
                Response response = client.newCall(request).execute();
                result = response.body().string();
            } finally {

            }
        }
        return result;
    }
    public String deleteTask_api(int id)throws IOException{
        String result="";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(deletetask_api_URL+"?task_id="+id)
                .build();
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            try {
                Response response = client.newCall(request).execute();
                result = response.body().string();
            } finally {

            }
        }
        return result;
    }

    public String postgroup_api(Group group)throws IOException{
        String result="";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(postgroup_api_URL+"?user_id="+USER_ID+"&id="+group.get_groupId()+"&nom="+group.get_groupName()+"&description="+group.get_description())
                .build();
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            try {
                Response response = client.newCall(request).execute();
                result = response.body().string();
            } finally {

            }
        }
        return result;
    }

public String postcontact_api(Contact contact)throws IOException{
String result="";
    OkHttpClient client = new OkHttpClient();
    Log.d(TAG, "postcontact_api USER_ID :"+USER_ID);
    Request request = new Request.Builder()
            .url(postcontact_api_URL+"?user_id="+USER_ID+"&id="+contact.getid()+"&name="+contact.getname()+"&email="+contact.getemail())
            .build();
    int SDK_INT = android.os.Build.VERSION.SDK_INT;
    if (SDK_INT > 8) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } finally {

        }
    }
    return result;
}
    public String posttask_api(Task task)throws IOException{
        String result="";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(posttask_api_URL+"?user_id="+USER_ID+"&id="+task.get_taskId()+"&titre="+task.get_title_task()+"&resp_id="+task.get_responsible()+"&manager_id="+task.get_manager()+"&group_id="+task.get_groupId()+"&date="+task.get_year_task()+"-"+task.get_month_task()+"-"+task.get_day_task()+"%20&time=%2015%3A20%3A00&isalarmeon="+task.get_isAlarmeOn())
                .build();
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            try {
                Response response = client.newCall(request).execute();
                result = response.body().string();
            } finally {

            }
        }
        return result;
    }

    public void getTasks_api() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(gettasks_api_URL+"?user_id="+USER_ID)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG,"ERROR getTasks_api ");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
               // Log.d(TAG," getTasks_api json :"+ response.body().string());
                try {
                    JSONArray jsonArray = new JSONArray(response.body().string());
                    // Pour tous les objets on récupère les infos
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        // On fait le lien Personne - Objet JSON
                        Task task = new Task();
                        task.set_taskId(obj.getInt("id"));
                        task.set_title_task(obj.getString("name"));
                        task.set_localisation_task(obj.getString("localisation"));
                        task.set_description(obj.getString("description"));
                        task.set_responsible(obj.getInt("resp_id"));
                        task.set_manager(obj.getInt("manager_id"));
                        task.set_isAlarmeOn(obj.getInt("isalarmeon"));
                        task.set_groupId(obj.getInt("groupe_id"));
                        // date and time
                        JSONObject objdate = obj.getJSONObject("date");
                        String datestr = objdate.getString("date");
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
                        try {
                            Date date = sdf.parse(datestr);
                            DateTime datetime = new DateTime(date);
                            int day = Integer.parseInt(datetime.toString("DD"));
                            int month = Integer.parseInt(datetime.toString("MM"));
                            int year = Integer.parseInt(datetime.toString("YYYY"));

                            task.set_day_task(day);
                            task.set_month_task(month);
                            task.set_year_task(year);
                        }catch(ParseException ie){
                        }
                        JSONObject objtime = obj.getJSONObject("time");
                        String timestr = objtime.getString("date");
                        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
                        try {
                            Date date2 = sdf2.parse(timestr);
                            DateTime datetime = new DateTime(date2);
                            int hours = Integer.parseInt(datetime.toString("HH"));
                            int minutes = Integer.parseInt(datetime.toString("mm"));
                            task.set_hours_task(hours);
                            task.set_minutes_task(minutes);
                        }catch(ParseException ie){
                        }
                        // On ajoute la personne à la liste
                        Log.d(TAG,"task name i :"+i+" "+task.get_taskId());
                        Log.d(TAG,"task name i :"+i+" "+task.get_title_task());
                        Log.d(TAG,"task group i : "+task.get_groupId());
                        Log.d(TAG,"task resp i :"+task.get_responsible());
                        Log.d(TAG,"task manager i :"+task.get_manager());
                        Log.d(TAG,"task month: "+task.get_month_task());
                        Log.d(TAG,"task day: "+task.get_day_task());
                        Log.d(TAG,"task year: "+task.get_year_task());
                        addOrUpdateTask(task);
                    }
                }
                catch(JSONException ie) {
                    ie.printStackTrace();
                }
            }
        });
    }

    public void getContacts_api() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(getcontacts_api_URL+"?user_id="+USER_ID)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG,"ERROR getContacts_api ");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                List<Contact> listcontact = new Gson().fromJson(response.body().string(), new TypeToken<List<Contact>>() {}.getType());
                for (int i = 0; i < listcontact.size(); i++){
                    Log.d(TAG,"contact name i :"+i+" "+listcontact.get(i).getname());
                    addOrUpdateContact(new Contact(listcontact.get(i).getid(),listcontact.get(i).getname(),listcontact.get(i).getemail()));
                }
            }
        });
    }

    public void getUser_api(String email) {
        Log.d(TAG,"getUser_api ....");
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(getuser_api_URL+"?email="+email)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG,"ERROR getUser_api ");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG," getUser_api response");
                List<User> users = new Gson().fromJson(response.body().string(), new TypeToken<List<User>>() {}.getType());
                for (int i = 0; i < users.size(); i++){
                    Log.d(TAG,"user name i :"+i+" "+users.get(i).getUsername());
                    String[] piece = users.get(i).getPassword().split("\\{");
                    USER_ID = users.get(i).getUser_id();
                    addOrUpdateUser(new User(users.get(i).getUser_id(),users.get(i).getUsername(),users.get(i).getEmail(),piece[0]));
                }
            }
        });
    }
    public void getGroups_api() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(getgroups_api_URL+"?user_id="+USER_ID)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG,"ERROR getGroups_api ");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //Log.d(TAG, "getgroups_api_URL json :" +response.body().string());

                List<Group> listgroups = new Gson().fromJson(response.body().string(), new TypeToken<List<Group>>() {}.getType());
                for (int i = 0; i < listgroups.size(); i++){
                    Log.d(TAG,"contact name i :"+i+" "+listgroups.get(i).get_groupName());
                    addOrUpdateGroup(new Group(listgroups.get(i).get_groupId(),listgroups.get(i).get_groupName(),listgroups.get(i).get_description()));

                }
            }
        });
    }

    /****************************************************************
     * */

    public static synchronized MySQLite getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new MySQLite(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Constructor should be private to prevent direct instantiation.
     * Make a call to the static method "getInstance()" instead.
     */
    private MySQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database connection is being configured.
    // Configure database settings for things like foreign key support, write-ahead logging, etc.
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    // Called when the database is created for the FIRST time.
    // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS "+TABLE_USER+
                " (" +
                " "+KEY_ID_USER+" INTEGER primary key," +
                " "+KEY_USERNAME_USER+" TEXT," +
                " "+KEY_EMAIL_USER+" TEXT," +
                " "+KEY_PASSWORD_USER+" TEXT" +
                ");";
        String CREATE_TABLE_GROUP = "CREATE TABLE IF NOT EXISTS "+TABLE_GROUP+
                " (" +
                " "+KEY_ID_GROUP+" INTEGER primary key," +
                " "+KEY_TITLE_GROUP+" TEXT," +
                " "+KEY_DESCRIPTION_GROUP+" TEXT" +
                ");";

        String CREATE_TABLE_CONTACT = "CREATE TABLE IF NOT EXISTS "+TABLE_CONTACT+
                " (" +
                " "+KEY_ID_CONTACT+" INTEGER primary key," +
                " "+KEY_NAME_CONTACT+" TEXT," +
                " "+KEY_EMAIL_CONTACT+" TEXT" +
                ");";

        String CREATE_TABLE_TASK = "CREATE TABLE IF NOT EXISTS "+TABLE_TASK+
                " (" +
                " "+KEY_ID_TASK+" INTEGER PRIMARY KEY," +
                " "+KEY_TITLE_TASK+" TEXT," +
                " "+KEY_LOCALISATION_TASK+" TEXT," +
                " "+KEY_DAY_TASK+" INTEGER," +
                " "+KEY_MONTH_TASK+" INTEGER," +
                " "+KEY_YEAR_TASK+" INTEGER," +
                " "+KEY_MINUTES_TASK+" INTEGER," +
                " "+KEY_HOURS_TASK+" INTEGER," +
                " "+KEY_DESCRIPTION_TASK+" TEXT," +
                " "+KEY_RESPONSIBLE_TASK_FK+" INTEGER," +
                " "+KEY_MANAGER_TASK+" INTEGER," +
                " "+KEY_GROUP_TASK_FK+" INTEGER," +
                " "+KEY_ISALARMEON_TASK+" INTEGER," +
                " "+"FOREIGN KEY ("+KEY_GROUP_TASK_FK+") REFERENCES "+TABLE_GROUP+"("+KEY_ID_GROUP+"),"+
                " "+"FOREIGN KEY ("+KEY_RESPONSIBLE_TASK_FK+") REFERENCES "+TABLE_CONTACT+"("+KEY_ID_CONTACT+")"+
                " );";

        db.execSQL(CREATE_TABLE_GROUP);
        db.execSQL(CREATE_TABLE_CONTACT);
        db.execSQL(CREATE_TABLE_TASK);
        db.execSQL(CREATE_TABLE_USER);
    }
public void incrementVersionDB (){
    Log.d(TAG,"incrementVersionDB from : "+DATABASE_VERSION +" to :");
    DATABASE_VERSION=DATABASE_VERSION+1;
    Log.d(TAG,""+DATABASE_VERSION);
}
    // Called when the database needs to be upgraded.
    // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    // but the DATABASE_VERSION is different than the version of the database that exists on disk.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUP);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
            onCreate(db);
        }
    }

    public void clearDB() {
        SQLiteDatabase db = getWritableDatabase();
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUP);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
            onCreate(db);
    }

    // Insert or update a user in the database
    // Since SQLite doesn't support "upsert" we need to fall back on an attempt to UPDATE (in case the
    // user already exists) optionally followed by an INSERT (in case the user does not already exist).
    // Unfortunately, there is a bug with the insertOnConflict method
    // (https://code.google.com/p/android/issues/detail?id=13045) so we need to fall back to the more
    // verbose option of querying for the user's primary key if we did an update.

public long addOrUpdateUser(User user) {
        // The database connection is cached so it's not expensive to call getWriteableDatabase() multiple times.
        SQLiteDatabase db = getWritableDatabase();
        long userId = -1;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            if (user.getUser_id() != 0){
                values.put(KEY_ID_USER, user.getUser_id());
            }
            values.put(KEY_USERNAME_USER, user.getUsername());
            values.put(KEY_EMAIL_USER, user.getEmail());
            values.put(KEY_PASSWORD_USER, user.getPassword());

            // First try to update the user in case the user already exists in the database
            // This assumes userNames are unique
            int rows = db.update(TABLE_USER, values, KEY_USERNAME_USER + "= ?", new String[]{user.getUsername()});

            // Check if update succeeded
            if (rows == 1) {
                // Get the primary key of the user we just updated
                String usersSelectQuery = String.format("SELECT %s FROM %s WHERE %s = ?",
                        KEY_ID_USER, TABLE_USER, KEY_USERNAME_USER);
                Cursor cursor = db.rawQuery(usersSelectQuery, new String[]{String.valueOf(user.getUsername())});
                try {
                    if (cursor.moveToFirst()) {
                        userId = cursor.getInt(0);
                        db.setTransactionSuccessful();
                    }
                } finally {
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                }
            } else {
                // user with this userName did not already exist, so insert new user
                userId = db.insertOrThrow(TABLE_USER, null, values);
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add or update User");
        } finally {
            db.endTransaction();
        }
        return userId;
    }
public long addOrUpdateContact(Contact contact) {
    // The database connection is cached so it's not expensive to call getWriteableDatabase() multiple times.
    SQLiteDatabase db = getWritableDatabase();
    long userId = -1;

    db.beginTransaction();
    try {
        ContentValues values = new ContentValues();
        if (contact.getid() != 0){
            values.put(KEY_ID_CONTACT, contact.getid());
        }
        values.put(KEY_NAME_CONTACT, contact.getname());
        values.put(KEY_EMAIL_CONTACT, contact.getemail());

        // First try to update the user in case the user already exists in the database
        // This assumes userNames are unique
        int rows = db.update(TABLE_CONTACT, values, KEY_NAME_CONTACT + "= ?", new String[]{contact.getname()});

        // Check if update succeeded
        if (rows == 1) {
            // Get the primary key of the user we just updated
            String usersSelectQuery = String.format("SELECT %s FROM %s WHERE %s = ?",
                    KEY_ID_CONTACT, TABLE_CONTACT, KEY_NAME_CONTACT);
            Cursor cursor = db.rawQuery(usersSelectQuery, new String[]{String.valueOf(contact.getname())});
            try {
                if (cursor.moveToFirst()) {
                    userId = cursor.getInt(0);
                    db.setTransactionSuccessful();
                }
            } finally {
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
            }
        } else {
            // user with this userName did not already exist, so insert new user
            userId = db.insertOrThrow(TABLE_CONTACT, null, values);
            db.setTransactionSuccessful();
        }
    } catch (Exception e) {
        Log.d(TAG, "Error while trying to add or update Contact");
    } finally {
        db.endTransaction();
    }
    return userId;
}


    public long addOrUpdateGroup(Group user) {
        // The database connection is cached so it's not expensive to call getWriteableDatabase() multiple times.
        SQLiteDatabase db = getWritableDatabase();
        long userId = -1;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            if (user.get_groupId()!=0){
                values.put(KEY_ID_GROUP, user.get_groupId());
            }
            values.put(KEY_TITLE_GROUP, user.get_groupName());
            values.put(KEY_DESCRIPTION_GROUP, user.get_description());

            // First try to update the user in case the user already exists in the database
            // This assumes userNames are unique
            int rows = db.update(TABLE_GROUP, values, KEY_TITLE_GROUP + "= ?", new String[]{user.get_groupName()});

            // Check if update succeeded
            if (rows == 1) {
                // Get the primary key of the user we just updated
                String usersSelectQuery = String.format("SELECT %s FROM %s WHERE %s = ?",
                        KEY_ID_GROUP, TABLE_GROUP, KEY_TITLE_GROUP);
                Cursor cursor = db.rawQuery(usersSelectQuery, new String[]{String.valueOf(user.get_groupName())});
                try {
                    if (cursor.moveToFirst()) {
                        userId = cursor.getInt(0);
                        db.setTransactionSuccessful();
                    }
                } finally {
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                }
            } else {
                // user with this userName did not already exist, so insert new user
                userId = db.insertOrThrow(TABLE_GROUP, null, values);
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add or update Group");
        } finally {
            db.endTransaction();
        }
        return userId;
    }

    public long addOrUpdateTask(Task task) {
        // The database connection is cached so it's not expensive to call getWriteableDatabase() multiple times.
        SQLiteDatabase db = getWritableDatabase();
        long userId = -1;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            if (task.get_taskId() !=0){
                values.put(KEY_ID_TASK, task.get_taskId());
            }
            values.put(KEY_TITLE_TASK, task.get_title_task());
            values.put(KEY_LOCALISATION_TASK, task.get_localisation_task());
            values.put(KEY_DAY_TASK, task.get_day_task());
            values.put(KEY_MONTH_TASK, task.get_month_task());
            values.put(KEY_YEAR_TASK, task.get_year_task());
            values.put(KEY_MINUTES_TASK, task.get_minutes_task());
            values.put(KEY_HOURS_TASK, task.get_hours_task());
            values.put(KEY_DESCRIPTION_TASK, task.get_description());
            values.put(KEY_RESPONSIBLE_TASK_FK, task.get_responsible());
            values.put(KEY_MANAGER_TASK, task.get_manager());
            values.put(KEY_GROUP_TASK_FK, task.get_groupId());
            values.put(KEY_ISALARMEON_TASK, task.get_isAlarmeOn());

            // First try to update the user in case the user already exists in the database
            // This assumes userNames are unique
            int rows = db.update(TABLE_TASK, values, KEY_TITLE_TASK + "= ?", new String[]{task.get_title_task()});

            // Check if update succeeded
            if (rows == 1) {
                // Get the primary key of the user we just updated
                String usersSelectQuery = String.format("SELECT %s FROM %s WHERE %s = ?",
                        KEY_ID_TASK, TABLE_TASK, KEY_TITLE_TASK);
                Cursor cursor = db.rawQuery(usersSelectQuery, new String[]{String.valueOf(task.get_title_task())});
                try {
                    if (cursor.moveToFirst()) {
                        userId = cursor.getInt(0);
                        db.setTransactionSuccessful();
                    }
                } finally {
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                }
            } else {
                // user with this userName did not already exist, so insert new user
                userId = db.insertOrThrow(TABLE_TASK, null, values);
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add or update Task");
        } finally {
            db.endTransaction();
        }
        return userId;
    }

    public User getuser() {
        List<User> users = new ArrayList<>();
        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    User newuser = new User();
                    newuser.setUser_id( cursor.getInt(cursor.getColumnIndex(KEY_ID_USER)));
                    newuser.setUsername( cursor.getString(cursor.getColumnIndex(KEY_USERNAME_USER)));
                    newuser.setEmail( cursor.getString(cursor.getColumnIndex(KEY_EMAIL_USER)));
                    newuser.setPassword( cursor.getString(cursor.getColumnIndex(KEY_PASSWORD_USER)));
                    users.add(newuser);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get User from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return users.get(0);
    }

    // Get all group in the database
    public List<Group> getAllGroups() {
        List<Group> groups = new ArrayList<>();
        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_GROUP, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Group newGroup = new Group(0,"","");
                    newGroup.set_groupId( cursor.getInt(cursor.getColumnIndex(KEY_ID_GROUP)));
                    newGroup.set_groupName( cursor.getString(cursor.getColumnIndex(KEY_TITLE_GROUP)));
                    newGroup.set_description( cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION_GROUP)));
                    groups.add(newGroup);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get Groups from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return groups;
    }

    // Get all group in the database
    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CONTACT, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Contact newContact = new Contact(0,"","");
                    newContact.setid( cursor.getInt(cursor.getColumnIndex(KEY_ID_CONTACT)));
                    newContact.setname( cursor.getString(cursor.getColumnIndex(KEY_NAME_CONTACT)));
                    newContact.setemail( cursor.getString(cursor.getColumnIndex(KEY_EMAIL_CONTACT)));
                    contacts.add(newContact);
                    Log.d(TAG, "contacts.add(newContact)name : "+newContact.getname());
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get Contact from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return contacts;
    }


    public int getNumberOfContact(){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_CONTACT, null).getCount();
    }

    public int getNumberOfGroup(){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_GROUP, null).getCount();
    }

    public int getNumberOfTask(){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_TASK, null).getCount();
    }

    public Group getGroup(int id) {
        // Retourne la tache dont l'id est passé en paramètre

        Group a = new Group(0, "", "");
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_GROUP + " WHERE " + KEY_ID_GROUP + "="+id, null);
        if (c.moveToFirst()) {
            a.set_groupId(c.getInt(c.getColumnIndex(KEY_ID_GROUP)));
            a.set_groupName(c.getString(c.getColumnIndex(KEY_TITLE_GROUP)));
            a.set_description(c.getString(c.getColumnIndex(KEY_DESCRIPTION_GROUP)));
            c.close();
        }
        return a;
    }

    public Group getGroup(String name) {
        // Retourne la tache dont l'id est passé en paramètre

        Group a = new Group(0, "", "");
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_GROUP + " WHERE " + KEY_TITLE_GROUP+ "= ?", new String[]{name});
        if (c.moveToFirst()) {
            a.set_groupId(c.getInt(c.getColumnIndex(KEY_ID_GROUP)));
            a.set_groupName(c.getString(c.getColumnIndex(KEY_TITLE_GROUP)));
            a.set_description(c.getString(c.getColumnIndex(KEY_DESCRIPTION_GROUP)));
            c.close();
        }
        return a;
    }


    public Contact getContact(int id) {
        // Retourne la tache dont l'id est passé en paramètre
        SQLiteDatabase db = getReadableDatabase();
        Contact a=new Contact(0,"","");

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_CONTACT+" WHERE "+KEY_ID_CONTACT+"="+id, null);
        if (c.moveToFirst()) {
            a.setid(c.getInt(c.getColumnIndex(KEY_ID_CONTACT)));
            a.setname(c.getString(c.getColumnIndex(KEY_NAME_CONTACT)));
            a.setemail(c.getString(c.getColumnIndex(KEY_EMAIL_CONTACT)));
            c.close();
        }
        return a;
    }

    public Contact getContact(String name) {
        // Retourne la tache dont l'id est passé en paramètre
        SQLiteDatabase db = getReadableDatabase();
        Contact a=new Contact(0,"","");

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_CONTACT+" WHERE "+KEY_NAME_CONTACT+"= ?", new String[]{name});
        if (c.moveToFirst()) {
            a.setid(c.getInt(c.getColumnIndex(KEY_ID_CONTACT)));
            a.setname(c.getString(c.getColumnIndex(KEY_NAME_CONTACT)));
            a.setemail(c.getString(c.getColumnIndex(KEY_EMAIL_CONTACT)));
            c.close();
        }
        return a;
    }

    public Task getTask(int id) {
        // Retourne la tache dont l'id est passé en paramètre

        Task a=new Task();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_TASK+" WHERE "+KEY_ID_TASK+"="+id, null);
        if (c.moveToFirst()) {
            a.set_taskId(c.getInt(c.getColumnIndex(KEY_ID_TASK)));
            a.set_title_task(c.getString(c.getColumnIndex(KEY_TITLE_TASK)));
            a.set_localisation_task(c.getString(c.getColumnIndex(KEY_LOCALISATION_TASK)));
            a.set_day_task(c.getInt(c.getColumnIndex(KEY_DAY_TASK)));
            a.set_month_task(c.getInt(c.getColumnIndex(KEY_MONTH_TASK)));
            a.set_year_task(c.getInt(c.getColumnIndex(KEY_YEAR_TASK)));
            a.set_minutes_task(c.getInt(c.getColumnIndex(KEY_MINUTES_TASK)));
            a.set_hours_task(c.getInt(c.getColumnIndex(KEY_HOURS_TASK)));
            a.set_description(c.getString(c.getColumnIndex(KEY_DESCRIPTION_TASK)));
            a.set_responsible(c.getInt(c.getColumnIndex(KEY_RESPONSIBLE_TASK_FK)));
            a.set_manager(c.getInt(c.getColumnIndex(KEY_MANAGER_TASK)));
            a.set_groupId(c.getInt(c.getColumnIndex(KEY_GROUP_TASK_FK)));
            a.set_isAlarmeOn(c.getInt(c.getColumnIndex(KEY_ISALARMEON_TASK)));
            c.close();
        }
        return a;
    }

    public void deleteTask (int id){
        SQLiteDatabase db = getReadableDatabase();
         db.delete(TABLE_TASK, KEY_TITLE_TASK+"="+id, null);
    }
    public void deleteContact (int id){
        SQLiteDatabase db = getReadableDatabase();
        db.delete(TABLE_TASK, KEY_RESPONSIBLE_TASK_FK+"="+id, null); // delete associated task
        boolean succes = db.delete(TABLE_CONTACT, KEY_ID_CONTACT+"="+id, null)>0;
        if (succes){Log.d(TAG,"contact deleted");}else{Log.d(TAG,"contact NOT deleted");}
    }
    public void deleteGroup (int id){
        SQLiteDatabase db = getReadableDatabase();
        db.delete(TABLE_TASK, KEY_GROUP_TASK_FK+"="+id, null); // delete associated task
        boolean succes = db.delete(TABLE_GROUP, KEY_ID_GROUP+"="+id, null)>0;
        if (succes){Log.d(TAG,"group deleted");}else{Log.d(TAG,"group NOT deleted");}
    }
    public Task getTask(String name) {
        // Retourne la tache dont l'id est passé en paramètre

        Task a=new Task();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_TASK+" WHERE "+KEY_TITLE_TASK+"= ?", new String[]{name});
        if (c.moveToFirst()) {
            a.set_taskId(c.getInt(c.getColumnIndex(KEY_ID_TASK)));
            a.set_title_task(c.getString(c.getColumnIndex(KEY_TITLE_TASK)));
            a.set_localisation_task(c.getString(c.getColumnIndex(KEY_LOCALISATION_TASK)));
            a.set_day_task(c.getInt(c.getColumnIndex(KEY_DAY_TASK)));
            a.set_month_task(c.getInt(c.getColumnIndex(KEY_MONTH_TASK)));
            a.set_year_task(c.getInt(c.getColumnIndex(KEY_YEAR_TASK)));
            a.set_minutes_task(c.getInt(c.getColumnIndex(KEY_MINUTES_TASK)));
            a.set_hours_task(c.getInt(c.getColumnIndex(KEY_HOURS_TASK)));
            a.set_description(c.getString(c.getColumnIndex(KEY_DESCRIPTION_TASK)));
            a.set_responsible(c.getInt(c.getColumnIndex(KEY_RESPONSIBLE_TASK_FK)));
            a.set_manager(c.getInt(c.getColumnIndex(KEY_MANAGER_TASK)));
            a.set_groupId(c.getInt(c.getColumnIndex(KEY_GROUP_TASK_FK)));
            a.set_isAlarmeOn(c.getInt(c.getColumnIndex(KEY_ISALARMEON_TASK)));
            c.close();
        }
        return a;
    }
    public boolean isTaskNameExists(String name) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_TASK+" WHERE "+KEY_TITLE_TASK+"= ?", new String[]{name});
        return (c.getCount()>0);
    }
    public boolean isGroupNameExists(String name) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_GROUP+" WHERE "+KEY_TITLE_GROUP+"= ?", new String[]{name});
        return (c.getCount()>0);
    }
    public boolean isContactNameExists(String name) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_CONTACT+" WHERE "+KEY_NAME_CONTACT+"= ?", new String[]{name});
        return (c.getCount()>0);
    }

    public TaskForAdapteur getTaskForAdapteur(int id_task){

        TaskForAdapteur task = new TaskForAdapteur("","","");
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT "+KEY_TITLE_TASK+", " +KEY_TITLE_GROUP+", "+KEY_NAME_CONTACT+" FROM "
                +TABLE_TASK+ "  JOIN "+TABLE_GROUP+" ON "+KEY_GROUP_TASK_FK+"="+KEY_ID_GROUP+
                             "  JOIN "+TABLE_CONTACT+" ON "+KEY_ID_CONTACT+"="+KEY_RESPONSIBLE_TASK_FK+
                             " WHERE "+KEY_ID_TASK+"="+id_task;
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            task.setName(c.getString(c.getColumnIndex(KEY_TITLE_TASK)));
            task.setGroup(c.getString(c.getColumnIndex(KEY_TITLE_GROUP)));
            task.setResp(c.getString(c.getColumnIndex(KEY_NAME_CONTACT)));
            c.close();
        }return task;
    }
    public List<TaskForAdapteur> getAllTaskForAdapteur() {
        List<TaskForAdapteur> list = new ArrayList<>();
        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT "+KEY_TITLE_TASK+", " +KEY_TITLE_GROUP+", "+KEY_NAME_CONTACT+" FROM "
                +TABLE_TASK+ "  JOIN "+TABLE_GROUP+" ON "+KEY_GROUP_TASK_FK+"="+KEY_ID_GROUP+
                "  JOIN "+TABLE_CONTACT+" ON "+KEY_ID_CONTACT+"="+KEY_RESPONSIBLE_TASK_FK;
        Cursor cursor = db.rawQuery(query, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    TaskForAdapteur tfa = new TaskForAdapteur();
                    tfa.setName( cursor.getString(cursor.getColumnIndex(KEY_TITLE_TASK)));
                    tfa.setResp( cursor.getString(cursor.getColumnIndex(KEY_NAME_CONTACT)));
                    tfa.setGroup( cursor.getString(cursor.getColumnIndex(KEY_TITLE_GROUP)));
                    list.add(tfa);
                    Log.d(TAG, "getAllTaskForAdapteur.add(tfa)name : "+tfa.getName());
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get tfa from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return list;
    }
    public int getNumberOfTaskForAdapteur(){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT "+KEY_TITLE_TASK+", " +KEY_TITLE_GROUP+", "+KEY_NAME_CONTACT+" FROM "
                +TABLE_TASK+ "  JOIN "+TABLE_GROUP+" ON "+KEY_GROUP_TASK_FK+"="+KEY_ID_GROUP+
                "  JOIN "+TABLE_CONTACT+" ON "+KEY_ID_CONTACT+"="+KEY_RESPONSIBLE_TASK_FK;
        return db.rawQuery(query, null).getCount();
    }
}
