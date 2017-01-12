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

public class GroupAdapteur extends RecyclerView.Adapter<GroupAdapteur.MyViewHolder>{

    private List<Group> _groupList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name_group);
        }
    }

    public GroupAdapteur(List<Group> groupList) {
        this._groupList = groupList;
    }

    @Override
    public GroupAdapteur.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_list_row, parent, false);

        return new GroupAdapteur.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GroupAdapteur.MyViewHolder holder, int position) {
        Group group = _groupList.get(position);
        holder.name.setText(group.get_groupName());
    }

    public void clearData() {
        int size = this._groupList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this._groupList.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }
    public void removeItem(int position) {
        this._groupList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, this._groupList.size());
    }
    public Group getGroup(int position){
        return this._groupList.get(position);
    }
    @Override
    public int getItemCount() {
        return _groupList.size();
    }
}
