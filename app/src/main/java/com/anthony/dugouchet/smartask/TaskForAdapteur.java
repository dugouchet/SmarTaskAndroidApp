package com.anthony.dugouchet.smartask;

/**
 * Created by dugou on 28/11/2016.
 */

public class TaskForAdapteur {

    private String name,group,resp ;

    public TaskForAdapteur() {
    }

    public String getResp() {
        return resp;
    }

    public void setResp(String resp) {
        this.resp = resp;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TaskForAdapteur(String name, String group, String resp) {

        this.name = name;
        this.group = group;
        this.resp = resp;
    }
}
