package com.anthony.dugouchet.smartask;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dugou on 28/11/2016.
 */

public class TaskAdapteur extends RecyclerView.Adapter<TaskAdapteur.MyViewHolder>{

    private List<TaskForAdapteur> _taskList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, group,resp;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name_task);
            group = (TextView) view.findViewById(R.id.group_task);
            resp = (TextView) view.findViewById(R.id.resp_task);
        }
    }

    public TaskAdapteur(List<TaskForAdapteur> taskList) {
        this._taskList = taskList;
    }

    @Override
    public TaskAdapteur.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_list_row, parent, false);

        return new TaskAdapteur.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TaskAdapteur.MyViewHolder holder, int position) {
        TaskForAdapteur task = _taskList.get(position);
        holder.name.setText(task.getName());
        holder.group.setText(task.getGroup());
        holder.resp.setText(task.getResp());
    }

    public void clearData() {
        int size = this._taskList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this._taskList.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }
    public void removeItem(int position) {
        this._taskList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, this._taskList.size());
    }
    public TaskForAdapteur getTaskForAdapteur(int position){
        return this._taskList.get(position);
    }
    @Override
    public int getItemCount() {
        return _taskList.size();
    }
}

