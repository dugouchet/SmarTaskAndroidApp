package com.anthony.dugouchet.smartask;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by dugou on 27/11/2016.
 */

public class ContactAdapteur extends RecyclerView.Adapter<ContactAdapteur.MyViewHolder> {

    private List<Contact> _contactList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, adress;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name_contact);
            adress = (TextView) view.findViewById(R.id.adress_contact);
        }
    }

    public ContactAdapteur(List<Contact> contactList) {
        this._contactList = contactList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Contact contact = _contactList.get(position);
        holder.name.setText(contact.getname());
        holder.adress.setText(contact.getemail());
    }

    public void clearData() {
        int size = this._contactList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this._contactList.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }

    public void removeItem(int position) {
        this._contactList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, this._contactList.size());
    }
    public Contact getContact(int position){
        Log.d(TAG,"contactList size :"+_contactList.size());
        return this._contactList.get(position);
    }

    @Override
    public int getItemCount() {
        return _contactList.size();
    }
}
