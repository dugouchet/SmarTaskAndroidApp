package com.anthony.dugouchet.smartask;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dugou on 21/12/2016.
 */

public class User {

    @SerializedName("id") private int user_id ;
    @SerializedName("username") private String username;
    @SerializedName("email") private String email;
    @SerializedName("password") private String password;

    public User() {
    }

    public User(int user_id, String username, String email, String password) {
        this.user_id = user_id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
