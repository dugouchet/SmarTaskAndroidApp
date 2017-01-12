package com.anthony.dugouchet.smartask;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dugou on 21/11/2016.
 */

public class Group {

    @SerializedName("id") private int _groupId ;
    @SerializedName("name") private String _groupName;
    @SerializedName("description") private String _description;


    public Group(int _groupId, String _groupName, String _description) {
        this._groupId = _groupId;
        this._groupName = _groupName;
        this._description = _description;
    }

    public int get_groupId() {

        return _groupId;
    }

    public void set_groupId(int _groupId) {
        this._groupId = _groupId;
    }

    public String get_groupName() {
        return _groupName;
    }

    public void set_groupName(String _groupName) {
        this._groupName = _groupName;
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }
}
