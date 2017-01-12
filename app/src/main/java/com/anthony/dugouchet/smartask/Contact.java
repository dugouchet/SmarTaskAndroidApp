package com.anthony.dugouchet.smartask;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

/**
 * Created by dugou on 21/11/2016.
 */

public class Contact {
    @SerializedName("id") private int id;
    @SerializedName("name") private String name;
    @SerializedName("email") private String email;

    public Contact(JSONObject jObject) {
        this.id = jObject.optInt("id");
        this.name = jObject.optString("name");
        this.email = jObject.optString("email");
    }

    public Contact(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getid() {

        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }


}
