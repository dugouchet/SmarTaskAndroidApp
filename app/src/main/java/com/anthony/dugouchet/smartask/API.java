package com.anthony.dugouchet.smartask;

import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by dugou on 14/12/2016.
 */

public class API extends AppCompatActivity {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String URL = "http://sample-env-2.jn3ta3wjpd.eu-central-1.elasticbeanstalk.com/web/app_dev.php/";
    public static final String getcontacts_api_URL = URL+"getcontacts_api/";
    public static final String getgroups_api_URL = URL+"getgroups_api/";
    public static final String gettasks_api_URL = URL+"gettasks_api/";
    public static final String postgroup_api_URL = URL+"postgroup_api/";
    public static final String posttask_api_URL = URL+"posttask_api/";
    public static final String postcontact_api_URL = URL+"postcontact_api/";
    public static final String deletetask_api_URL = URL+"deletetask_api/";
    public static final String deletegroup_api_URL = URL+"deletegroup_api/";
    public static final String deletecontact_api_URL = URL+"deletecontact_api/";

    OkHttpClient client = new OkHttpClient();


    public String ContactJson(Contact contact) {
        return "{'winCondition':'HIGH_SCORE',"
                + "'name':"+contact.getname()+","
                + "'email':"+contact.getname()+","
                + "]}";
    }


    public String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public String postcontact_api(Contact contact) throws IOException {
        return this.post(postcontact_api_URL, ContactJson(contact));
    }
    public  void getContacts_api() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(getcontacts_api_URL)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //do failure stuff
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Contact c = new Contact(0,"","");
                c = new Gson().fromJson(response.body().charStream(), Contact.class);
                List<Contact> listcontact = new Gson().fromJson(response.body().charStream(), new TypeToken<List<Contact>>() {}.getType());
                MySQLite mySQLite = MySQLite.getInstance(API.this);
                for (int i = 0; i < listcontact.size(); i++) {
                    mySQLite.addOrUpdateContact(listcontact.get(i));
                }

                /*
                Gson gson = new Gson();
                Gist gist = gson.fromJson(response.body().charStream(), Gist.class);
                for (Map.Entry<String, GistFile> entry : gist.files.entrySet()) {
                    System.out.println(entry.getKey());
                    System.out.println(entry.getValue().content);
                }
                */
            }
        });
    }
    /*
    static class Gist {
        Map<String, GistFile> files;
    }

    static class GistFile {
        String content;
    }


*/

}
