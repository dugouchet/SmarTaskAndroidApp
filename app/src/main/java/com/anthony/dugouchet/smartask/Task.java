package com.anthony.dugouchet.smartask;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dugou on 19/11/2016.
 */

public class Task {
    public Task() {
    }

    @SerializedName("id") private int _taskId ;
    @SerializedName("name") private String _title_task;
    @SerializedName("localisation") private String _localisation_task;
    private int _day_task;
    private int _month_task;
    private int _year_task;
    private int _minutes_task;
    private int _hours_task;
    @SerializedName("description")private String _description;
    @SerializedName("resp_id")private int _responsible;
    @SerializedName("manager_id")private int _manager;
    @SerializedName("groupe_id")private int _groupId;
    @SerializedName("isalarmeon")private int _isAlarmeOn;


    public int get_responsible() {
        return _responsible;
    }

    public void set_responsible(int _responsible) {
        this._responsible = _responsible;
    }
    public int get_manager() {
        return _manager;
    }

    public void set_manager(int _manager) {
        this._manager = _manager;
    }

    public Task(int _taskId, String _title_task, String _localisation_task, int _day_task, int _month_task, int _year_task, int _minutes_task, int _hours_task, String _description, int _responsible,int _manager, int _groupId, int _isAlarmeOn) {
        this._taskId = _taskId;
        this._title_task = _title_task;
        this._localisation_task = _localisation_task;
        this._day_task = _day_task;
        this._month_task = _month_task;
        this._year_task = _year_task;
        this._minutes_task = _minutes_task;
        this._hours_task = _hours_task;
        this._description = _description;
        this._responsible = _responsible;
        this._manager = _manager;
        this._groupId = _groupId;
        this._isAlarmeOn = _isAlarmeOn;
    }

    public void set_taskId(int _taskId) {
        this._taskId = _taskId;
    }

    public void set_title_task(String _title_task) {
        this._title_task = _title_task;
    }

    public void set_localisation_task(String _localisation_task) {
        this._localisation_task = _localisation_task;
    }

    public void set_day_task(int _day_task) {
        this._day_task = _day_task;
    }

    public void set_month_task(int _month_task) {
        this._month_task = _month_task;
    }

    public void set_year_task(int _year_task) {
        this._year_task = _year_task;
    }

    public void set_minutes_task(int _minutes_task) {
        this._minutes_task = _minutes_task;
    }

    public void set_hours_task(int _hours_task) {
        this._hours_task = _hours_task;
    }


    public void set_groupId(int _groupId) {
        this._groupId = _groupId;
    }


    public void set_isAlarmeOn(int _isAlarmeOn) {
        this._isAlarmeOn = _isAlarmeOn;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public String get_description() {

        return _description;
    }

    public int get_taskId() {

        return _taskId;
    }

    public String get_title_task() {
        return _title_task;
    }

    public String get_localisation_task() {
        return _localisation_task;
    }

    public int get_day_task() {
        return _day_task;
    }

    public int get_month_task() {
        return _month_task;
    }

    public int get_year_task() {
        return _year_task;
    }

    public int get_minutes_task() {
        return _minutes_task;
    }

    public int get_hours_task() {
        return _hours_task;
    }

    public int get_groupId() {
        return _groupId;
    }

    public int get_isAlarmeOn() {
        return _isAlarmeOn;
    }







}
